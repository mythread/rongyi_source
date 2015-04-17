package com.gcrm.action.crm;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.gcrm.domain.Account;
import com.gcrm.domain.DailyRecord;
import com.gcrm.domain.Record;
import com.gcrm.domain.User;
import com.gcrm.service.IBaseService;
import com.gcrm.util.Constant;

/**
 * 类DailyRecordAction.java的实现描述：TODO 类实现描述
 * 
 * @author jiejie 2014年4月22日 下午4:41:46
 */
public class DailyRecordAction extends BaseEditAction {

    /**
     * 
     */
    private static final long         serialVersionUID = 7733302529820585255L;

    private IBaseService<DailyRecord> baseService;
    private IBaseService<Record>      recordService;
    private IBaseService<Account>     accountService;

    private String                    dailyDateName;
    private String                    content;

    public String dailyRecord() {
        return SUCCESS;
    }

    /**
     * 生成日报
     */
    public String create() throws Exception {
        User user = getLoginUser();
        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATE_SIMPLE_FORMAT);
        if (StringUtils.isEmpty(dailyDateName)) {
            date = new Date();
            dailyDateName = dateFormat.format(date);
        } else {
            date = dateFormat.parse(dailyDateName);
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 1);
        String endDate = dateFormat.format(cal.getTime());
        // 查询商场跟踪记录
        String recordHql = "from Record where tableName = \'account\' and created_on >= '"
                           + dailyDateName
                           + "' and created_on < '"
                           + endDate
                           + "' and created_by = "
                           + user.getId()
                           + " and (type = \'INTENT_VISIT_CHANGE\' or type = \'INTENT_CHANGE\' or type = \'VISIT_CHANGE\' or type = \'FOLLOW_RECORD\')";
        List<Record> recordList = recordService.findByHQL(recordHql);
        if (recordList != null && recordList.size() > 0) {
            int i = 1;
            StringBuilder sb = new StringBuilder(500);
            sb.append(dailyDateName).append("工作日报：").append("\r\n");
            for (Record r : recordList) {
                String accountHql = "from Account where id = " + r.getOuterId();
                List<Account> accountList = accountService.findByHQL(accountHql);
                if (accountList == null || accountList.isEmpty()) {
                    continue;
                }
                Account acc = accountList.get(0);
                sb.append(i).append("、").append(acc.getName()).append("\r\n").append("*拜访情况:").append(r.getVisitTypeText()).append("/").append(r.getAccountVisitText(r.getAccountVisit())).append("\r\n").append("*客户意向:").append(r.getAccountIntentText(r.getAccountIntent())).append("\r\n").append("*详细情况:").append(r.getVisitNote()).append("\r\n").append("\r\n");
                i++;
            }
            content = sb.toString().trim();
        } else {
            content = "亲，您没取得进展哦！";
        }

        return SUCCESS;
    }

    /**
     * 保存日报
     * 
     * @return
     */

    public String save() throws Exception {
        if (StringUtils.isEmpty(content) || StringUtils.isEmpty(dailyDateName)) {
            return INPUT;
        }
        User user = getLoginUser();
        DailyRecord drecord = new DailyRecord();
        Date date = new Date();
        drecord.setCreated_on(date);
        drecord.setCreated_by(user);
        drecord.setUpdated_on(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATE_SIMPLE_FORMAT);
        Date d = dateFormat.parse(dailyDateName);
        drecord.setDailyDate(d);
        drecord.setContent(content);
        drecord = baseService.makePersistent(drecord);
        return SUCCESS;
    }

    public IBaseService<DailyRecord> getBaseService() {
        return baseService;
    }

    public void setBaseService(IBaseService<DailyRecord> baseService) {
        this.baseService = baseService;
    }

    public String getDailyDateName() {
        return dailyDateName;
    }

    public void setDailyDateName(String dailyDateName) {
        this.dailyDateName = dailyDateName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public IBaseService<Record> getRecordService() {
        return recordService;
    }

    public void setRecordService(IBaseService<Record> recordService) {
        this.recordService = recordService;
    }

    public IBaseService<Account> getAccountService() {
        return accountService;
    }

    public void setAccountService(IBaseService<Account> accountService) {
        this.accountService = accountService;
    }

}
