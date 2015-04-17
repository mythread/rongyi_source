/**
 * Copyright (C) 2012 - 2013, Grass CRM Studio Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */
package com.gcrm.action.crm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.core.task.TaskExecutor;

import com.gcrm.domain.Account;
import com.gcrm.domain.AccountLevel;
import com.gcrm.domain.AccountNature;
import com.gcrm.domain.AccountType;
import com.gcrm.domain.AnnualRevenue;
import com.gcrm.domain.Campaign;
import com.gcrm.domain.Capital;
import com.gcrm.domain.ChangeLog;
import com.gcrm.domain.CompanySize;
import com.gcrm.domain.Contact;
import com.gcrm.domain.Contract;
import com.gcrm.domain.Currency;
import com.gcrm.domain.Document;
import com.gcrm.domain.Industry;
import com.gcrm.domain.Record;
import com.gcrm.domain.Record.RecordTypeEnum;
import com.gcrm.domain.Role;
import com.gcrm.domain.TargetList;
import com.gcrm.domain.User;
import com.gcrm.service.IBaseService;
import com.gcrm.service.IOptionService;
import com.gcrm.util.BeanUtil;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.gcrm.util.security.UserUtil;
import com.gcrm.vo.RecordVO;
import com.gcrm.vo.StatisticsRecordVO;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;

/**
 * Edits Account
 */
public class EditAccountAction extends BaseEditAction implements Preparable {

    private static final long             serialVersionUID = -2404576552417042445L;

    private IBaseService<Account>         baseService;
    private IOptionService<AccountType>   accountTypeService;
    private IOptionService<AccountLevel>  accountLevelService;
    private IOptionService<Capital>       capitalService;
    private IOptionService<AnnualRevenue> annualRevenueService;
    private IOptionService<CompanySize>   companySizeService;
    private IOptionService<AccountNature> accountNatureService;
    private IOptionService<Industry>      industryService;
    private IBaseService<Currency>        currencyService;
    private IBaseService<User>            userService;
    private IBaseService<Campaign>        campaignService;
    private IBaseService<TargetList>      targetListService;
    private IBaseService<Document>        documentService;
    private IBaseService<ChangeLog>       changeLogService;
    private IBaseService<Record>          recordService;
    private IBaseService<Contract>        contractService;
    private TaskExecutor                  taskExecutor;
    private Account                       account;
    private List<AccountType>             types;
    private List<AccountLevel>            accountLevels;
    private List<Currency>                currencies;
    private List<Capital>                 capitals;
    private List<AnnualRevenue>           annualRevenues;
    private List<CompanySize>             companySizes;
    private List<AccountNature>           accountNatures;
    private List<Industry>                industries;
    private List<RecordVO>                recordList;                              // 记录列表
    private List<Contract>                contractList;                            // 合同列表
    private Integer                       typeID           = 0;
    private String                        advertLevel      = "";
    private String                        mallLevel        = "";
    private Integer                       accountLevelID   = null;
    private Integer                       currencyID       = null;
    private Integer                       capitalID        = null;
    private Integer                       annualRevenueID  = null;
    private Integer                       companySizeID    = null;
    private Integer                       accountNatureID  = null;
    private Integer                       industryID       = null;
    private Integer                       campaignID       = null;
    private Integer                       managerID        = null;
    private String                        managerText      = null;
    private String                        createDate       = null;
    private List<Contact>                 mainContactList;                         // 主要联系人
    private List<Contact>                 otherContactList;                        // 其他联系人
    private String                        accountIntent;                           // 意向状态
    private String                        accountVisit;                            // 拜访状态
    private IBaseService<Contact>         contactService;
    private StatisticsRecordVO            recordVO;
    private String                        goon;

    /**
     * Saves the entity.
     * 
     * @return the SUCCESS result
     */
    public String save() throws Exception {
        String validateStr = validateAccountForm();
        if (StringUtils.isNotEmpty(validateStr)) {
            response2Json(null, false, validateStr);
            return null;
        }
        // 验证名称，地址是否存在
        if (account == null) {
            return null;
        }
        String hql = "from Contact where account = " + this.getId() + " AND contact_default = 1";
        mainContactList = contactService.findByHQL(hql);
        boolean flag = true;// 添加新记录
        if (account.getId() == null) {
            if (!StringUtils.equalsIgnoreCase(goon, "true")) {
                // 不是强制添加
                String existhql = "from Account where name = '" + account.getName() + "' or address = '"
                                  + account.getAddress() + "'";
                List<Account> accountList1 = baseService.findByHQL(existhql);
                if (!accountList1.isEmpty()) {
                    String msg = String.format("存在商场【%s】，地址【%s】的记录了，是否继续添加？", accountList1.get(0).getName(),
                                               accountList1.get(0).getAddress());
                    response2Json("exist", false, msg);
                    return null;
                }
            }
        } else {
            flag = false;
        }

        final Account originalAccount = saveEntity();
        String recordText = "";
        // 记录
        Record record1 = null;
        Record record2 = null;
        if (account.getId() == null) {
            recordText = String.format("新建商场【%s】指派给%s", account.getName(), account.getAssigned_to().getLast_name());
            record1 = getRecordOjb(RecordTypeEnum.ADD_ACCOUNT, null, null, null, 0, 0, 0, null, "account", recordText);
        } else {
            if (!originalAccount.getAssigned_to().getId().equals(account.getAssigned_to().getId())) {
                recordText = String.format("商场指派由%s转向%s跟进", originalAccount.getAssigned_to().getName(),
                                           account.getAssigned_to().getName());
                record2 = getRecordOjb(RecordTypeEnum.ASSIGN_CHANGE, account.getId(), null, null,
                                       account.getAccountIntent(), account.getAccountVisit(), 0,
                                       account.getAssigned_date(), "account", recordText);
            } else {

                Account oldaccount = baseService.getEntityById(Account.class, account.getId());
                Account oldaccountEntry = oldaccount.clone();
                Map<String, Map<String, String>> resultMap = compareAccount(account, oldaccountEntry);
                StringBuffer changeValueSb = new StringBuffer();
                Map<String, String> newOldMap = new HashMap<String, String>();
                for (Entry<String, Map<String, String>> maps : resultMap.entrySet()) {
                    changeValueSb.append("修改字段");
                    changeValueSb.append("【" + maps.getKey() + "】");
                    changeValueSb.append(":");
                    newOldMap = maps.getValue();
                    for (Entry<String, String> subMap : newOldMap.entrySet()) {
                        changeValueSb.append(subMap.getValue());
                        changeValueSb.append("=>");
                        changeValueSb.append(subMap.getKey());
                    }
                    changeValueSb.append(",");

                }

                recordText = String.format("商场【%s】信息进行了修改，" + changeValueSb.toString(), account.getName());
                record2 = getRecordOjb(RecordTypeEnum.UPDATE_ACCOUNT, account.getId(), null, null,
                                       account.getAccountIntent(), account.getAccountVisit(), 0,
                                       account.getAssigned_date(), "account", recordText);

            }
        }
        User loginUser = this.getLoginUser();
        final Collection<ChangeLog> changeLogs = changeLog(originalAccount, account, loginUser);
        account = baseService.makePersistent(account);
        this.setId(account.getId());
        this.setSaveFlag("true");
        if (record1 != null) {
            record1.setOuterId(account.getId());
            recordService.makePersistent(record1);
        }
        if (record2 != null) {
            recordService.makePersistent(record2);
        }
        if (originalAccount != null) {
            taskExecutor.execute(new Runnable() {

                public void run() {
                    batchInserChangeLogs(changeLogs);
                }
            });
        }
        if (flag) {
            response2Json(account.getId(), true, "新商场添加成功!");
        } else {
            response2Json(account.getId(), true, "商场更新成功!");
        }
        return null;
    }

