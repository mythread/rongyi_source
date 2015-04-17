package com.fanxian.web.fanxian.webuser;

import org.apache.commons.lang.math.NumberUtils;

import com.fanxian.commons.cookie.CookieKeyEnum;
import com.fanxian.commons.cookie.CookieNameEnum;
import com.fanxian.commons.cookie.manager.CookieManager;

public class FanxianWebUserBuild {

    public static FanxianWebUser createWebUser(CookieManager cookieManager) {
        FanxianWebUser webUser = new FanxianWebUser();
        String userId = cookieManager.get(CookieNameEnum.fanxian_cookie_userid);
        String cookieId = cookieManager.get(CookieKeyEnum.cookie_id);
        if (cookieId == null) {
            cookieId = CookieIdBuilder.createCookieId(cookieManager);
        }
        webUser.setUserId(NumberUtils.toInt(userId));
        webUser.setCookieId(cookieId);
        if (!webUser.isLogin()) {
            cookieManager.set(CookieNameEnum.fanxian_cookie_userid, null);
        }
        return webUser;
    }
}
