package com.fanxian.web.fanxian.webuser;

import java.io.Serializable;

import com.fanxian.commons.lang.Argument;

public class FanxianWebUser implements Serializable {

    private static final long                  serialVersionUID = 781199955690895006L;
    private static ThreadLocal<FanxianWebUser> userHolder       = new ThreadLocal<FanxianWebUser>();

    public static FanxianWebUser getCurrentUser() {
        return userHolder.get();
    }

    public static void setCurrentUser(FanxianWebUser webUer) {
        userHolder.set(webUer);
    }

    private Integer userId;

    private String  cookieId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCookieId() {
        return cookieId;
    }

    public void setCookieId(String cookieId) {
        this.cookieId = cookieId;
    }

    public boolean isLogin() {
        return Argument.isPositive(userId);
    }
}
