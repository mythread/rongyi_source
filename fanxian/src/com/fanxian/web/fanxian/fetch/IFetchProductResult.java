package com.fanxian.web.fanxian.fetch;

/**
 * 解析各网站的商品
 * 
 * @author jiejie 2014-1-25 下午10:19:27
 */
public interface IFetchProductResult {

    void resolver();

    boolean success();

    String message();
}
