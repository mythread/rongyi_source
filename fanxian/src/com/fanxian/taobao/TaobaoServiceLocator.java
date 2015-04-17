package com.fanxian.taobao;

import com.yue.commons.core.CommonServiceLocator;

public class TaobaoServiceLocator extends CommonServiceLocator {

    public static TaobaoService getTaobaoService() {
        return (TaobaoService) getBean("taobaoService");
    }
}
