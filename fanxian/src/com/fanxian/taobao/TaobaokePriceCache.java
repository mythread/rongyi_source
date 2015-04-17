package com.fanxian.taobao;

/**
 * 存放淘宝商品佣金，存入内存
 */
public class TaobaokePriceCache {

    private String  identity;  // 格式：“taobao_” + id;
    private Integer price;     // 佣金价格
    private long    createTime; // 数据存入内存的时间

    public TaobaokePriceCache(String identity, Integer price) {
        this.identity = identity;
        this.price = price;
        this.createTime = System.currentTimeMillis();
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

}
