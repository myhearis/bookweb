/*
 * Copyright (c) 2015, 2018, Oracle and/or its affiliates. All rights reserved.
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License, version 2.0, as published by the
 * Free Software Foundation.
 *
 * This program is also distributed with certain software (including but not
 * limited to OpenSSL) that is licensed under separate terms, as designated in a
 * particular file or component or in included license documentation. The
 * authors of MySQL hereby grant you an additional permission to link the
 * program and your derivative works with the separately licensed software that
 * they have included with MySQL.
 *
 * Without limiting anything contained in the foregoing, this file, which is
 * part of MySQL Connector/J, is also subject to the Universal FOSS Exception,
 * version 1.0, a copy of which can be found at
 * http://oss.oracle.com/licenses/universal-foss-exception.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License, version 2.0,
 * for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin St, Fifth Floor, Boston, MA 02110-1301  USA
 */

package com.mysql.cj.result;

import java.sql.Date;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import com.mysql.cj.Messages;
import com.mysql.cj.WarningListener;
import com.mysql.cj.exceptions.DataReadException;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.exceptions.WrongArgumentException;

/**
 * A value factory for creating {@link Date} values.
 */
public class SqlDateValueFactory extends DefaultValueFactory<Date> {
    private WarningListener warningListener;
    // cached per instance to avoid re-creation on every create*() call
    private Calendar cal;

    public SqlDateValueFactory(Calendar calendar, TimeZone tz) {
        if (calendar != null) {
            this.cal = (Calendar) calendar.clone();
        } else {
            // c.f. Bug#11540 for details on locale
            this.cal = Calendar.getInstance(tz, Locale.US);
            this.cal.set(Calendar.MILLISECOND, 0);
            this.cal.setLenient(false);
        }
    }

    public SqlDateValueFactory(Calendar calendar, TimeZone tz, WarningListener warningListener) {
        this(calendar, tz);
        this.warningListener = warningListener;
    }

    @Override
    public Date createFromDate(int year, int month, int day) {
        synchronized (this.cal) {
            try {
                if (year == 0 && month == 0 && day == 0) {
                    throw new DataReadException(Messages.getString("ResultSet.InvalidZeroDate"));
                }

                this.cal.clear();
                this.cal.set(year, month - 1, day);
                long ms = this.cal.getTimeInMillis();
                return new Date(ms);
            } catch (IllegalArgumentException e) {
                throw ExceptionFactory.createException(WrongArgumentException.class, e.getMessage(), e);
            }
        }
    }

    @Override
    public Date createFromTime(int hours, int minutes, int seconds, int nanos) {
        if (this.warningListener != null) {
            // TODO: need column context
            this.warningListener.warningEncountered(Messages.getString("ResultSet.ImplicitDatePartWarning", new Object[] { "java.sql.Date" }));
        }

        synchronized (this.cal) {
            try {
                // c.f. java.sql.Time "The date components should be set to the "zero epoch" value of January 1, 1970 GMT and should not be accessed."
                // A new Calendar instance is used to don't spoil the date part of the default one.
                Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.US);
                c1.set(1970, 0, 1, hours, minutes, seconds);
                c1.set(Calendar.MILLISECOND, 0);
                long ms = (nanos / 1000000) + c1.getTimeInMillis();
                return new Date(ms);
            } catch (IllegalArgumentException e) {
                throw ExceptionFactory.createException(WrongArgumentException.class, e.getMessage(), e);
            }
        }
    }

    @Override
    public Date createFromTimestamp(int year, int month, int day, int hours, int minutes, int seconds, int nanos) {
        if (this.warningListener != null) {
            // TODO: need column context
            this.warningListener.warningEncountered(Messages.getString("ResultSet.PrecisionLostWarning", new Object[] { "java.sql.Date" }));
        }

        // truncate any time information
        return createFromDate(year, month, day);
    }

    public String getTargetTypeName() {
        return Date.class.getName();
    }
}
