package my.pojo;

import java.util.List;

/**
 * @Classname Page
 * @author: 我心
 * @Description:分页类，每一页是一个对象
 * @Date 2021/10/19 21:18
 * @Created by Lenovo
 */
public class Page<T> {
    private int allCount;//总记录数
    public static final int DEFAULT_SIZE=4;
    private int pageNo;//当前页码
    private int pagetTotal;//总页码
    private int pageSize=DEFAULT_SIZE;//每页显示数量
    private List<T> list;//当前页数据

    public int getAllCount() {
        return allCount;
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Page() {
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        //在set方法中进行判断，不过前提是必须先给pagetTotal赋值，然后才能给pageNo赋值
        if(pageNo<=0)
            pageNo=1;
        if (pageNo>pagetTotal)
            pageNo=pagetTotal;
        this.pageNo = pageNo;
    }

    public int getPagetTotal() {
        return pagetTotal;
    }

    public void setPagetTotal(int pagetTotal) {
        this.pagetTotal = pagetTotal;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    public Page(int pageNo, int pagetTotal, int pageSize, List<T> list) {
        this.pageNo = pageNo;
        this.pagetTotal = pagetTotal;
        this.pageSize = pageSize;
        this.list = list;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageNo=" + pageNo +
                ", pagetTotal=" + pagetTotal +
                ", pageSize=" + pageSize +
                ", list=" + list +
                '}';
    }
}
