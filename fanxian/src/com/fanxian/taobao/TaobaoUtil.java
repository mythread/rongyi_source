package com.fanxian.taobao;

public class TaobaoUtil {

    // 先借我用用appkey啊
    // private static final String LINHUAQIAN_APP_KEY = "21730541";
    public static final String       LINHUAQIAN_APP_KEY     = "12512707";
    // private static final String LINHUAQIAN_APP_SECRET = "37835af56880df7747a556706047ad32";
    public static final String       LINHUAQIAN_APP_SECRET  = "13d984ee0f0f205d4dde51617187b15a";
    private static final String      LINHUAQIAN_SESSION_KEY = null;
    private static final String      URL                    = "http://gw.api.taobao.com/router/rest";
    private static final AuthInfo4TB LINHUAQIAN_APP_AUTH    = new AuthInfo4TB(LINHUAQIAN_APP_KEY,
                                                                              LINHUAQIAN_APP_SECRET,
                                                                              LINHUAQIAN_SESSION_KEY, URL);

    public static AuthInfo4TB getLinHuaQianAppAuth() {
        return LINHUAQIAN_APP_AUTH;
    }

}
