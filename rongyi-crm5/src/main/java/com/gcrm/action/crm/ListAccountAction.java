/**
 * Copyright (C) 2012 - 2013, Grass CRM Studio Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */
package com.gcrm.action.crm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts2.ServletActionContext;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;

import com.gcrm.domain.Account;
import com.gcrm.domain.AccountLevel;
import com.gcrm.domain.AccountNature;
import com.gcrm.domain.AccountType;
import com.gcrm.domain.AnnualRevenue;
import com.gcrm.domain.Campaign;
import com.gcrm.domain.Capital;
import com.gcrm.domain.CompanySize;
import com.gcrm.domain.Contact;
import com.gcrm.domain.Contact_default;
import com.gcrm.domain.Currency;
import com.gcrm.domain.Document;
import com.gcrm.domain.Industry;
import com.gcrm.domain.TargetList;
import com.gcrm.domain.User;
import com.gcrm.exception.ServiceException;
import com.gcrm.service.IBaseService;
import com.gcrm.service.IOptionService;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.gcrm.util.security.UserUtil;
import com.gcrm.vo.SearchCondition;
import com.gcrm.vo.SearchResult;
import com.gcrm.vo.UserVO;

/**
 * Lists Account
 */
public class ListAccountAction extends BaseListAction {

    private static final long             serialVersionUID = -2404576552417042445L;

    private IBaseService<Account>         baseService;
    private IBaseService<Contact>         contactBaseService;
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
    private Account                       account;
    private String                        detailAddress;
    private Integer                       accountTypeId;

    private static final String           CLAZZ            = Account.class.getSimpleName();

    private String                        startAssignDate;                                 // 开始跟进时间
    private String                        endAssignDate;                                   // 结束跟进时间
    private String                        assignTo;                                        // 跟进人
    private String[]                      mallbdLevels;                                    // 商场BD等级
    private String[]                      advertLevels;                                    // 广告等级
    private String[]                      intentNames;                                     // 客户意向
    private String[]                      visitNames;                                      // 拜访进度
    private String[]                      accountTypes;                                    // 客户类型
    private List<User>                    underlingUsers;                                  // 下属

    /**
     * Gets the list data.
     * 
     * @return null
     */
    @Override
    public String list() throws Exception {

        SearchCondition searchCondition = getSearchCondition();
        User user = getLoginUser();
        boolean isAdminRole = isAdminRole(user);
        if (isAdminRole) {
            searchCondition.setCondition(" order by assigned_date desc");
        } else {
            // 自己及下属
            List<Integer> userIdList = new ArrayList<Integer>();
            String hql = "from User where report_to = " + UserUtil.getLoginUser().getId();
            List<User> underlingUsers = userService.findByHQL(hql);
            for (User u : underlingUsers) {
                userIdList.add(u.getId());
            }
            userIdList.add(user.getId());
            String str = "assigned_to in (" + StringUtils.join(userIdList, ",") + ") order by assigned_date desc";
            searchCondition.setCondition(str);
        }

        SearchResult<Account> result = baseService.getPaginationObjects(CLAZZ, searchCondition);
        Iterator<Account> accounts = result.getResult().iterator();
        long totalRecords = result.getTotalRecords();
        getListJson(accounts, totalRecords, null, false);
        return null;
    }

    /**
     * 查询所有商场
     */

    /**
     * Gets the list data. 查询自己及下属的所有客户
     * 
     * @return null
     */
    public String listFull() throws Exception {
        UserUtil.permissionCheck("view_account");

        Map<String, String> fieldTypeMap = new HashMap<String, String>();
        fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
        fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
        User loginUser = UserUtil.getLoginUser();

        SearchCondition searchCondition = getSearchCondition(fieldTypeMap, loginUser.getScope_account(), loginUser);
        SearchResult<Account> result = null;
        if (searchCondition == null) {
            searchCondition = new SearchCondition(1, 15, "id", "desc", "");
            result = new SearchResult<Account>(0, new ArrayList<Account>());
        } else {
            result = baseService.getPaginationObjects(CLAZZ, searchCondition);
        }
        Iterator<Account> accounts = result.getResult().iterator();
        long totalRecords = result.getTotalRecords();
        getListJson(accounts, totalRecords, searchCondition, true);
        return null;
    }

    /**
     * 获得下属名单
     */
    public void listUnderUser() throws Exception {
        // 查询自己的下属
        String hql = "from User where report_to = " + UserUtil.getLoginUser().getId();
        underlingUsers = userService.findByHQL(hql);
        List<UserVO> list = new ArrayList<UserVO>();
        if (underlingUsers == null) {
            return;
        }
        for (User u : underlingUsers) {
            list.add(new UserVO(u.getId(), u.getLast_name()));
        }
        JSONArray array = JSONArray.fromObject(list);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(array.toString());
    }

