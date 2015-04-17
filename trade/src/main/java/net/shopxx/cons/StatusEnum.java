package net.shopxx.cons;

/**
 * 数据库标记是否可用标记
 * 
 * @author jiejie 2014年3月3日 下午1:01:52
 */
public enum StatusEnum {
    /**
     * 可用
     */
    ENABLE("可用", 0),
    /**
     * 不可用
     */
    DISABLE("不可用", 1);

    private String  name;
    private Integer value;

    private StatusEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the value
     */
    public Integer getValue() {
        return value;
    }

    /**
     * 根据value得到枚举
     * 
     * @param value
     * @return
     */
    public static StatusEnum getEnum(Integer value) {
        if (value == null) {
            return null;
        }
        for (StatusEnum sEnum : values()) {
            if (sEnum.getValue().intValue() == value.intValue()) {
                return sEnum;
            }
        }
        return null;
    }

    /**
     * 是否是可用
     * 
     * @param value
     * @return
     */
    public static boolean isEnable(Integer value) {
        if (value == null) {
            return false;
        }
        return ENABLE.getValue().intValue() == value.intValue();
    }

    /**
     * 是否是可用
     * 
     * @param value
     * @return
     */
    public boolean isEnable() {
        return ENABLE == this;
    }

    /**
     * 是否是不可用
     * 
     * @param value
     * @return
     */
    public static boolean isDisable(Integer value) {
        if (value == null) {
            return false;
        }
        return DISABLE.getValue().intValue() == value.intValue();
    }

    /**
     * 是否是不可用
     * 
     * @param value
     * @return
     */
    public boolean isDisable() {
        return DISABLE == this;
    }

}