    /**
     * 对比新旧ACCOUNT 存入MAP<字段名,Map<新值,旧值>>
     */

    private Map<String, Map<String, String>> compareAccount(Account newAccount, Account oldAccount) {
        if (newAccount == null || oldAccount == null) {
            return null;
        }
        Map<String, Map<String, String>> resultMap = new HashMap<String, Map<String, String>>();
        try {
            Map<String, String> fieldValueMap = null;
            String oldName = oldAccount.getName() != null ? oldAccount.getName() : "";
            String newName = newAccount.getName() != null ? newAccount.getName() : "";

            String oldAdvert_level = oldAccount.getAdvert_level() != null ? oldAccount.getAdvert_level() : "";
            String newAdvert_level = newAccount.getAdvert_level() != null ? newAccount.getAdvert_level() : "";

            String oldMallbd_level = oldAccount.getMallbd_level() != null ? oldAccount.getMallbd_level() : "";
            String newMallbd_level = newAccount.getMallbd_level() != null ? newAccount.getMallbd_level() : "";

            String oldAccount_type = oldAccount.getAccount_type().getLabel_zh_CN() != null ? oldAccount.getAccount_type().getLabel_zh_CN()
                                                                                             + "" : "";
            String newAccount_type = newAccount.getAccount_type().getLabel_zh_CN() != null ? newAccount.getAccount_type().getLabel_zh_CN()
                                                                                             + "" : "";

            String oldProvince = oldAccount.getProvince() != null ? oldAccount.getProvince() : "";
            String newProvince = newAccount.getProvince() != null ? newAccount.getProvince() : "";

            String oldCity = oldAccount.getCity() != null ? oldAccount.getCity() : "";
            String newCity = newAccount.getCity() != null ? newAccount.getCity() : "";

            String oldDistrict = oldAccount.getDistrict() != null ? oldAccount.getDistrict() : "";
            String newDistrict = newAccount.getDistrict() != null ? newAccount.getDistrict() : "";

            String oldMallCircle = oldAccount.getMall_circle() != null ? oldAccount.getMall_circle() : "";
            String newMallCircle = newAccount.getMall_circle() != null ? newAccount.getMall_circle() : "";

            String oldAddress = oldAccount.getAddress() != null ? oldAccount.getAddress() : "";
            String newAddress = newAccount.getAddress() != null ? newAccount.getAddress() : "";

            String oldMall_acreage = oldAccount.getMall_acreage() != null ? oldAccount.getMall_acreage() + "" : "";
            String newMall_acreage = newAccount.getMall_acreage() != null ? newAccount.getMall_acreage() + "" : "";

            String oldMerchant_num = oldAccount.getMerchant_num() != null ? oldAccount.getMerchant_num() + "" : "";
            String newMerchant_num = newAccount.getMerchant_num() != null ? newAccount.getMerchant_num() + "" : "";

            String oldDay_people_flow = oldAccount.getDay_people_flow() != null ? oldAccount.getDay_people_flow() + "" : "";
            String newDay_people_flow = newAccount.getDay_people_flow() != null ? newAccount.getDay_people_flow() + "" : "";

            String oldPeak_people_flow = oldAccount.getPeak_people_flow() != null ? oldAccount.getPeak_people_flow()
                                                                                    + "" : "";
            String newPeak_people_flow = newAccount.getPeak_people_flow() != null ? newAccount.getPeak_people_flow()
                                                                                    + "" : "";

            String oldYear_sales = oldAccount.getYear_sales() != null ? oldAccount.getYear_sales() + "" : "";
            String newYear_sales = newAccount.getYear_sales() != null ? newAccount.getYear_sales() + "" : "";

            String oldFloors = oldAccount.getFloors() != null ? oldAccount.getFloors() + "" : "";
            String newFloors = newAccount.getFloors() != null ? newAccount.getFloors() + "" : "";

            String oldMemo = oldAccount.getMemo() != null ? oldAccount.getMemo() : "";
            String newMemo = newAccount.getMemo() != null ? newAccount.getMemo() : "";

            if (!oldName.equals(newName)) {
                fieldValueMap = new HashMap<String, String>();
                fieldValueMap.put(newName, oldName);
                resultMap.put("商场名", fieldValueMap);
            }

            if (!oldAdvert_level.equals(newAdvert_level)) {
                fieldValueMap = new HashMap<String, String>();
                fieldValueMap.put(newAdvert_level, oldAdvert_level);
                resultMap.put("广告等级", fieldValueMap);
            }

            if (!oldFloors.equals(newFloors)) {
                fieldValueMap = new HashMap<String, String>();
                fieldValueMap.put(newFloors, oldFloors);
                resultMap.put("楼层", fieldValueMap);
            }

            if (!oldMallbd_level.equals(newMallbd_level)) {
                fieldValueMap = new HashMap<String, String>();
                fieldValueMap.put(newMallbd_level, oldMallbd_level);
                resultMap.put("商场bd等级", fieldValueMap);
            }

            if (!oldAccount_type.equals(newAccount_type)) {
                fieldValueMap = new HashMap<String, String>();
                fieldValueMap.put(newAccount_type, oldAccount_type);
                resultMap.put("商场属性", fieldValueMap);
            }

            if (!oldProvince.equals(newProvince)) {
                fieldValueMap = new HashMap<String, String>();
                fieldValueMap.put(newProvince, oldProvince);
                resultMap.put("省", fieldValueMap);
            }

            if (!oldCity.equals(newCity)) {
                fieldValueMap = new HashMap<String, String>();
                fieldValueMap.put(newCity, oldCity);
                resultMap.put("城市", fieldValueMap);
            }

            if (!oldDistrict.equals(newDistrict)) {
                fieldValueMap = new HashMap<String, String>();
                fieldValueMap.put(newDistrict, oldDistrict);
                resultMap.put("区", fieldValueMap);
            }

            if (!oldMallCircle.equals(newMallCircle)) {
                fieldValueMap = new HashMap<String, String>();
                fieldValueMap.put(newMallCircle, oldMallCircle);
                resultMap.put("商圈", fieldValueMap);
            }

            if (!oldAddress.equals(newAddress)) {
                fieldValueMap = new HashMap<String, String>();
                fieldValueMap.put(newAddress, oldAddress);
                resultMap.put("商场地址", fieldValueMap);
            }

            if (!oldMall_acreage.equals(newMall_acreage)) {
                fieldValueMap = new HashMap<String, String>();
                fieldValueMap.put(newMall_acreage, oldMall_acreage);
                resultMap.put("营业面积", fieldValueMap);
            }

            if (!oldMerchant_num.equals(newMerchant_num)) {
                fieldValueMap = new HashMap<String, String>();
                fieldValueMap.put(newMerchant_num, oldMerchant_num);
                resultMap.put("商户数量", fieldValueMap);
            }

            if (!oldDay_people_flow.equals(newDay_people_flow)) {
                fieldValueMap = new HashMap<String, String>();
                fieldValueMap.put(newDay_people_flow, oldDay_people_flow);
                resultMap.put("日均人流量", fieldValueMap);
            }

            if (!oldPeak_people_flow.equals(newPeak_people_flow)) {
                fieldValueMap = new HashMap<String, String>();
                fieldValueMap.put(newPeak_people_flow, oldPeak_people_flow);
                resultMap.put("峰值人流量", fieldValueMap);
            }

            if (!oldYear_sales.equals(newYear_sales)) {
                fieldValueMap = new HashMap<String, String>();
                fieldValueMap.put(newYear_sales, oldYear_sales);
                resultMap.put("年销售额", fieldValueMap);
            }

            if (!oldMemo.equals(newMemo)) {
                fieldValueMap = new HashMap<String, String>();
                fieldValueMap.put(newMemo, oldMemo);
                resultMap.put("备注", fieldValueMap);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 验证添加商场时的必填字段
     */
    private String validateAccountForm() {
        if (StringUtils.isBlank(account.getName())) {
            return "请输入商场名！";
        } else if (getOwnerID() == null) {
            return "请指定该商场的跟进人！";
        } else if (typeID == null) {
            return "请选择商场属性！";
        } else if (StringUtils.isEmpty(account.getProvince())) {
            return "请选择区域！";
        } else if (StringUtils.isEmpty(account.getCity())) {
            return "请选择区域！";
        } else if (StringUtils.isEmpty(account.getMall_circle())) {
            return "请输入商圈名！";
        } else if (StringUtils.isEmpty(account.getAddress())) {
            return "请输入商场详细地址！";
        }
        return null;
    }

    /**
     * Batch update change log
     * 
     * @param changeLogs change log collection
     */
    private void batchInserChangeLogs(Collection<ChangeLog> changeLogs) {
        changeLogService.batchUpdate(changeLogs);
    }

    /**
     * Mass update entity record information
     */
    public String massUpdate() throws Exception {
        saveEntity();
        String[] fieldNames = this.massUpdate;
        if (fieldNames != null) {
            String[] selectIDArray = this.seleteIDs.split(",");
            Collection<Account> accounts = new ArrayList<Account>();
            final User loginUser = this.getLoginUser();
            User user = userService.getEntityById(User.class, loginUser.getId());
            final List<Account> originalAccounts = new ArrayList<Account>();
            final List<Account> currentAccounts = new ArrayList<Account>();
            for (String IDString : selectIDArray) {
                int id = Integer.parseInt(IDString);
                Account accountInstance = this.baseService.getEntityById(Account.class, id);
                Account originalAccount = accountInstance.clone();
                for (String fieldName : fieldNames) {
                    Object value = BeanUtil.getFieldValue(account, fieldName);
                    BeanUtil.setFieldValue(accountInstance, fieldName, value);
                }
                accountInstance.setUpdated_by(user);
                accountInstance.setUpdated_on(new Date());

                originalAccounts.add(originalAccount);
                currentAccounts.add(accountInstance);
                accounts.add(accountInstance);
            }

            if (accounts.size() > 0) {
                this.baseService.batchUpdate(accounts);
                final Collection<ChangeLog> allChangeLogs = genBAChangeLog(originalAccounts, currentAccounts, loginUser);
                taskExecutor.execute(new Runnable() {

                    public void run() {
                        batchInserChangeLogs(allChangeLogs);
                    }
                });
            }
        }
        return SUCCESS;
    }

    /**
     * 更新客户意向
     */
    public String updateIntent() throws Exception {
        if (NumberUtils.toInt(accountIntent) == 0) {
            addActionMessage("请求参数不合法");
            return INPUT;
        }
        // saveEntity();
        String oldIntent = null;
        String newIntent = null;
        String recordText = "";
        Record r = null;
        List<Account> list = new ArrayList<Account>();
        if (StringUtils.isNotBlank(seleteIDs)) {
            String[] selectIDArray = this.seleteIDs.split(",");
            for (String id : selectIDArray) {
                Account account = this.baseService.getEntityById(Account.class, NumberUtils.toInt(id));
                oldIntent = account.getIntentName();
                account.setAccountIntent(Integer.valueOf(accountIntent));
                newIntent = account.getIntentName();
                this.updateBaseInfo(account);
                list.add(account);
                recordText = String.format("商场【%s】客户意向由%s转为%s", account.getName(), oldIntent, newIntent);
                r = getRecordOjb(RecordTypeEnum.INTENT_CHANGE, null, null, null, 0, 0, 0, null, "account", recordText);
                recordService.makePersistent(r);
            }
        }
        baseService.batchUpdate(list);
        response2Json(null, true, "更新成功");
        return SUCCESS;
    }

    /**
     * 更新拜访情况
     * 
     * @return
     */
    public String updateVisit() throws Exception {
        if (NumberUtils.toInt(accountVisit) == 0) {
            addActionMessage("请求参数不合法");
            return INPUT;
        }
        Record r = null;
        String recordText = "";
        String oldVisit = "";
        String newVisit = "";
        List<Account> list = new ArrayList<Account>();
        if (StringUtils.isNotBlank(seleteIDs)) {
            String[] selectIDArray = this.seleteIDs.split(",");
            for (String id : selectIDArray) {
                Account account = this.baseService.getEntityById(Account.class, Integer.valueOf(id));
                oldVisit = account.getVisitName();
                account.setAccountVisit(Integer.valueOf(accountVisit));
                newVisit = account.getVisitName();
                this.updateBaseInfo(account);
                list.add(account);
                recordText = String.format("商场【%s】拜访进度由%s转为%s", account.getName(), oldVisit, newVisit);
                r = getRecordOjb(RecordTypeEnum.VISIT_CHANGE, null, null, null, 0, 0, 0, null, "account", recordText);
                recordService.makePersistent(r);
            }
        }
        baseService.batchUpdate(list);
        response2Json(null, true, "更新成功");
        return SUCCESS;
    }

    /**
     * Generates change log for batch account update
     * 
     * @param originalAccount original account
     * @param account current accounts
     * @param loginUser current login user
     * @return change log collection
     */
    private Collection<ChangeLog> genBAChangeLog(List<Account> originalAccounts, List<Account> currentAccounts,
                                                 User loginUser) {
        Collection<ChangeLog> allChangeLogs = new ArrayList<ChangeLog>();
        Account originalAccount = null;
        Account currentAccount = null;
        for (int i = 0; i < originalAccounts.size(); i++) {
            originalAccount = originalAccounts.get(i);
            currentAccount = currentAccounts.get(i);
            Collection<ChangeLog> changeLogs = changeLog(originalAccount, currentAccount, loginUser);
            allChangeLogs.addAll(changeLogs);
        }
        return allChangeLogs;
    }

    /**
     * Saves entity field
     * 
     * @return original account record
     * @throws Exception
     */
    private Account saveEntity() throws Exception {
        Account originalAccount = null;
        if (account.getId() == null) {
            UserUtil.permissionCheck("create_account");
            account.setAccountIntent(0);
            account.setAccountVisit(0);
            account.setAssigned_date(new Date());
        } else {
            UserUtil.permissionCheck("update_account");
            originalAccount = baseService.getEntityById(Account.class, account.getId());
            account.setCreated_on(originalAccount.getCreated_on());
            account.setCreated_by(originalAccount.getCreated_by());
            account.setAccountIntent(originalAccount.getAccountIntent());
            account.setAccountVisit(originalAccount.getAccountVisit());
        }
        AccountType type = null;
        if (typeID != null) {
            type = accountTypeService.getOptionById(AccountType.class, typeID);
        }
        account.setAccount_type(type);

        User assignTo = null;
        if (this.getOwnerID() != null) {
            assignTo = userService.getEntityById(User.class, this.getOwnerID());
        }
        if (originalAccount != null) {
            if (!originalAccount.getId().equals(this.getOwnerID())) {
                account.setAssigned_date(new Date());
            }
        }
        account.setOwner(assignTo);
        account.setAssigned_to(assignTo);
        account.setMemo(CommonUtil.stringToJson(account.getMemo()));
        super.updateBaseInfo(account);
        return originalAccount;
    }

    /**
     * Creates change log
     * 
     * @param originalAccount original account record
     * @param account current account record
     * @param loginUser current login user
     * @return change log collections
     */
    private Collection<ChangeLog> changeLog(Account originalAccount, Account account, User loginUser) {
        Collection<ChangeLog> changeLogs = null;
        if (originalAccount != null) {
            String entityName = Account.class.getSimpleName();
            Integer recordID = account.getId();
            changeLogs = new ArrayList<ChangeLog>();

            String oldName = CommonUtil.fromNullToEmpty(originalAccount.getName());
            String newName = CommonUtil.fromNullToEmpty(account.getName());
            createChangeLog(changeLogs, entityName, recordID, "entity.name.label", oldName, newName, loginUser);

            String oldAccountLevel = getOptionValue(originalAccount.getAccount_level());
            String newAccountLevel = getOptionValue(account.getAccount_level());
            createChangeLog(changeLogs, entityName, recordID, "entity.account_level.label", oldAccountLevel,
                            newAccountLevel, loginUser);

            String oldCurrencyName = "";
            Currency oldCurrency = originalAccount.getCurrency();
            if (oldCurrency != null) {
                oldCurrencyName = CommonUtil.fromNullToEmpty(oldCurrency.getName());
            }
            String newCurrencyName = "";
            Currency newCurrency = account.getCurrency();
            if (newCurrency != null) {
                newCurrencyName = CommonUtil.fromNullToEmpty(newCurrency.getName());
            }
            createChangeLog(changeLogs, entityName, recordID, "entity.currency.label", oldCurrencyName,
                            newCurrencyName, loginUser);

            String oldCapital = getOptionValue(originalAccount.getCapital());
            String newCapital = getOptionValue(account.getCapital());
            createChangeLog(changeLogs, entityName, recordID, "entity.capital.label", oldCapital, newCapital, loginUser);

            String oldAnnualRevenue = getOptionValue(originalAccount.getAnnual_revenue());
            String newAnnualRevenue = getOptionValue(account.getAnnual_revenue());
            createChangeLog(changeLogs, entityName, recordID, "menu.annualRevenue.title", oldAnnualRevenue,
                            newAnnualRevenue, loginUser);

            String oldCompanySize = getOptionValue(originalAccount.getCompany_size());
            String newCompanySize = getOptionValue(account.getCompany_size());
            createChangeLog(changeLogs, entityName, recordID, "menu.companySize.title", oldCompanySize, newCompanySize,
                            loginUser);

            String oldAccountType = getOptionValue(originalAccount.getAccount_type());
            String newAccountType = getOptionValue(account.getAccount_type());
            createChangeLog(changeLogs, entityName, recordID, "entity.type.label", oldAccountType, newAccountType,
                            loginUser);

            String oldIndustry = getOptionValue(originalAccount.getIndustry());
            String newIndustry = getOptionValue(account.getIndustry());
            createChangeLog(changeLogs, entityName, recordID, "menu.industry.title", oldIndustry, newIndustry,
                            loginUser);

            String oldOfficePhone = CommonUtil.fromNullToEmpty(originalAccount.getOffice_phone());
            String newOfficePhone = CommonUtil.fromNullToEmpty(account.getOffice_phone());
            createChangeLog(changeLogs, entityName, recordID, "entity.office_phone.label", oldOfficePhone,
                            newOfficePhone, loginUser);

            String oldWebsite = CommonUtil.fromNullToEmpty(originalAccount.getWebsite());
            String newWebsite = CommonUtil.fromNullToEmpty(account.getWebsite());
            createChangeLog(changeLogs, entityName, recordID, "entity.website.label", oldWebsite, newWebsite, loginUser);

            String oldFax = CommonUtil.fromNullToEmpty(originalAccount.getFax());
            String newWFax = CommonUtil.fromNullToEmpty(account.getFax());
            createChangeLog(changeLogs, entityName, recordID, "entity.fax.label", oldFax, newWFax, loginUser);

            String oldBillStreet = CommonUtil.fromNullToEmpty(originalAccount.getBill_street());
            String newBillStreet = CommonUtil.fromNullToEmpty(account.getBill_street());
            createChangeLog(changeLogs, entityName, recordID, "entity.billing_street.label", oldBillStreet,
                            newBillStreet, loginUser);

            String oldBillState = CommonUtil.fromNullToEmpty(originalAccount.getBill_state());
            String newBillState = CommonUtil.fromNullToEmpty(account.getBill_state());
            createChangeLog(changeLogs, entityName, recordID, "entity.billing_state.label", oldBillState, newBillState,
                            loginUser);

            String oldBillPostalCode = CommonUtil.fromNullToEmpty(originalAccount.getBill_postal_code());
            String newBillPostalCode = CommonUtil.fromNullToEmpty(account.getBill_postal_code());
            createChangeLog(changeLogs, entityName, recordID, "entity.billing_postal_code.label", oldBillPostalCode,
                            newBillPostalCode, loginUser);

            String oldBillCountry = CommonUtil.fromNullToEmpty(originalAccount.getBill_country());
            String newBillCountry = CommonUtil.fromNullToEmpty(account.getBill_country());
            createChangeLog(changeLogs, entityName, recordID, "entity.billing_country.label", oldBillCountry,
                            newBillCountry, loginUser);

            String oldShipStreet = CommonUtil.fromNullToEmpty(originalAccount.getShip_street());
            String newShipStreet = CommonUtil.fromNullToEmpty(account.getShip_street());
            createChangeLog(changeLogs, entityName, recordID, "entity.shipping_street.label", oldShipStreet,
                            newShipStreet, loginUser);

            String oldShipState = CommonUtil.fromNullToEmpty(originalAccount.getShip_state());
            String newShipState = CommonUtil.fromNullToEmpty(account.getShip_state());
            createChangeLog(changeLogs, entityName, recordID, "entity.shipping_state.label", oldShipState,
                            newShipState, loginUser);

            String oldShipPostalCode = CommonUtil.fromNullToEmpty(originalAccount.getShip_postal_code());
            String newShipPostalCode = CommonUtil.fromNullToEmpty(account.getShip_postal_code());
            createChangeLog(changeLogs, entityName, recordID, "entity.shipping_postal_code.label", oldShipPostalCode,
                            newShipPostalCode, loginUser);

            String oldShipCountry = CommonUtil.fromNullToEmpty(originalAccount.getShip_country());
            String newShipCountry = CommonUtil.fromNullToEmpty(account.getShip_country());
            createChangeLog(changeLogs, entityName, recordID, "entity.shipping_country.label", oldShipCountry,
                            newShipCountry, loginUser);

            String oldEmail = CommonUtil.fromNullToEmpty(originalAccount.getEmail());
            String newEmail = CommonUtil.fromNullToEmpty(account.getEmail());
            createChangeLog(changeLogs, entityName, recordID, "entity.email.label", oldEmail, newEmail, loginUser);

            String oldAccounNature = getOptionValue(originalAccount.getAccount_nature());
            String newAccounNature = getOptionValue(account.getAccount_nature());
            createChangeLog(changeLogs, entityName, recordID, "entity.account_nature.label", oldAccounNature,
                            newAccounNature, loginUser);

            String oldLegalRepresentative = CommonUtil.fromNullToEmpty(originalAccount.getLegal_representative());
            String newLegalRepresentative = CommonUtil.fromNullToEmpty(account.getLegal_representative());
            createChangeLog(changeLogs, entityName, recordID, "entity.legal_representative.label",
                            oldLegalRepresentative, newLegalRepresentative, loginUser);

            String oldBusinessScope = CommonUtil.fromNullToEmpty(originalAccount.getBusiness_scope());
            String newBusinessScope = CommonUtil.fromNullToEmpty(account.getBusiness_scope());
            createChangeLog(changeLogs, entityName, recordID, "entity.business_scope.label", oldBusinessScope,
                            newBusinessScope, loginUser);

            SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATE_FORMAT);
            String oldCreateDateValue = "";
            Date oldCreateDate = originalAccount.getCreate_date();
            if (oldCreateDate != null) {
                oldCreateDateValue = dateFormat.format(oldCreateDate);
            }
            String newCreateDateValue = "";
            Date newCreateDate = account.getCreate_date();
            if (newCreateDate != null) {
                newCreateDateValue = dateFormat.format(newCreateDate);
            }
            createChangeLog(changeLogs, entityName, recordID, "entity.create_date.label", oldCreateDateValue,
                            newCreateDateValue, loginUser);

            String oldCredit = CommonUtil.fromNullToEmpty(originalAccount.getCredit());
            String newCredit = CommonUtil.fromNullToEmpty(account.getCredit());
            createChangeLog(changeLogs, entityName, recordID, "entity.credit.label", oldCredit, newCredit, loginUser);

            String oldReputation = CommonUtil.fromNullToEmpty(originalAccount.getReputation());
            String newReputation = CommonUtil.fromNullToEmpty(account.getReputation());
            createChangeLog(changeLogs, entityName, recordID, "entity.reputation.label", oldReputation, newReputation,
                            loginUser);

            String oldMarketPosition = CommonUtil.fromNullToEmpty(originalAccount.getMarket_position());
            String newMarketPosition = CommonUtil.fromNullToEmpty(account.getMarket_position());
            createChangeLog(changeLogs, entityName, recordID, "entity.market_position.label", oldMarketPosition,
                            newMarketPosition, loginUser);

            String oldDevelopmentPotential = CommonUtil.fromNullToEmpty(originalAccount.getDevelopment_potential());
            String newDevelopmentPotential = CommonUtil.fromNullToEmpty(account.getDevelopment_potential());
            createChangeLog(changeLogs, entityName, recordID, "entity.development_potential.label",
                            oldDevelopmentPotential, newDevelopmentPotential, loginUser);

            String oldOperationalCharacteristics = CommonUtil.fromNullToEmpty(originalAccount.getOperational_characteristics());
            String newOperationalCharacteristics = CommonUtil.fromNullToEmpty(account.getOperational_characteristics());
            createChangeLog(changeLogs, entityName, recordID, "entity.operational_characteristics.label",
                            oldOperationalCharacteristics, newOperationalCharacteristics, loginUser);

            String oldOperationalDirection = CommonUtil.fromNullToEmpty(originalAccount.getOperational_direction());
            String newOperationalDirection = CommonUtil.fromNullToEmpty(account.getOperational_direction());
            createChangeLog(changeLogs, entityName, recordID, "entity.operational_direction.label",
                            oldOperationalDirection, newOperationalDirection, loginUser);

            String oldNotes = CommonUtil.fromNullToEmpty(originalAccount.getNotes());
            String newNotes = CommonUtil.fromNullToEmpty(account.getNotes());
            createChangeLog(changeLogs, entityName, recordID, "entity.notes.label", oldNotes, newNotes, loginUser);

            String oldSicCode = CommonUtil.fromNullToEmpty(originalAccount.getSic_code());
            String newSicCode = CommonUtil.fromNullToEmpty(account.getSic_code());
            createChangeLog(changeLogs, entityName, recordID, "account.sic_code.label", oldSicCode, newSicCode,
                            loginUser);

            String oldTicketSymbol = CommonUtil.fromNullToEmpty(originalAccount.getTicket_symbol());
            String newTicketSymbol = CommonUtil.fromNullToEmpty(account.getTicket_symbol());
            createChangeLog(changeLogs, entityName, recordID, "account.ticket_symbol.label", oldTicketSymbol,
                            newTicketSymbol, loginUser);

            String oldManagerName = "";
            Account oldManager = originalAccount.getManager();
            if (oldManager != null) {
                oldManagerName = CommonUtil.fromNullToEmpty(oldManager.getName());
            }
            String newManagerName = "";
            Account newManager = account.getManager();
            if (newManager != null) {
                newManagerName = CommonUtil.fromNullToEmpty(newManager.getName());
            }
            createChangeLog(changeLogs, entityName, recordID, "account.manager.label", oldManagerName, newManagerName,
                            loginUser);

            String oldAssignedToName = "";
            User oldAssignedTo = originalAccount.getAssigned_to();
            if (oldAssignedTo != null) {
                oldAssignedToName = oldAssignedTo.getName();
            }
            String newAssignedToName = "";
            User newAssignedTo = account.getAssigned_to();
            if (newAssignedTo != null) {
                newAssignedToName = newAssignedTo.getName();
            }
            createChangeLog(changeLogs, entityName, recordID, "entity.assigned_to.label",
                            CommonUtil.fromNullToEmpty(oldAssignedToName),
                            CommonUtil.fromNullToEmpty(newAssignedToName), loginUser);
        }
        return changeLogs;
    }

    /**
     * Gets the entity.
     * 
     * @return the SUCCESS result
     */
    public String get() throws Exception {
        if (this.getId() != null) {
            UserUtil.permissionCheck("view_account");
            String url = "/jsp/crm/listAccount.jsp";
            // 检查下该id是否是当前用户自己的
            account = baseService.getEntityById(Account.class, this.getId());
            if (!isAdminRole(getLoginUser())) {
                if (!account.getAssigned_to().getId().equals(getLoginUser().getId())) {
                    User reportTo = account.getAssigned_to().getReport_to();
                    if (reportTo != null) {
                        if (!reportTo.getId().equals(getLoginUser().getId())) {
                            redirectUrl(url);
                            return null;
                        }
                    } else {
                        redirectUrl(url);
                        return null;
                    }
                }
            }

            advertLevel = account.getAdvert_level();
            mallLevel = account.getMallbd_level();
            UserUtil.scopeCheck(account, "scope_account");
            AccountLevel accountLevel = account.getAccount_level();
            if (accountLevel != null) {
                accountLevelID = accountLevel.getId();
            }
            AccountType type = account.getAccount_type();
            if (type != null) {
                typeID = type.getId();
            }
            Currency currency = account.getCurrency();
            if (currency != null) {
                currencyID = currency.getId();
            }
            AnnualRevenue annualRevenue = account.getAnnual_revenue();
            if (annualRevenue != null) {
                annualRevenueID = annualRevenue.getId();
            }
            Capital capital = account.getCapital();
            if (capital != null) {
                capitalID = capital.getId();
            }
            CompanySize companySize = account.getCompany_size();
            if (companySize != null) {
                companySizeID = companySize.getId();
            }
            AccountNature accountNature = account.getAccount_nature();
            if (accountNature != null) {
                accountNatureID = accountNature.getId();
            }
            Industry industry = account.getIndustry();
            if (industry != null) {
                industryID = industry.getId();
            }

            User assignedTo = account.getAssigned_to();
            if (assignedTo != null) {
                this.setAssignedToID(assignedTo.getId());
                this.setAssignedToText(assignedTo.getLast_name());
            }

            Account manager = account.getManager();
            if (manager != null) {
                managerID = manager.getId();
                managerText = manager.getName();
            }

            Date create_date = account.getCreate_date();
            SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATE_FORMAT);
            if (create_date != null) {
                createDate = dateFormat.format(create_date);
            }
            recordVO = getStatisticsRecordVO(account);
            this.getBaseInfo(account, Account.class.getSimpleName(), Constant.CRM_NAMESPACE);

        } else {
            this.initBaseInfo();
        }

        if (getId() != null) {
            // 得到主要联系人
            String hql = "from Contact where account = " + this.getId() + "AND contact_default = 1 order by id desc";
            mainContactList = contactService.findByHQL(hql);
            // 其他联系人
            String hql2 = "from Contact where account = " + this.getId() + "AND contact_default != 1 order by id desc";
            otherContactList = contactService.findByHQL(hql2);
            if (otherContactList.isEmpty()) {
                otherContactList = null;
            }

            // 合同列表
            String contractHql = "from Contract where accountId = " + getId();
            contractList = contractService.findByHQL(contractHql);
        }

        if (getId() != null) {
            // 查询该客户下的记录(合同 + 商场跟踪记录)
            String recordHql = "from Record where (tableName = \'account\' AND outerId = " + this.getId() + ")";
            List<Integer> contractIds = new ArrayList<Integer>();
            if (!contractList.isEmpty()) {
                for (Contract c : contractList) {
                    contractIds.add(c.getId());
                }
                recordHql += " or (tableName = \'contract\' AND outerId in (" + StringUtils.join(contractIds, ",")
                             + ") )";
            }
            recordHql += "order by assigned_date desc,id desc";
            List<Record> records = recordService.findByHQL(recordHql);

            List<RecordVO> list = new ArrayList<RecordVO>();
            for (Record r : records) {
                list.add(convert2AccountRecordVO(r));
            }
            setRecordList(list);
        }
        return SUCCESS;
    }

    private boolean isAdminRole(User user) {
        if (user == null) {
            return false;
        }
        Set<Role> roleSet = user.getRoles();
        for (Role r : roleSet) {
            if (r.getName().toLowerCase().contains("admin")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获得跟进统计信息
     */
    private StatisticsRecordVO getStatisticsRecordVO(Account account) {
        if (account == null) {
            return null;
        }
        StatisticsRecordVO vo = new StatisticsRecordVO();
        vo.setAssignToName(account.getAssigned_to().getLast_name());
        SimpleDateFormat format = new SimpleDateFormat(Constant.DATE_SIMPLE_FORMAT);
        vo.setCreateDate(format.format(account.getCreated_on()));
        vo.setIntentName(account.getIntentName());
        vo.setVisitName(account.getVisitName());
        String phoneHql = "select count(id) from Record where visitType = \'PHONE_VISIT\' AND tableName = \'account\' AND outerId = ?";
        long phoneNum = recordService.countsByParams(phoneHql, new Integer[] { account.getId() });
        vo.setPhoneVisitNum(phoneNum);
        String dropVisitHql = "select count(id) from Record where visitType = \'DROP_IN_VISIT\' AND tableName = \'account\' AND outerId = ?";
        long dropVisitNum = recordService.countsByParams(dropVisitHql, new Integer[] { account.getId() });
        vo.setDropVisitNum(dropVisitNum);
        Date endDate = account.getAssigned_date();
        Date startDate = account.getCreated_on();
        Integer days = (int) ((endDate.getTime() - startDate.getTime()) / (3600 * 24 * 1000));
        vo.setDays(days);
        return vo;
    }

    /**
     * Gets Account Relation Counts
     * 
     * @return null
     */
    public String getAccountRelationCounts() throws Exception {
        long contactNumber = this.baseService.countsByParams("select count(contact.id) from Contact contact where account.id = ?",
                                                             new Integer[] { this.getId() });
        long opportunityNumber = this.baseService.countsByParams("select count(opportunity.id) from Opportunity opportunity where account.id = ?",
                                                                 new Integer[] { this.getId() });
        long leadNumber = this.baseService.countsByParams("select count(lead.id) from Lead lead where account.id = ?",
                                                          new Integer[] { this.getId() });
        long accountNumber = this.baseService.countsByParams("select count(account.id) from Account account where manager.id = ?",
                                                             new Integer[] { this.getId() });
        long documentNumber = this.baseService.countsByParams("select count(*) from Account account join account.documents where account.id = ?",
                                                              new Integer[] { this.getId() });
        long caseNumber = this.baseService.countsByParams("select count(caseInstance.id) from CaseInstance caseInstance where account.id = ?",
                                                          new Integer[] { this.getId() });
        long taskNumber = this.baseService.countsByParams("select count(task.id) from Task task where related_object='Account' and related_record = ?",
                                                          new Integer[] { this.getId() });

        StringBuilder jsonBuilder = new StringBuilder("");
        jsonBuilder.append("{\"contactNumber\":\"").append(contactNumber).append("\",\"opportunityNumber\":\"").append(opportunityNumber).append("\",\"leadNumber\":\"").append(leadNumber).append("\",\"accountNumber\":\"").append(accountNumber).append("\",\"documentNumber\":\"").append(documentNumber).append("\",\"caseNumber\":\"").append(caseNumber).append("\",\"taskNumber\":\"").append(taskNumber).append("\"}");
        // Returns JSON data back to page
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(jsonBuilder.toString());
        return null;
    }

    /**
     * Prepares the list
     */
    public void prepare() throws Exception {
        ActionContext context = ActionContext.getContext();
        Map<String, Object> session = context.getSession();
        String local = (String) session.get("locale");
        this.accountLevels = accountLevelService.getOptions(AccountLevel.class.getSimpleName(), local);
        this.types = accountTypeService.getOptions(AccountType.class.getSimpleName(), local);
        this.annualRevenues = annualRevenueService.getOptions(AnnualRevenue.class.getSimpleName(), local);
        this.capitals = capitalService.getOptions(Capital.class.getSimpleName(), local);
        this.companySizes = companySizeService.getOptions(CompanySize.class.getSimpleName(), local);
        this.accountNatures = accountNatureService.getOptions(AccountNature.class.getSimpleName(), local);
        this.industries = industryService.getOptions(Industry.class.getSimpleName(), local);
        this.currencies = currencyService.getAllObjects(Currency.class.getSimpleName());
    }

    /**
     * @param baseService the baseService to set
     */
    public void setBaseService(IBaseService<Account> baseService) {
        this.baseService = baseService;
    }

    /**
     * @param accountTypeService the accountTypeService to set
     */
    public void setAccountTypeService(IOptionService<AccountType> accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    /**
     * @param userService the userService to set
     */
    public void setUserService(IBaseService<User> userService) {
        this.userService = userService;
    }

    /**
     * @return the account
     */
    public Account getAccount() {
        return account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * @return the types
     */
    public List<AccountType> getTypes() {
        return types;
    }

    /**
     * @param types the types to set
     */
    public void setTypes(List<AccountType> types) {
        this.types = types;
    }

    /**
     * @param industries the industries to set
     */
    public void setIndustries(List<Industry> industries) {
        this.industries = industries;
    }

    /**
     * @return the typeID
     */
    public Integer getTypeID() {
        return typeID;
    }

    /**
     * @param typeID the typeID to set
     */
    public void setTypeID(Integer typeID) {
        this.typeID = typeID;
    }

    /**
     * @return the industryID
     */
    public Integer getIndustryID() {
        return industryID;
    }

    /**
     * @param industryID the industryID to set
     */
    public void setIndustryID(Integer industryID) {
        this.industryID = industryID;
    }

    /**
     * @return the campaignID
     */
    public Integer getCampaignID() {
        return campaignID;
    }

    /**
     * @param campaignID the campaignID to set
     */
    public void setCampaignID(Integer campaignID) {
        this.campaignID = campaignID;
    }

    /**
     * @return the manageID
     */
    public Integer getManagerID() {
        return managerID;
    }

    /**
     * @param manageID the manageID to set
     */
    public void setManagerID(Integer managerID) {
        this.managerID = managerID;
    }

    /**
     * @param campaignService the campaignService to set
     */
    public void setCampaignService(IBaseService<Campaign> campaignService) {
        this.campaignService = campaignService;
    }

    /**
     * @param targetListService the targetListService to set
     */
    public void setTargetListService(IBaseService<TargetList> targetListService) {
        this.targetListService = targetListService;
    }

    /**
     * @param documentService the documentService to set
     */
    public void setDocumentService(IBaseService<Document> documentService) {
        this.documentService = documentService;
    }

    /**
     * @return the managerText
     */
    public String getManagerText() {
        return managerText;
    }

    /**
     * @param managerText the managerText to set
     */
    public void setManagerText(String managerText) {
        this.managerText = managerText;
    }

    /**
     * @param industryService the industryService to set
     */
    public void setIndustryService(IOptionService<Industry> industryService) {
        this.industryService = industryService;
    }

    /**
     * @param changeLogService the changeLogService to set
     */
    public void setChangeLogService(IBaseService<ChangeLog> changeLogService) {
        this.changeLogService = changeLogService;
    }

    /**
     * @return the taskExecutor
     */
    public TaskExecutor getTaskExecutor() {
        return taskExecutor;
    }

    /**
     * @param taskExecutor the taskExecutor to set
     */
    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    /**
     * @param accountLevelService the accountLevelService to set
     */
    public void setAccountLevelService(IOptionService<AccountLevel> accountLevelService) {
        this.accountLevelService = accountLevelService;
    }

    /**
     * @param capitalService the capitalService to set
     */
    public void setCapitalService(IOptionService<Capital> capitalService) {
        this.capitalService = capitalService;
    }

    /**
     * @param annualRevenueService the annualRevenueService to set
     */
    public void setAnnualRevenueService(IOptionService<AnnualRevenue> annualRevenueService) {
        this.annualRevenueService = annualRevenueService;
    }

    /**
     * @param companySizeService the companySizeService to set
     */
    public void setCompanySizeService(IOptionService<CompanySize> companySizeService) {
        this.companySizeService = companySizeService;
    }

    /**
     * @param accountNatureService the accountNatureService to set
     */
    public void setAccountNatureService(IOptionService<AccountNature> accountNatureService) {
        this.accountNatureService = accountNatureService;
    }

    /**
     * @return the accountLevels
     */
    public List<AccountLevel> getAccountLevels() {
        return accountLevels;
    }

    /**
     * @param accountLevels the accountLevels to set
     */
    public void setAccountLevels(List<AccountLevel> accountLevels) {
        this.accountLevels = accountLevels;
    }

    /**
     * @return the capitals
     */
    public List<Capital> getCapitals() {
        return capitals;
    }

    /**
     * @param capitals the capitals to set
     */
    public void setCapitals(List<Capital> capitals) {
        this.capitals = capitals;
    }

    /**
     * @return the annualRevenues
     */
    public List<AnnualRevenue> getAnnualRevenues() {
        return annualRevenues;
    }

    /**
     * @param annualRevenues the annualRevenues to set
     */
    public void setAnnualRevenues(List<AnnualRevenue> annualRevenues) {
        this.annualRevenues = annualRevenues;
    }

    /**
     * @return the companySizes
     */
    public List<CompanySize> getCompanySizes() {
        return companySizes;
    }

    /**
     * @param companySizes the companySizes to set
     */
    public void setCompanySizes(List<CompanySize> companySizes) {
        this.companySizes = companySizes;
    }

    /**
     * @return the accountNatures
     */
    public List<AccountNature> getAccountNatures() {
        return accountNatures;
    }

    /**
     * @param accountNatures the accountNatures to set
     */
    public void setAccountNatures(List<AccountNature> accountNatures) {
        this.accountNatures = accountNatures;
    }

    /**
     * @return the accountLevelID
     */
    public Integer getAccountLevelID() {
        return accountLevelID;
    }

    /**
     * @param accountLevelID the accountLevelID to set
     */
    public void setAccountLevelID(Integer accountLevelID) {
        this.accountLevelID = accountLevelID;
    }

    /**
     * @return the capitalID
     */
    public Integer getCapitalID() {
        return capitalID;
    }

    /**
     * @param capitalID the capitalID to set
     */
    public void setCapitalID(Integer capitalID) {
        this.capitalID = capitalID;
    }

    /**
     * @return the annualRevenueID
     */
    public Integer getAnnualRevenueID() {
        return annualRevenueID;
    }

    /**
     * @param annualRevenueID the annualRevenueID to set
     */
    public void setAnnualRevenueID(Integer annualRevenueID) {
        this.annualRevenueID = annualRevenueID;
    }

    /**
     * @return the companySizeID
     */
    public Integer getCompanySizeID() {
        return companySizeID;
    }

    /**
     * @param companySizeID the companySizeID to set
     */
    public void setCompanySizeID(Integer companySizeID) {
        this.companySizeID = companySizeID;
    }

    /**
     * @return the accountNatureID
     */
    public Integer getAccountNatureID() {
        return accountNatureID;
    }

    /**
     * @param accountNatureID the accountNatureID to set
     */
    public void setAccountNatureID(Integer accountNatureID) {
        this.accountNatureID = accountNatureID;
    }

    /**
     * @param currencies the currencies to set
     */
    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    /**
     * @param currencyService the currencyService to set
     */
    public void setCurrencyService(IBaseService<Currency> currencyService) {
        this.currencyService = currencyService;
    }

    /**
     * @return the currencyID
     */
    public Integer getCurrencyID() {
        return currencyID;
    }

    /**
     * @param currencyID the currencyID to set
     */
    public void setCurrencyID(Integer currencyID) {
        this.currencyID = currencyID;
    }

    /**
     * @return the createDate
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getAccountIntent() {
        return accountIntent;
    }

    public void setAccountIntent(String accountIntent) {
        this.accountIntent = accountIntent;
    }

    public String getAccountVisit() {
        return accountVisit;
    }

    public void setAccountVisit(String accountVisit) {
        this.accountVisit = accountVisit;
    }

    public void setRecordService(IBaseService<Record> recordService) {
        this.recordService = recordService;
    }

    public List<RecordVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<RecordVO> recordList) {
        this.recordList = recordList;
    }

    private RecordVO convert2AccountRecordVO(Record record) {
        if (record != null) {
            RecordVO r = new RecordVO();
            r.setCreateOnName(record.getCreatedOnName());
            r.setCreateByName(record.getCreated_by().getLast_name());
            r.setAssignedDateName(record.getAssignDateName());
            r.setTypeShowName(record.getTypeText());
            r.setRecordText(record.getMemo());
            r.setVisitNote(record.getVisitNote());
            if (record.getContactId() != null) {
                Contact ca = contactService.getEntityById(Contact.class, record.getContactId());
                if (ca != null) {
                    r.setContactName(ca.getName());
                }
            }
            return r;
        }
        return null;
    }

    public IBaseService<Contact> getContactService() {
        return contactService;
    }

    public void setContactService(IBaseService<Contact> contactService) {
        this.contactService = contactService;
    }

    public StatisticsRecordVO getRecordVO() {
        return recordVO;
    }

    public void setRecordVO(StatisticsRecordVO recordVO) {
        this.recordVO = recordVO;
    }

    public String getAdvertLevel() {
        return advertLevel;
    }

    public void setAdvertLevel(String advertLevel) {
        this.advertLevel = advertLevel;
    }

    public String getMallLevel() {
        return mallLevel;
    }

    public void setMallLevel(String mallLevel) {
        this.mallLevel = mallLevel;
    }

    public List<Contact> getMainContactList() {
        return mainContactList;
    }

    public void setMainContactList(List<Contact> mainContactList) {
        this.mainContactList = mainContactList;
    }

    public List<Contact> getOtherContactList() {
        return otherContactList;
    }

    public void setOtherContactList(List<Contact> otherContactList) {
        this.otherContactList = otherContactList;
    }

    public IBaseService<Contract> getContractService() {
        return contractService;
    }

    public void setContractService(IBaseService<Contract> contractService) {
        this.contractService = contractService;
    }

    public List<Contract> getContractList() {
        return contractList;
    }

    public void setContractList(List<Contract> contractList) {
        this.contractList = contractList;
    }

    public String getGoon() {
        return goon;
    }

    public void setGoon(String goon) {
        this.goon = goon;
    }

}
