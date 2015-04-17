package com.fanxian.biz.userandpro.cons;

/**
 * 用来标记订单返利是否已经充值给用户
 */
public enum AlipayStatusEnum {
    YES("yes"), NO("no");

    private AlipayStatusEnum(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
