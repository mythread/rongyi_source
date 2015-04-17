package com.gcrm.action.crm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.gcrm.domain.Account;
import com.gcrm.domain.Contact;
import com.gcrm.domain.Record;
import com.gcrm.domain.Record.RecordTypeEnum;
import com.gcrm.service.IBaseService;
import com.gcrm.util.Constant;

public class EditRecordAction extends BaseEditAction {

    private Integer               pageNow;
    private Record                record;
    private IBaseService<Record>  baseService;
    private IBaseService<Contact> contactService;
    List<Contact>                 contactList;     // 联系人
    private String                assignedDateName; // 指派时间
    private IBaseService<Account> accountService;
    private Integer               outerId;
    private String                table;           // 表名
    private String                accountName;     // 商场名称
    private Integer               intentStatus;
    private Integer               visitStatus;
    private String                contactName;     // 受访人

    public String get() {
        Integer id = getId();
        record = null;
        if (id != null) {
            record = baseService.getEntityById(Record.class, id);
            if (record != null && record.getContactId() != null) {
                contactName = contactService.getEntityById(Contact.class, record.getContactId()).getName();
            }
            Account ac = accountService.getEntityById(Account.class, record.getOuterId());
            if (ac != null) {
                setAccountName(ac.getName());
                setIntentStatus(ac.getAccountIntent());
                setVisitStatus(ac.getAccountVisit());
                setOuterId(outerId);
            }

        }
        if (outerId != null && StringUtils.equalsIgnoreCase(table, "account")) {
            Account ac = accountService.getEntityById(Account.class, outerId);
            if (ac != null) {
                setAccountName(ac.getName());
                setIntentStatus(ac.getAccountIntent());
                setVisitStatus(ac.getAccountVisit());
                setOuterId(outerId);

                // 获得商场下的联系人
                String hql = "from Contact where account = " + outerId;
                contactList = contactService.findByHQL(hql);
            }
        }
        this.setId(id);
        return SUCCESS;
    }

    /**
     * 保存
     */
    public String save() throws Exception {
        saveEntity();
        record = baseService.makePersistent(record);
        this.setId(record.getId());
        this.setSaveFlag("true");
        return SUCCESS;
    }

