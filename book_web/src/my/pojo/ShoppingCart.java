package my.pojo;

import org.hamcrest.Condition;

import java.util.*;

/**
 * @Classname ShoppingCart
 * @author: 我心
 * @Description:购物车对象
 * @Date 2021/10/30 10:52
 * @Created by Lenovo
 */
public class ShoppingCart {
    private Goods newGood;//最近添加的商品
    private int totalCount=0;//购物车中的总商品数量
    private double totalPrice=0;//总商品金额
    private HashMap<Integer,Goods> goodsMap=new HashMap<>();//商品列表
    //更新购物车数据的方法，更新总商品数量，总商品金额
    public void update(){
        int allCount=0;
        double allPrice=0;
        for (Goods value : goodsMap.values()) {
            allCount+=value.getCount();
            allPrice+=value.getTotalPrice();
        }
        //赋值
        this.totalCount=allCount;
        this.totalPrice=allPrice;
    }
    //添加商品的方法
    public void addGoods(Goods goods){
        //通过id从集合中查找对应id的商品项
        //返回值不为空说明有值，只需要修改商品数量即可
        if (goodsMap.get(goods.getId())!=null){
            Goods good = goodsMap.get(goods.getId());
            int count = good.getCount()+1;
            good.setCount(count);
        }
        //若不存在该商品，直接添加到集合中
        else
            goodsMap.put(goods.getId(),goods);
        this.newGood=goods;
        //更新购物车数据
        update();
    }
    //移除商品的方法,获取id,直接从集合中移除
    public void deleteGoods(int id){
        goodsMap.remove(id);
        //更新数据
        update();
    }
    //直接在购物车中修改商品数量的方法
    public void modifyGoods(int id,int count){
        goodsMap.get(id).setCount(count);
        //更新数据
        update();
    }
    //清空购物车
    public void clear(){
        goodsMap.clear();
        update();

    }

    public Goods getNewGood() {
        return newGood;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public HashMap<Integer, Goods> getGoodsMap() {
        return goodsMap;
    }

    public void setGoodsMap(HashMap<Integer, Goods> goodsMap) {
        this.goodsMap = goodsMap;
    }


    public static void main(String[] args) {
        ShoppingCart shoppingCart = new ShoppingCart();
        for (int i=1;i<=3;i++){
        Goods g = new Goods(1, "你好", 15);
        shoppingCart.addGoods(g);
        }
        shoppingCart.addGoods(new Goods(2,"添",10));
        System.out.println(shoppingCart.getGoodsMap());
        System.out.println(shoppingCart.getTotalCount());
        System.out.println("总价"+shoppingCart.getTotalPrice());
        shoppingCart.deleteGoods(2);
        System.out.println("删除2号以后");
        System.out.println(shoppingCart.getGoodsMap());
        System.out.println(shoppingCart.getTotalCount());
        System.out.println("总价"+shoppingCart.getTotalPrice());
        //更新商品数量
        shoppingCart.modifyGoods(1,2);
        System.out.println("更新数量");
        System.out.println(shoppingCart.getGoodsMap());
        System.out.println(shoppingCart.getTotalCount());
        System.out.println("总价"+shoppingCart.getTotalPrice());
        System.out.println("清空");
        shoppingCart.clear();
        System.out.println(shoppingCart.getGoodsMap());
        System.out.println(shoppingCart.getTotalCount());
        System.out.println("总价"+shoppingCart.getTotalPrice());
    }
}
