package com.gcrm.domain;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * 跟踪记录DO
 * 
 * @author jiejie
 */
public class Record extends BaseEntity {

    private static final long serialVersionUID = 8824872805760668628L;
    private String            type;                                   // 记录类型 ：新增联系人，修改联系人，新建客户，修改客户，上传合同，指派变更，电话拜访
    // 意向变更，进度变更
    private String            tableName;                              // 关联的数据库表名,
    private Integer           outerId;                                // 外键id
    private Integer           contactId;                              // 联系人id
    private String            visitType;                              // 访问形式
    private String            visitNote;                              // 访问记录
    private Integer           accountIntent;
    private Integer           accountVisit;
    private Integer           oldAccountIntent;                       // 原有客户意向
    private Integer           oldAccountVisit;                        // 原有拜访进度
    private Integer           isRemind;                               // 是否提醒 0:不提醒 1：提醒
    private Date              assigned_date;                          // 跟进时间
    private String            memo;                                   // 备注 （也可用来记录跟踪文案）

    /**
     * 获得跟进时间
     */
    public String getAssignDateName() {
        if (assigned_date == null) {
            return "";
        }
        String dateName = getDateFormat().format(assigned_date);
        if (StringUtils.contains(dateName, "00:00:00")) {
            return dateName.split(" ")[0];
        }
        return dateName;
    }

    /**
     * 获得记录类型文本内容
     * 
     * @return
     */
    public String getTypeText() {
        if ("account".equals(tableName)) {
            // 记录商场流转信息的
            if (RecordTypeEnum.FOLLOW_RECORD.name().equals(type)) {
                if ("PHONE_VISIT".equals(visitType)) {
                    return "电话拜访";
                } else if ("DROP_IN_VISIT".equals(visitType)) {
                    return "上门拜访";
                } else if ("STRANGE_VISIT".equals(visitType)) {
                    return "陌生拜访";
                } else if ("EMAIL_VISIT".equals(visitType)) {
                    return "Email往来";
                } else if ("IM_VISIT".equals(visitType)) {
                    return "IM沟通";
                } else {
                    return "";
                }
            } else {
                return RecordTypeEnum.getEnum(type).getShowName();
            }
        } else {
            return RecordTypeEnum.getEnum(type).getShowName();
        }
    }

    public String getVisitTypeText() {
        if (visitType != null) {
            if ("PHONE_VISIT".equals(visitType)) {
                return "电话拜访";
            } else if ("DROP_IN_VISIT".equals(visitType)) {
                return "上门拜访";
            } else if ("STRANGE_VISIT".equals(visitType)) {
                return "陌生拜访";
            } else if ("EMAIL_VISIT".equals(visitType)) {
                return "Email往来";
            } else if ("IM_VISIT".equals(visitType)) {
                return "IM沟通";
            } else {
                return "";
            }
        }
        return "";
    }

    public String getAccountIntentText(Integer accountIntent) {
        if (accountIntent == 0) {
            return "暂无";
        } else if (accountIntent == 1) {
            return "初步意向";
        } else if (accountIntent == 2) {
            return "口头承诺";
        } else if (accountIntent == 3) {
            return "试用合同";
        } else if (accountIntent == 4) {
            return "正式合同";
        } else if (accountIntent == 5) {
            return "已有设备";
        } else if (accountIntent == 6) {
            return "无投放价值";
        } else if (accountIntent == 7) {
            return "已进场";
        } else {
            return "";
        }

    }

    public String getAccountVisitText(Integer accountVisit) {
        if (accountVisit == 0) {
            return "暂无";
        } else if (accountVisit == 1) {
            return "初次拜访";
        } else if (accountVisit == 2) {
            return "二次拜访";
        } else if (accountVisit == 3) {
            return "多次拜访";
        } else if (accountVisit == 4) {
            return "谈判中";
        } else if (accountVisit == 5) {
            return "售后维护";
        } else {
            return "";
        }
    }

    // 意向变更
    public String getIntentChangeStr() {
        if (oldAccountIntent == null) {
            oldAccountIntent = 0;
        }
        return getAccountIntentText(oldAccountIntent) + " >> " + getAccountIntentText(accountIntent);
    }

    // 进度变更
    public String getVisitChangeStr() {
        if (oldAccountVisit == null) {
            oldAccountVisit = 0;
        }
        return getAccountVisitText(oldAccountVisit) + " >> " + getAccountVisitText(accountVisit);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getOuterId() {
        return outerId;
    }

    public void setOuterId(Integer outerId) {
        this.outerId = outerId;
    }

    public String getVisitType() {
        return visitType;
    }

    public void setVisitType(String visitType) {
        this.visitType = visitType;
    }

    public String getVisitNote() {
        return visitNote;
    }

    public void setVisitNote(String visitNote) {
        this.visitNote = visitNote;
    }

    public Integer getAccountIntent() {
        return accountIntent;
    }

    public void setAccountIntent(Integer accountIntent) {
        this.accountIntent = accountIntent;
    }

    public Integer getAccountVisit() {
        return accountVisit;
    }

    public void setAccountVisit(Integer accountVisit) {
        this.accountVisit = accountVisit;
    }

    public Integer getIsRemind() {
        return isRemind;
    }

    public void setIsRemind(Integer isRemind) {
        this.isRemind = isRemind;
    }

    public Date getAssigned_date() {
        return assigned_date;
    }

    public void setAssigned_date(Date assigned_date) {
        this.assigned_date = assigned_date;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String getName() {
        return "跟踪记录";
    }

    public static enum RecordTypeEnum {
        /**
         * 新增联系人
         */
        ADD_CONTACT("新增联系人"),
        /**
         * 更新联系人
         */
        UPDATE_CONTACT("更新联系人"),
        /**
         * 新建客户
         */
        ADD_ACCOUNT("新建客户"),
        /**
         * 修改客户
         */
        UPDATE_ACCOUNT("修改客户"),
        /**
         * 上传合同
         */
        UPLOAD_CONTRACT("上传合同"),
        /**
         * 指派变更
         */
        ASSIGN_CHANGE("指派变更"),
        /**
         * 跟进记录
         */
        FOLLOW_RECORD("跟进记录"),
        /**
         * 意向变更
         */
        INTENT_CHANGE("意向变更"),
        /**
         * 进度变更
         */
        VISIT_CHANGE("进度变更"),

        /**
         * 意向，进度均改变
         */
        INTENT_VISIT_CHANGE("意向、进度变更");

        private String showName;

        private RecordTypeEnum(String showName) {
            this.showName = showName;
        }

        public String getShowName() {
            return showName;
        }

        public static RecordTypeEnum getEnum(String value) {
            for (RecordTypeEnum t : values()) {
                if (StringUtils.equals(t.name(), value)) {
                    return t;
                }
            }
            return null;
        }
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public Integer getOldAccountIntent() {
        return oldAccountIntent;
    }

    public void setOldAccountIntent(Integer oldAccountIntent) {
        this.oldAccountIntent = oldAccountIntent;
    }

    public Integer getOldAccountVisit() {
        return oldAccountVisit;
    }

    public void setOldAccountVisit(Integer oldAccountVisit) {
        this.oldAccountVisit = oldAccountVisit;
    }

}