    /**
     * Saves entity field
     * 
     * @return original account record
     * @throws Exception
     */
    private void saveEntity() throws Exception {
        Record originalRecord = null;
        if (record.getId() != null) {
            originalRecord = baseService.getEntityById(Record.class, record.getId());
            record.setCreated_on(originalRecord.getCreated_on());
            record.setCreated_by(originalRecord.getCreated_by());
        }
        record.setTableName("account");
        Integer accountId = record.getOuterId();
        // 更新商场客户信息
        Account acc = accountService.getEntityById(Account.class, accountId);
        if (StringUtils.isNotBlank(assignedDateName)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATE_SIMPLE_FORMAT);

            Date assignDate = dateFormat.parse(assignedDateName);
            record.setAssigned_date(assignDate);
            acc.setAssigned_date(assignDate);
        }
        setRecordType(record, acc);
        super.updateBaseInfo(acc);
        accountService.makePersistent(acc);
        String recordText = "";
        if ("PHONE_VISIT".equals(record.getVisitType())) {
            recordText = String.format("%s给商场【%s】新增了一条电话拜访记录", getLoginUser().getLast_name(), acc.getName());
        } else if ("DROP_IN_VISIT".equals(record.getVisitType())) {
            recordText = String.format("%s给商场【%s】新增了一条上门拜访记录", getLoginUser().getLast_name(), acc.getName());
        } else if ("STRANGE_VISIT".equals(record.getVisitType())) {
            recordText = String.format("%s给商场【%s】新增了一条陌生拜访记录", getLoginUser().getLast_name(), acc.getName());
        } else if ("EMAIL_VISIT".equals(record.getVisitType())) {
            recordText = String.format("%s给商场【%s】新增了一条邮件往来记录", getLoginUser().getLast_name(), acc.getName());
        } else if ("IM_VISIT".equals(record.getVisitType())) {
            recordText = String.format("%s给商场【%s】新增了一条IM沟通记录", getLoginUser().getLast_name(), acc.getName());
        } else {
            recordText = String.format("%s给商场【%s】新增了一条记录", getLoginUser().getLast_name(), acc.getName());
        }
        if (RecordTypeEnum.INTENT_VISIT_CHANGE.name().equals(record.getType())) {
            recordText += String.format("，客户意向转为%s，拜访进度转为%s", acc.getIntentName(), acc.getVisitName());
        } else if (RecordTypeEnum.INTENT_CHANGE.name().equals(record.getType())) {
            recordText += String.format("，客户意向转为%s", acc.getIntentName());
        } else if (RecordTypeEnum.VISIT_CHANGE.name().equals(record.getType())) {
            recordText += String.format("，拜访进度转为%s", acc.getVisitName());
        }
        record.setIsRemind(0);
        record.setMemo(recordText);
        super.updateBaseInfo(record);
    }

    /**
     * 设置record的type类型
     * 
     * @return
     */
    private void setRecordType(Record record, Account acc) {
        if (record == null || acc == null) {
            return;
        }
        // 请求参数里的意向
        Integer intentId = record.getAccountIntent();
        Integer visitId = record.getAccountVisit();
        if (intentId == null || visitId == null) {
            return;
        }
        // 现有数据库里的意向
        Integer nowIntentId = acc.getAccountIntent();
        if (nowIntentId == null) {
            nowIntentId = 0;
        }
        Integer nowVisitId = acc.getAccountVisit();
        if (nowVisitId == null) {
            nowVisitId = 0;
        }
        record.setOldAccountIntent(nowIntentId);
        record.setOldAccountVisit(nowVisitId);
        if (intentId == 0 && visitId == 0) {
            record.setType(RecordTypeEnum.FOLLOW_RECORD.name());
        } else {

            if (!nowIntentId.equals(intentId) && nowVisitId.equals(visitId)) {
                // 意向变更
                acc.setAccountIntent(intentId);
                record.setType(RecordTypeEnum.INTENT_CHANGE.name());
            } else if (nowIntentId.equals(intentId) && !nowVisitId.equals(visitId)) {
                // 拜访变更
                acc.setAccountVisit(visitId);
                record.setType(RecordTypeEnum.VISIT_CHANGE.name());
            } else if (!nowIntentId.equals(intentId) && !nowVisitId.equals(visitId)) {
                // 意向、进度均变更
                acc.setAccountIntent(intentId);
                acc.setAccountVisit(visitId);
                record.setType(RecordTypeEnum.INTENT_VISIT_CHANGE.name());
            } else {
                record.setType(RecordTypeEnum.FOLLOW_RECORD.name());
            }
        }

    }

    public Integer getPageNow() {
        return pageNow;
    }

    public void setPageNow(Integer pageNow) {
        this.pageNow = pageNow;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public IBaseService<Record> getBaseService() {
        return baseService;
    }

    public void setBaseService(IBaseService<Record> baseService) {
        this.baseService = baseService;
    }

    public String getAssignedDateName() {
        return assignedDateName;
    }

    public void setAssignedDateName(String assignedDateName) {
        this.assignedDateName = assignedDateName;
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }

    public IBaseService<Contact> getContactService() {
        return contactService;
    }

    public void setContactService(IBaseService<Contact> contactService) {
        this.contactService = contactService;
    }

    public IBaseService<Account> getAccountService() {
        return accountService;
    }

    public void setAccountService(IBaseService<Account> accountService) {
        this.accountService = accountService;
    }

    public Integer getOuterId() {
        return outerId;
    }

    public void setOuterId(Integer outerId) {
        this.outerId = outerId;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Integer getIntentStatus() {
        return intentStatus;
    }

    public void setIntentStatus(Integer intentStatus) {
        this.intentStatus = intentStatus;
    }

    public Integer getVisitStatus() {
        return visitStatus;
    }

    public void setVisitStatus(Integer visitStatus) {
        this.visitStatus = visitStatus;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

}