    protected SearchCondition getSearchCondition(Map<String, String> fieldTypeMap, int scope, User loginUser)
                                                                                                             throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        StringBuilder condition = new StringBuilder("");
        // SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATE_SIMPLE_FORMAT);
        if (StringUtils.isEmpty(assignTo)) {
            condition.append("assigned_to").append(" in ( ").append(String.valueOf(loginUser.getId())).append(" )");
        }
        if (super.getFilters() != null && super.getFilters().trim().length() > 0) {
            advancedSearch(condition);
        } else {
            HashMap parameters = (HashMap) request.getParameterMap();
            Iterator iterator = parameters.keySet().iterator();

            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                String[] value = ((String[]) parameters.get(key));
                // TODO:中文乱码
                // String val = new String(value[0].getBytes("iso8859-1"));
                String val = value[0];
                if (value == null || StringUtils.isEmpty(value[0])) {
                    continue;
                }
                if (!BaseListAction.GRID_FIELD_SET.contains(key)) {
                    if (StringUtils.equals(key, "mallbdLevels")) {
                        if (condition.length() != 0) {
                            condition.append(" AND ");
                        }
                        condition.append("mallbd_level").append(" in (");
                        for (int i = 0; i < value.length; i++) {
                            condition.append("'").append(value[i]).append("'");
                            if (i < (value.length - 1)) {
                                condition.append(",");
                            }
                        }
                        condition.append(")");
                    } else if (StringUtils.equals(key, "advertLevels")) {
                        if (condition.length() != 0) {
                            condition.append(" AND ");
                        }
                        condition.append("advert_level").append(" in (");
                        for (int i = 0; i < value.length; i++) {
                            condition.append("'").append(value[i]).append("'");
                            if (i < (value.length - 1)) {
                                condition.append(",");
                            }
                        }
                        condition.append(")");
                    } else if (StringUtils.equals(key, "intentNames")) {
                        if (condition.length() != 0) {
                            condition.append(" AND ");
                        }
                        condition.append("accountIntent").append(" in (").append(StringUtils.join(value, ",")).append(" )");
                    } else if (StringUtils.equals(key, "visitNames")) {
                        if (condition.length() != 0) {
                            condition.append(" AND ");
                        }
                        condition.append("accountVisit").append(" in (").append(StringUtils.join(value, ",")).append(" )");
                    } else if (StringUtils.equals(key, "accountTypes")) {
                        if (condition.length() != 0) {
                            condition.append(" AND ");
                        }
                        condition.append("account_type").append(" in (").append(StringUtils.join(value, ",")).append(" )");

                    } else if (StringUtils.equals(key, "assignTo")) {
                        if (StringUtils.equalsIgnoreCase("all", val)) {
                            continue;
                        }
                        if (condition.length() != 0) {
                            condition.append(" AND ");
                        }
                        String assignedToStr = null;
                        if (StringUtils.equalsIgnoreCase("self", val)) {
                            // 自己的
                            assignedToStr = String.valueOf(loginUser.getId());

                        } else if (StringUtils.equals(val, "underling")) {
                            // 下属
                            List<Integer> userIdList = new ArrayList<Integer>();
                            String hql = "from User where report_to = " + loginUser.getId();
                            underlingUsers = userService.findByHQL(hql);
                            if (underlingUsers == null || underlingUsers.isEmpty()) {
                                return null;
                            }
                            for (User u : underlingUsers) {
                                userIdList.add(u.getId());
                            }
                            assignedToStr = StringUtils.join(userIdList, ",");
                        } else if (StringUtils.equals(val, "selfAndunderling")) {
                            // 自己及下属
                            List<Integer> userIdList = new ArrayList<Integer>();
                            String hql = "from User where report_to = " + loginUser.getId();
                            underlingUsers = userService.findByHQL(hql);
                            for (User u : underlingUsers) {
                                userIdList.add(u.getId());
                            }
                            userIdList.add(loginUser.getId());
                            assignedToStr = StringUtils.join(userIdList, ",");
                        } else if (NumberUtils.toInt(val) != 0) {
                            // 单个下属
                            assignedToStr = val;
                        }
                        if (assignedToStr != null) {
                            condition.append("assigned_to").append(" in ( ").append(assignedToStr).append(" )");
                        }
                    } else if (StringUtils.equals(key, "detailAddress")) {
                        if (condition.length() != 0) {
                            condition.append(" AND ");
                        }
                        condition.append("(province").append(" like '%").append(val).append("%'");
                        condition.append(" or ").append("city").append(" like '%").append(val).append("%'");
                        condition.append(" or ").append("district").append(" like '%").append(val).append("%'");
                        condition.append(" or ").append("address").append(" like '%").append(val).append("%')");

                    } else if (StringUtils.equals(key, "account.province")) {
                        if (condition.length() != 0) {
                            condition.append(" AND ");
                        }

                        condition.append("(province").append(" like '%").append(val).append("%')");
                    } else if (StringUtils.equals(key, "account.city")) {
                        if (condition.length() != 0) {
                            condition.append(" AND ");
                        }

                        condition.append("(city").append(" like '%").append(val).append("%')");
                    } else if (StringUtils.equals(key, "account.district")) {
                        if (condition.length() != 0) {
                            condition.append(" AND ");
                        }

                        condition.append("(district").append(" like '%").append(val).append("%')");
                    } else if (StringUtils.equals(key, "startAssignDate")) {
                        if (condition.length() != 0) {
                            condition.append(" AND ");
                        }
                        condition.append("updated_on").append(" >= '").append(val).append("' ");
                    } else if (StringUtils.equals(key, "endAssignDate")) {
                        if (condition.length() != 0) {
                            condition.append(" AND ");
                        }
                        condition.append("updated_on").append(" <= '").append(val).append("' ");
                    } else {
                        if (condition.length() != 0) {
                            condition.append(" AND ");
                        }

                        if (NumberUtils.toFloat(val) != 0.0f) {
                            condition.append(key).append(" = ").append(val);
                        } else {
                            if (StringUtils.contains(val, ">")) {
                                int index = val.indexOf(">");
                                String v = val.substring(index + 1);
                                condition.append(key).append(" > ").append(v.trim());
                            } else if (StringUtils.contains(val, "<")) {
                                int index = val.indexOf("<");
                                String v = val.substring(index + 1);
                                condition.append(key).append(" < ").append(v.trim());
                            } else {
                                condition.append(key).append(" like '%").append(val).append("%'");
                            }

                        }
                    }
                }
            }
        }

        // if (scope == Role.OWNER_OR_DISABLED) {
        // if (condition.length() != 0) {
        // condition.append(" AND ");
        // }
        // condition.append("owner = ").append(loginUser.getId());
        // }

        int pageNo = super.getPage();
        if (pageNo == 0) {
            pageNo = 1;
        }

        int pageSize = super.getRows();
        if (pageSize == 0) {
            pageSize = 1;
        }
        super.setSidx("assigned_date");
        super.setSord("desc");
        SearchCondition searchCondition = new SearchCondition(pageNo, pageSize, super.getSidx(), super.getSord(),
                                                              condition.toString());
        return searchCondition;

    }

    /**
     * Gets the list JSON data.
     * 
     * @return list JSON data
     */
    public static void getListJson(Iterator<Account> accounts, long totalRecords, SearchCondition searchCondition,
                                   boolean isList) throws Exception {

        StringBuilder jsonBuilder = new StringBuilder();
        String assignedToName = null;
        jsonBuilder.append(getJsonHeader(totalRecords, searchCondition, isList));
        while (accounts.hasNext()) {
            Account instance = accounts.next();
            int id = instance.getId();
            String name = CommonUtil.stringToJson(CommonUtil.fromNullToEmpty(instance.getName()));
            String detailAddress = CommonUtil.stringToJson(CommonUtil.convert2String(instance.getDetailAddress()));
            String intentName = CommonUtil.convert2String(instance.getIntentName());
            String visitName = CommonUtil.convert2String(instance.getVisitName());
            SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATE_FULL_FORMAT);
            Date assignDate = instance.getAssigned_date();
            String assignedDateName = "";
            if (assignDate != null) {
                assignedDateName = dateFormat.format(assignDate);
            }
            String updateOnName = instance.getUpdatedOnName();
            String advert_level = CommonUtil.convert2String(instance.getAdvert_level());
            String mallbd_level = CommonUtil.convert2String(instance.getMallbd_level());
            String accountTypeName = "";
            if (instance.getAccount_type() != null) {
                accountTypeName = CommonUtil.convert2String(instance.getAccount_type().getLabel_zh_CN());
            }
            String mallAcreage = CommonUtil.convert2String(instance.getMall_acreage());
            String merchant_num = CommonUtil.convert2String(instance.getMerchant_num());
            String day_people_flow = CommonUtil.convert2String(instance.getDay_people_flow());
            String peak_people_flow = CommonUtil.convert2String(instance.getPeak_people_flow());
            String year_sales = CommonUtil.convert2String(instance.getYear_sales());
            String memo = CommonUtil.stringToJson(CommonUtil.convert2String(instance.getMemo()));
            String guanli = getAddRecord(id);
            String createdByName = "";
            User createdBy = instance.getCreated_by();
            if (createdBy != null) {
                createdByName = CommonUtil.fromNullToEmpty(createdBy.getName());
            }
            User user = instance.getAssigned_to();
            if (user != null) {
                assignedToName = CommonUtil.fromNullToEmpty(user.getLast_name());
            } else {
                assignedToName = "";
            }
            if (isList) {
                jsonBuilder.append("{\"cell\":[\"").append(id).append("\",\"").append(name).append("\",\"").append(detailAddress).append("\",\"").append(intentName).append("\"");
                jsonBuilder.append(",\"").append(visitName).append("\",\"").append(assignedToName).append("\",\"").append(assignedDateName).append("\",\"").append(updateOnName).append("\",\"").append(advert_level).append("\",\"").append(mallbd_level).append("\",\"").append(accountTypeName).append("\",\"").append(mallAcreage).append("\",\"").append(merchant_num).append("\",\"").append(day_people_flow).append("\",\"").append(peak_people_flow).append("\",\"").append(year_sales).append("\",\"").append(memo).append("\",\"").append(createdByName).append("\"]}");
            } else {
                jsonBuilder.append("{\"id\":\"").append(id).append("\",\"name\":\"").append(name).append("\",\"guanli\":\"").append(guanli).append("\",\"detailAddress\":\"").append(detailAddress).append("\",\"intentName\":\"").append(intentName).append("\",\"visitName\":\"").append(visitName).append("\",\"assignedToName\":\"").append(assignedToName).append("\",\"assignedDateName\":\"").append(assignedDateName).append("\",\"updateOnName\":\"").append(updateOnName).append("\",\"advert_level\":\"").append(advert_level).append("\",\"mallbd_level\":\"").append(mallbd_level).append("\",\"accountTypeName\":\"").append(accountTypeName).append("\",\"mallAcreage\":\"").append(mallAcreage).append("\",\"merchant_num\":\"").append(merchant_num).append("\",\"day_people_flow\":\"").append(day_people_flow).append("\",\"peak_people_flow\":\"").append(peak_people_flow).append("\",\"year_sales\":\"").append(year_sales).append("\",\"memo\":\"").append(memo).append("\",\"createdByName\":\"").append(createdByName).append("\"}");
            }
            if (accounts.hasNext()) {
                jsonBuilder.append(",");
            }
        }
        jsonBuilder.append("]}");

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(jsonBuilder.toString());
    }

    /**
     * 添加记录的html
     */
    private static String getAddRecord(Integer mallId) {
        String href = "/jsp/crm/editRecord.action?outerId=" + mallId + "&table=account";
        return "<a href=\'" + href + "\' target=\'_blank\' >添加记录</a>";
    }

    /**
     * Selects the entities
     * 
     * @return the SUCCESS result
     */
    public String select() throws ServiceException {
        TargetList targetList = null;
        Document document = null;
        Set<Account> accounts = null;

        if ("TargetList".equals(this.getRelationKey())) {
            targetList = targetListService.getEntityById(TargetList.class, Integer.valueOf(this.getRelationValue()));
            accounts = targetList.getAccounts();
        } else if ("Document".equals(this.getRelationKey())) {
            document = documentService.getEntityById(Document.class, Integer.valueOf(this.getRelationValue()));
            accounts = document.getAccounts();
        }

        if (this.getSeleteIDs() != null) {
            String[] ids = seleteIDs.split(",");
            for (int i = 0; i < ids.length; i++) {
                String selectId = ids[i];
                account = baseService.getEntityById(Account.class, Integer.valueOf(selectId));
                accounts.add(account);
            }
        }

        if ("TargetList".equals(this.getRelationKey())) {
            targetListService.makePersistent(targetList);
        } else if ("Document".equals(this.getRelationKey())) {
            documentService.makePersistent(document);
        }
        return SUCCESS;
    }

    /**
     * Unselects the entities
     * 
     * @return the SUCCESS result
     */
    public String unselect() throws ServiceException {
        TargetList targetList = null;
        Document document = null;
        Set<Account> accounts = null;

        if ("TargetList".equals(this.getRelationKey())) {
            targetList = targetListService.getEntityById(TargetList.class, Integer.valueOf(this.getRelationValue()));
            accounts = targetList.getAccounts();
        } else if ("Document".equals(this.getRelationKey())) {
            document = documentService.getEntityById(Document.class, Integer.valueOf(this.getRelationValue()));
            accounts = document.getAccounts();
        }

        if (this.getSeleteIDs() != null) {
            String[] ids = seleteIDs.split(",");
            Collection<Account> selectedAccounts = new ArrayList<Account>();
            for (int i = 0; i < ids.length; i++) {
                Integer selectId = Integer.valueOf(ids[i]);
                A: for (Account account : accounts) {
                    if (account.getId().intValue() == selectId.intValue()) {
                        selectedAccounts.add(account);
                        break A;
                    }
                }
            }
            accounts.removeAll(selectedAccounts);
        }

        if ("TargetList".equals(this.getRelationKey())) {
            targetListService.makePersistent(targetList);
        } else if ("Document".equals(this.getRelationKey())) {
            documentService.makePersistent(document);
        }
        return SUCCESS;
    }

    /**
     * Gets the related documents.
     * 
     * @return null
     */
    public String relateAccountDocument() throws Exception {
        account = baseService.getEntityById(Account.class, id);
        Set<Document> documents = account.getDocuments();
        Iterator<Document> documentIterator = documents.iterator();
        long totalRecords = documents.size();
        ListDocumentAction.getListJson(documentIterator, totalRecords, null, false);
        return null;
    }

    /**
     * Deletes the selected entities.
     * 
     * @return the SUCCESS result
     */
    public String delete() throws Exception {
        UserUtil.permissionCheck("delete_account");
        baseService.batchDeleteEntity(Account.class, this.getSeleteIDs());
        return SUCCESS;
    }

    /**
     * Removes the related entities
     * 
     * @return the SUCCESS result
     */
    public String remove() throws ServiceException {
        if (this.getSeleteIDs() != null) {
            String[] ids = seleteIDs.split(",");
            for (int i = 0; i < ids.length; i++) {
                String removeId = ids[i];
                account = baseService.getEntityById(Account.class, Integer.valueOf(removeId));
                if ("Account".endsWith(super.getRemoveKey())) {
                    account.setManager(null);
                }
                this.baseService.makePersistent(account);
            }
        }
        return SUCCESS;
    }

    /**
     * Copies the selected entities
     * 
     * @return the SUCCESS result
     */
    public String copy() throws Exception {
        UserUtil.permissionCheck("create_account");
        if (this.getSeleteIDs() != null) {
            String[] ids = seleteIDs.split(",");
            for (int i = 0; i < ids.length; i++) {
                String copyid = ids[i];
                Account oriRecord = baseService.getEntityById(Account.class, Integer.valueOf(copyid));
                Account targetRecord = oriRecord.clone();
                targetRecord.setId(null);
                this.getbaseService().makePersistent(targetRecord);
            }
        }
        return SUCCESS;
    }

    /**
     * Exports the entities
     * 
     * @return the exported entities inputStream
     */
    public InputStream getInputStream() throws Exception {
        return getDownloadContent(false);
    }

    /**
     * Exports the template
     * 
     * @return the exported template inputStream
     */
    public InputStream getTemplateStream() throws Exception {
        return getDownloadContent(true);
    }

    private InputStream getDownloadContent(boolean isTemplate) throws Exception {
        UserUtil.permissionCheck("view_account");

        String fileName = "商场" + ".csv";
        fileName = new String(fileName.getBytes(), "ISO8859-1");
        File file = new File(fileName);
        Writer writers = new OutputStreamWriter(new FileOutputStream(file), "GBK");
        ICsvMapWriter writer = new CsvMapWriter(writers, CsvPreference.EXCEL_PREFERENCE);

        try {
            final String[] header = new String[] { "商场名称", "商场类型", "商场BD等级", "商圈名", "省", "市", "区", "商场地址", "营业面积",
                    "商户数量", "日均人流量", "峰值人流量", "年销售额", "意向", "进度", "备注", "联系人", "职位", "邮箱", "qq”", "办公室电话", "手机或直线" };
            writer.writeHeader(header);
            if (!isTemplate) {
                String[] ids = seleteIDs.split(",");
                for (int i = 0; i < ids.length; i++) {
                    String id = ids[i];
                    Account account = baseService.getEntityById(Account.class, Integer.parseInt(id));
                    final HashMap<String, ? super Object> data1 = new HashMap<String, Object>();
                    data1.put(header[0], account.getId());
                    data1.put(header[1], CommonUtil.fromNullToEmpty(account.getName()));
                    AccountLevel accountLevel = account.getAccount_level();
                    if (accountLevel != null) {
                        data1.put(header[2], accountLevel.getId());
                    } else {
                        data1.put(header[2], "");
                    }
                    data1.put(header[3], CommonUtil.getOptionLabel(accountLevel));
                    if (account.getCurrency() != null) {
                        data1.put(header[4], account.getCurrency().getId());
                        data1.put(header[5], account.getCurrency().getName());
                    } else {
                        data1.put(header[4], "");
                        data1.put(header[5], "");
                    }
                    Capital capital = account.getCapital();
                    if (capital != null) {
                        data1.put(header[6], capital.getId());
                    } else {
                        data1.put(header[6], "");
                    }
                    data1.put(header[7], CommonUtil.getOptionLabel(capital));
                    AnnualRevenue annualRevenue = account.getAnnual_revenue();
                    if (annualRevenue != null) {
                        data1.put(header[8], annualRevenue.getId());
                    } else {
                        data1.put(header[8], "");
                    }
                    data1.put(header[9], CommonUtil.getOptionLabel(annualRevenue));
                    CompanySize companySize = account.getCompany_size();
                    if (companySize != null) {
                        data1.put(header[10], companySize.getId());
                    } else {
                        data1.put(header[10], "");
                    }
                    data1.put(header[11], CommonUtil.getOptionLabel(companySize));
                    AccountType accountType = account.getAccount_type();
                    if (accountType != null) {
                        data1.put(header[12], accountType.getId());
                    } else {
                        data1.put(header[12], "");
                    }
                    data1.put(header[13], CommonUtil.getOptionLabel(accountType));
                    Industry industry = account.getIndustry();
                    if (industry != null) {
                        data1.put(header[14], industry.getId());
                    } else {
                        data1.put(header[14], "");
                    }
                    data1.put(header[15], CommonUtil.getOptionLabel(industry));
                    data1.put(header[16], CommonUtil.fromNullToEmpty(account.getEmail()));
                    data1.put(header[17], CommonUtil.fromNullToEmpty(account.getOffice_phone()));
                    data1.put(header[18], CommonUtil.fromNullToEmpty(account.getWebsite()));
                    data1.put(header[19], CommonUtil.fromNullToEmpty(account.getFax()));
                    data1.put(header[20], CommonUtil.fromNullToEmpty(account.getBill_street()));
                    data1.put(header[21], CommonUtil.fromNullToEmpty(account.getBill_city()));
                    data1.put(header[22], CommonUtil.fromNullToEmpty(account.getBill_state()));
                    data1.put(header[23], CommonUtil.fromNullToEmpty(account.getBill_postal_code()));
                    data1.put(header[24], CommonUtil.fromNullToEmpty(account.getBill_country()));
                    data1.put(header[25], CommonUtil.fromNullToEmpty(account.getShip_street()));
                    data1.put(header[26], CommonUtil.fromNullToEmpty(account.getShip_city()));
                    data1.put(header[27], CommonUtil.fromNullToEmpty(account.getShip_state()));
                    data1.put(header[28], CommonUtil.fromNullToEmpty(account.getShip_postal_code()));
                    data1.put(header[29], CommonUtil.fromNullToEmpty(account.getShip_country()));
                    AccountNature accountNature = account.getAccount_nature();
                    if (accountNature != null) {
                        data1.put(header[30], accountNature.getId());
                    } else {
                        data1.put(header[30], "");
                    }
                    data1.put(header[31], CommonUtil.getOptionLabel(accountNature));
                    data1.put(header[32], CommonUtil.fromNullToEmpty(account.getLegal_representative()));
                    data1.put(header[33], CommonUtil.fromNullToEmpty(account.getBusiness_scope()));
                    Date createDate = account.getCreate_date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATE_EDIT_FORMAT);
                    if (createDate != null) {
                        data1.put(header[34], dateFormat.format(createDate));
                    } else {
                        data1.put(header[34], "");
                    }
                    data1.put(header[35], CommonUtil.fromNullToEmpty(account.getCredit()));
                    data1.put(header[36], CommonUtil.fromNullToEmpty(account.getReputation()));
                    data1.put(header[37], CommonUtil.fromNullToEmpty(account.getMarket_position()));
                    data1.put(header[38], CommonUtil.fromNullToEmpty(account.getDevelopment_potential()));
                    data1.put(header[39], CommonUtil.fromNullToEmpty(account.getOperational_characteristics()));
                    data1.put(header[40], CommonUtil.fromNullToEmpty(account.getOperational_direction()));
                    data1.put(header[41], CommonUtil.fromNullToEmpty(account.getSic_code()));
                    data1.put(header[42], CommonUtil.fromNullToEmpty(account.getTicket_symbol()));
                    if (account.getManager() != null) {
                        data1.put(header[43], account.getManager().getId());
                        data1.put(header[44], account.getManager().getName());
                    } else {
                        data1.put(header[43], "");
                        data1.put(header[44], "");
                    }
                    if (account.getAssigned_to() != null) {
                        data1.put(header[45], account.getAssigned_to().getId());
                        data1.put(header[46], account.getAssigned_to().getName());
                    } else {
                        data1.put(header[45], "");
                        data1.put(header[46], "");
                    }
                    writer.write(data1, header);
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            writer.close();
        }

        InputStream in = new FileInputStream(file);

        this.setFileName(fileName);
        return in;
    }

    public String importCSV() throws Exception {
        File file = this.getUpload();
        Reader readers = new InputStreamReader(new FileInputStream(file), "GBK");
        CsvListReader reader = new CsvListReader(readers, CsvPreference.EXCEL_PREFERENCE);

        int failedNum = 0;
        int successfulNum = 0;
        Date date = new Date();
        try {
            final String[] header = reader.getCSVHeader(true);

            List<String> line = new ArrayList<String>();
            Map<String, String> failedMsg = new HashMap<String, String>();
            while ((line = reader.read()) != null) {

                Map<String, String> row = new HashMap<String, String>();
                for (int i = 0; i < line.size(); i++) {
                    row.put(header[i], line.get(i));
                }

                try {

                    String name = row.get("商场");
                    if (!CommonUtil.isNullOrEmpty(name)) {
                        Account account = new Account();
                        account.setName(name);

                        String assignedTo = row.get("负责人");
                        if (CommonUtil.isNullOrEmpty(assignedTo)) {
                            User user = new User();
                            user.setId(23);
                            user.clone();
                            account.setAssigned_to(user);
                        } else {
                            User assignedToUser = getUserIdByName(assignedTo);
                            assignedToUser.clone();
                            account.setAssigned_to(assignedToUser);
                        }

                        String typeIdStr = row.get("商场类型");
                        if (CommonUtil.isNullOrEmpty(typeIdStr)) {
                            account.setAccount_type(null);
                        } else {

                            Integer accountTypeId = getAccountTypesByStr(typeIdStr);
                            if (accountTypeId != null) {
                                AccountType accountType = accountTypeService.getEntityById(AccountType.class,
                                                                                           accountTypeId);
                                account.setAccount_type(accountType);
                            }

                        }
                        String mallbdLevel = row.get("行业等级");
                        if (CommonUtil.isNullOrEmpty(mallbdLevel)) {
                            account.setMallbd_level(null);
                        } else {
                            account.setMallbd_level(mallbdLevel);
                        }
                        String mallCircle = row.get("商圈");
                        if (CommonUtil.isNullOrEmpty(mallCircle)) {
                            account.setMall_circle(null);
                        } else {
                            account.setMall_circle(mallCircle);
                        }

                        String officePhone = row.get("商场电话");
                        if (CommonUtil.isNullOrEmpty(officePhone)) {
                            account.setOffice_phone(null);
                        } else {
                            account.setOffice_phone(officePhone);
                        }

                        String province = row.get("省");
                        if (CommonUtil.isNullOrEmpty(province)) {
                            account.setProvince(null);
                        } else {
                            account.setProvince(province);
                        }

                        String city = row.get("城市");
                        if (CommonUtil.isNullOrEmpty(city)) {
                            account.setCity(city);
                        } else {
                            account.setCity(city);
                        }

                        String district = row.get("行政区");
                        if (CommonUtil.isNullOrEmpty(district)) {
                            account.setDistrict(district);
                        } else {
                            account.setDistrict(district);
                        }

                        String address = row.get("地址");
                        if (CommonUtil.isNullOrEmpty(address)) {
                            account.setAddress(address);
                        } else {
                            account.setAddress(address);
                        }

                        // String mallAcreage = row.get("营业面积");
                        // if (CommonUtil.isNullOrEmpty(mallAcreage)) {
                        // account.setMall_acreage(null);
                        // } else {
                        // account.setMall_acreage(new BigDecimal(mallAcreage));
                        // }
                        //
                        // String dayPeopleFlow = row.get("日均人流量");
                        // if (CommonUtil.isNullOrEmpty(dayPeopleFlow)) {
                        // account.setDay_people_flow(null);
                        // } else {
                        // account.setDay_people_flow(new BigDecimal(dayPeopleFlow));
                        // }
                        //
                        // String peakPeopleFlow = row.get("峰值人流量");
                        // if (CommonUtil.isNullOrEmpty(peakPeopleFlow)) {
                        // account.setPeak_people_flow(null);
                        // } else {
                        // account.setPeak_people_flow(new BigDecimal(peakPeopleFlow));
                        // }
                        //
                        // String yearSales = row.get("年销售额");
                        // if (CommonUtil.isNullOrEmpty(yearSales)) {
                        // account.setYear_sales(null);
                        // } else {
                        // account.setYear_sales(new BigDecimal(yearSales));
                        // }

                        String meno = row.get("备注");
                        if (CommonUtil.isNullOrEmpty(meno)) {
                            account.setMemo(null);
                        } else {
                            account.setMemo(CommonUtil.stringToJson(meno));
                        }

                        String accountIntent = row.get("客户意向");
                        if (CommonUtil.isNullOrEmpty(accountIntent)) {
                            account.setAccountIntent(null);
                        } else {
                            Integer accountIntentInt = getAccountIntentByStr(accountIntent);
                            account.setAccountIntent(accountIntentInt);
                        }
                        String accountVisit = row.get("拜访进度");
                        if (CommonUtil.isNullOrEmpty(accountVisit)) {
                            account.setAccountVisit(null);
                        } else {
                            Integer accountVisitInt = getAccountVisitByStr(accountVisit);
                            account.setAccountVisit(accountVisitInt);
                        }

                        User user = super.getLoginUser();

                        Account oldaccount = baseService.findByName("Account", account.getName());
                        if (null == oldaccount) {
                            account.setCreated_on(date);
                            account.setUpdated_on(date);
                            account.setCreated_by(user);
                            account.setUpdated_by(user);
                            account.setAssigned_date(date);
                            Account accounts = baseService.makePersistent(account);
                            Contact contact = new Contact();
                            String lastName = row.get("联系人");
                            if (!CommonUtil.isNullOrEmpty(lastName)) {
                                contact.setLast_name(lastName);
                                Contact_default contact_default = new Contact_default();
                                contact_default.setId(1);
                                contact.setContact_default(contact_default);
                                String title = row.get("职位");
                                if (CommonUtil.isNullOrEmpty(title)) {
                                    contact.setTitle(null);
                                } else {
                                    contact.setTitle(title);
                                }

                                String officeEmail = row.get("邮箱");
                                if (CommonUtil.isNullOrEmpty(officeEmail)) {
                                    contact.setOffice_email(null);
                                } else {
                                    contact.setOffice_email(officeEmail);
                                }

                                String contactOffice_phone = row.get("手机");
                                if (CommonUtil.isNullOrEmpty(contactOffice_phone)) {
                                    contact.setOffice_phone(null);
                                } else {
                                    contact.setOffice_phone(contactOffice_phone);
                                }

                                String mobile = row.get("分机/直线");
                                if (CommonUtil.isNullOrEmpty(mobile)) {
                                    contact.setMobile(null);
                                } else {
                                    contact.setMobile(mobile);
                                }
                                contact.setAccount(accounts);
                                contact.setCreated_on(date);
                                contact.setUpdated_on(date);
                                contact.setCreated_by(user);
                                contact.setUpdated_by(user);
                                contactBaseService.makePersistent(contact);
                            }
                        }
                        successfulNum++;

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    failedNum++;
                    failedMsg.put(account.getName(), e.getMessage());
                }

            }

            this.setFailedMsg(failedMsg);
            this.setFailedNum(failedNum);
            this.setSuccessfulNum(successfulNum);
            this.setTotalNum(successfulNum + failedNum);
        } finally {
            reader.close();
        }
        return SUCCESS;
    }

    private Integer getAccountTypesByStr(String str) {
        if ("购物中心".equals(str)) {
            return 1;
        }
        if ("社区购物中心".equals(str)) {
            return 2;
        }
        if ("综合百货".equals(str)) {
            return 3;
        }
        if ("百货".equals(str)) {
            return 4;
        }
        return null;
    }

    private Integer getAccountIntentByStr(String str) {
        if ("暂无".equals(str)) {
            return 0;
        }
        if ("口头承诺".equals(str)) {
            return 1;
        }
        if ("初步意向".equals(str)) {
            return 2;
        }
        if ("试用合同".equals(str)) {
            return 3;
        }
        if ("正式合同".equals(str)) {
            return 4;
        }
        if ("已有设备".equals(str)) {
            return 5;
        }
        if ("无投放价值".equals(str)) {
            return 6;
        }
        if ("已进场".equals(str)) {
            return 7;
        }
        return null;
    }

    private Integer getAccountVisitByStr(String str) {
        if ("暂无".equals(str)) {
            return 0;
        }
        if ("初次".equals(str)) {
            return 1;
        }
        if ("二次".equals(str)) {
            return 2;
        }
        if ("多次".equals(str)) {
            return 3;
        }
        if ("谈判".equals(str)) {
            return 4;
        }
        if ("售后".equals(str)) {
            return 5;
        }
        return null;
    }

    private User getUserIdByName(String name) {
        List<User> userList = userService.getAllObjects("User");
        for (User user : userList) {
            if (name.equals(user.getLast_name())) {
                return user;
            }
        }
        User user = new User();
        user.setId(23);
        return user;
    }

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }

    @Override
    protected String getEntityName() {
        return Account.class.getSimpleName();
    }

    public IBaseService<Account> getbaseService() {
        return baseService;
    }

    public void setbaseService(IBaseService<Account> baseService) {
        this.baseService = baseService;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * @return the id
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the userService
     */
    public IBaseService<User> getUserService() {
        return userService;
    }

    /**
     * @param userService the userService to set
     */
    public void setUserService(IBaseService<User> userService) {
        this.userService = userService;
    }

    /**
     * @return the campaignService
     */
    public IBaseService<Campaign> getCampaignService() {
        return campaignService;
    }

    /**
     * @param campaignService the campaignService to set
     */
    public void setCampaignService(IBaseService<Campaign> campaignService) {
        this.campaignService = campaignService;
    }

    /**
     * @return the targetListService
     */
    public IBaseService<TargetList> getTargetListService() {
        return targetListService;
    }

    /**
     * @param targetListService the targetListService to set
     */
    public void setTargetListService(IBaseService<TargetList> targetListService) {
        this.targetListService = targetListService;
    }

    /**
     * @return the documentService
     */
    public IBaseService<Document> getDocumentService() {
        return documentService;
    }

    /**
     * @param documentService the documentService to set
     */
    public void setDocumentService(IBaseService<Document> documentService) {
        this.documentService = documentService;
    }

    /**
     * @return the baseService
     */
    public IBaseService<Account> getBaseService() {
        return baseService;
    }

    /**
     * @param baseService the baseService to set
     */
    public void setBaseService(IBaseService<Account> baseService) {
        this.baseService = baseService;
    }

    /**
     * @return the accountLevelService
     */
    public IOptionService<AccountLevel> getAccountLevelService() {
        return accountLevelService;
    }

    /**
     * @param accountLevelService the accountLevelService to set
     */
    public void setAccountLevelService(IOptionService<AccountLevel> accountLevelService) {
        this.accountLevelService = accountLevelService;
    }

    /**
     * @return the capitalService
     */
    public IOptionService<Capital> getCapitalService() {
        return capitalService;
    }

    /**
     * @param capitalService the capitalService to set
     */
    public void setCapitalService(IOptionService<Capital> capitalService) {
        this.capitalService = capitalService;
    }

    /**
     * @return the annualRevenueService
     */
    public IOptionService<AnnualRevenue> getAnnualRevenueService() {
        return annualRevenueService;
    }

    /**
     * @param annualRevenueService the annualRevenueService to set
     */
    public void setAnnualRevenueService(IOptionService<AnnualRevenue> annualRevenueService) {
        this.annualRevenueService = annualRevenueService;
    }

    /**
     * @return the companySizeService
     */
    public IOptionService<CompanySize> getCompanySizeService() {
        return companySizeService;
    }

    /**
     * @param companySizeService the companySizeService to set
     */
    public void setCompanySizeService(IOptionService<CompanySize> companySizeService) {
        this.companySizeService = companySizeService;
    }

    /**
     * @return the accountNatureService
     */
    public IOptionService<AccountNature> getAccountNatureService() {
        return accountNatureService;
    }

    /**
     * @param accountNatureService the accountNatureService to set
     */
    public void setAccountNatureService(IOptionService<AccountNature> accountNatureService) {
        this.accountNatureService = accountNatureService;
    }

    /**
     * @return the currencyService
     */
    public IBaseService<Currency> getCurrencyService() {
        return currencyService;
    }

    /**
     * @param currencyService the currencyService to set
     */
    public void setCurrencyService(IBaseService<Currency> currencyService) {
        this.currencyService = currencyService;
    }

    /**
     * @param accountTypeService the accountTypeService to set
     */
    public void setAccountTypeService(IOptionService<AccountType> accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    /**
     * @return the accountTypeService
     */
    public IOptionService<AccountType> getAccountTypeService() {
        return accountTypeService;
    }

    /**
     * @return the industryService
     */
    public IOptionService<Industry> getIndustryService() {
        return industryService;
    }

    /**
     * @param industryService the industryService to set
     */
    public void setIndustryService(IOptionService<Industry> industryService) {
        this.industryService = industryService;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public Integer getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(Integer accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public String getStartAssignDate() {
        return startAssignDate;
    }

    public void setStartAssignDate(String startAssignDate) {
        this.startAssignDate = startAssignDate;
    }

    public String getEndAssignDate() {
        return endAssignDate;
    }

    public void setEndAssignDate(String endAssignDate) {
        this.endAssignDate = endAssignDate;
    }

    public String getAssignTo() {
        return assignTo;
    }

    public void setAssignTo(String assignTo) {
        this.assignTo = assignTo;
    }

    public String[] getMallbdLevels() {
        return mallbdLevels;
    }

    public void setMallbdLevels(String[] mallbdLevels) {
        this.mallbdLevels = mallbdLevels;
    }

    public String[] getAdvertLevels() {
        return advertLevels;
    }

    public void setAdvertLevels(String[] advertLevels) {
        this.advertLevels = advertLevels;
    }

    public String[] getIntentNames() {
        return intentNames;
    }

    public void setIntentNames(String[] intentNames) {
        this.intentNames = intentNames;
    }

    public String[] getVisitNames() {
        return visitNames;
    }

    public void setVisitNames(String[] visitNames) {
        this.visitNames = visitNames;
    }

    public String[] getAccountTypes() {
        return accountTypes;
    }

    public void setAccountTypes(String[] accountTypes) {
        this.accountTypes = accountTypes;
    }

    public IBaseService<Contact> getContactBaseService() {
        return contactBaseService;
    }

    public void setContactBaseService(IBaseService<Contact> contactBaseService) {
        this.contactBaseService = contactBaseService;
    }

    public List<User> getUnderlingUsers() {
        return underlingUsers;
    }

    public void setUnderlingUsers(List<User> underlingUsers) {
        this.underlingUsers = underlingUsers;
    }

}
