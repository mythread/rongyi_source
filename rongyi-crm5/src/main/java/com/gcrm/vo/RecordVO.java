package com.gcrm.vo;

public class RecordVO {

    private String createOnName;
    private String assignedDateName; // 跟进时间
    private String createByName;
    private String typeShowName;
    private String recordText;
    private String contactName;      // 联系人名字
    private String visitNote;       // 访问记录

    public String getCreateOnName() {
        return createOnName;
    }

    public void setCreateOnName(String createOnName) {
        this.createOnName = createOnName;
    }

    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName;
    }

    public String getRecordText() {
        return recordText;
    }

    public void setRecordText(String recordText) {
        this.recordText = recordText;
    }

    public String getTypeShowName() {
        return typeShowName;
    }

    public void setTypeShowName(String typeShowName) {
        this.typeShowName = typeShowName;
    }

    public String getAssignedDateName() {
        return assignedDateName;
    }

    public void setAssignedDateName(String assignedDateName) {
        this.assignedDateName = assignedDateName;
    }

    public String getVisitNote() {
        return visitNote;
    }

    public void setVisitNote(String visitNote) {
        this.visitNote = visitNote;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

}
