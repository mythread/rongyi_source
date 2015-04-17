package net.shopxx.cons;

/**
 * 商品状态
 * 
 * @author jiejie 2014年3月4日 下午1:38:43
 */
public enum ProductStatusEnum {
    WAIT_RELEASE("待发布", "wait_release"),

    ALREADY_RELEASE("已发布", "already_release"),

    ONLINE("上线", "online"),

    OFFLINE("下线", "offline");


    private String name;
    private String value;

    private ProductStatusEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
