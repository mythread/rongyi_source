package com.fanxian.commons.cookie.manager;

import com.fanxian.commons.cookie.CookieKeyEnum;
import com.fanxian.commons.cookie.CookieNameEnum;
import com.fanxian.commons.cookie.annotation.CookieKeyPolicy;

/**
 * 对受管Cookie进行操作的接口
 * 
 * @author wanghai 2011-8-9 下午2:49:58
 */
public interface CookieManager {

    /**
     * 通过CookieKey来取值
     * 
     * @return 如果只值不存在，则返回<code>null</code>
     * @throws RuntimeException 如果当前CookieKey配置的是简单CookieName
     */
    String get(CookieKeyEnum cookieKeyEnum);

    /**
     * 该方法是针对简单Cookie的
     * 
     * @return 如果只值不存在，则返回<code>null</code>
     * @throws RuntimeException 如果当前配置的是复杂CookieName
     */
    String get(CookieNameEnum cookieNameEnum);

    /**
     * 设置复杂Cookie的值
     * 
     * <pre>
     * 更新CookieKeyEnum对应的Value。
     * 如果本次请求的Cookie中该CookieKeyEnum不存在存在，那本方法会添加一个Cookie项。
     * 如果需要{@link CookieKeyPolicy}中CookieKey不是保存在当前域的，那么调用本方法将不会有任何作用
     * </pre>
     * 
     * @throws RuntimeException 如果当前CookieKey配置的是简单CookieName
     */
    void set(CookieKeyEnum cookieKeyEnum, String value);

    /**
     * 设置简单Cookie的值
     * 
     * <pre>
     * 更新cookieNameEnum对应的Value。
     * 如果本次请求的Cookie中该cookieNameEnum不存在存在，那本方法会添加一个Cookie项。
     * 如果需要{@link CookieKeyPolicy}中CookieKey不是保存在当前域的，那么调用本方法将不会有任何作用
     * </pre>
     * 
     * @throws RuntimeException 如果当前配置的是复杂CookieName
     */
    void set(CookieNameEnum cookieNameEnum, String value);

    /**
     * 清理CookieNameEnum下所有CookieKey的值
     */
    void clear(CookieNameEnum cookieNameEnum);

    /**
     * 将本次Cookie的修改保存的reponse中去
     */
    void save();

    /**
     * 清理当前请求下所有的受管Cookie
     */
    void clearAll();

}
