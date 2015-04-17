package com.fanxian.biz.common.cons;

public enum YesNoEnum {
    YES("yes"), NO("no");

    private String value;

    private YesNoEnum(String value) {
        this.value = value;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    public static YesNoEnum getEnum(String value) {
        if (isYes(value)) {
            return YES;
        }
        if (isNo(value)) {
            return NO;
        }
        return null;
    }

    public boolean isYes() {
        return YES == this;
    }

    public static boolean isYes(String value) {
        return YES.getValue().equals(value);
    }

    public boolean isNo() {
        return NO == this;
    }

    public static boolean isNo(String value) {
        return NO.getValue().equals(value);
    }
}
