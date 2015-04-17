package com.gcrm.action.crm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.gcrm.domain.Record;
import com.gcrm.domain.Record.RecordTypeEnum;
import com.gcrm.domain.SimpleAccount;
import com.gcrm.domain.User;
import com.gcrm.service.IBaseService;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.gcrm.util.security.UserUtil;
import com.gcrm.vo.SearchCondition;
import com.gcrm.vo.SearchResult;

public class ListRecordAction extends BaseListAction {

    private IBaseService<Record>        baseService;
    private IBaseService<SimpleAccount> simpleAccountService;
    private IBaseService<User>          userService;

    private String                      CLAZZ = Record.class.getSimpleName();

    @Override
    public String list() throws Exception {
        // 查自己下的商场
        User user = getLoginUser();
        String accountHql = "";
        List<SimpleAccount> accounts = new ArrayList<SimpleAccount>();
        boolean isAdminRole = isAdminRole(user);
        if (isAdminRole) {
            accountHql = "from SimpleAccount order by id desc";
        } else {
            // 自己及下属
            List<Integer> userIdList = new ArrayList<Integer>();
            String hql = "from User where report_to = " + UserUtil.getLoginUser().getId();
            List<User> underlingUsers = userService.findByHQL(hql);
            for (User u : underlingUsers) {
                userIdList.add(u.getId());
            }
            userIdList.add(user.getId());
            String str = "assigned_to in (" + StringUtils.join(userIdList, ",") + ")";
            accountHql = "from SimpleAccount where " + str + "order by id desc";
        }
        accounts = simpleAccountService.findByHQL(accountHql);

        List<Integer> accountIds = new ArrayList<Integer>();
        Map<Integer, SimpleAccount> accountMap = new HashMap<Integer, SimpleAccount>();
        for (SimpleAccount acc : accounts) {
            accountMap.put(acc.getId(), acc);
        }

        // 查询商场下意向、进度记录
        String condition = "tableName = \'account\' and (type=\'INTENT_CHANGE\' or type=\'VISIT_CHANGE\' or type=\'INTENT_VISIT_CHANGE\') and outerId in ("
                           + StringUtils.join(accountMap.keySet(), ",") + ")";
        SearchCondition searchCondition = new SearchCondition(getPage(), getRows(), "id", "desc", condition);
        SearchResult<Record> result = baseService.getPaginationObjects(CLAZZ, searchCondition);
        List<Record> recordList = result.getResult();
        if (recordList == null || recordList.isEmpty()) {
            return null;
        }
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append(getJsonHeader(result.getTotalRecords(), searchCondition, false));

        boolean isLast = false;
        for (int i = 0; i < recordList.size(); i++) {
            if (i == recordList.size() - 1) {
                isLast = true;
            }
            Record r = recordList.get(i);
            getListJson(jsonBuilder, accountMap.get(r.getOuterId()), r, isLast);
        }
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(jsonBuilder.toString());
        return null;
    }

    public String listFull() throws Exception {
        Map<String, String> fieldTypeMap = new HashMap<String, String>();
        fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
        User loginUser = UserUtil.getLoginUser();
        SearchCondition searchCondition = getSearchCondition(fieldTypeMap, loginUser.getScope_account(), loginUser);
        SearchResult<Record> result = baseService.getPaginationObjects(CLAZZ, searchCondition);
        Iterator<Record> accounts = result.getResult().iterator();
        long totalRecords = result.getTotalRecords();
        getListJson(accounts, totalRecords, searchCondition, true);
        return null;

    }

    protected String getEntityName() {
        return Record.class.getSimpleName();
    }

    public void getListJson(StringBuilder jsonBuilder, SimpleAccount ac, Record record, boolean isLast) {

        String mallName = ac.getName();
        String assignedToName = "";
        User user = ac.getAssigned_to();
        if (user != null) {
            assignedToName = CommonUtil.fromNullToEmpty(user.getLast_name());
        }
        String intentChange = record.getIntentChangeStr();
        String visitChange = record.getVisitChangeStr();
        Date createDate = record.getCreated_on();
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATE_FULL_FORMAT);
        String createDateName = dateFormat.format(createDate);
        jsonBuilder.append("{\"mallName\":\"").append(mallName).append("\",\"mallId\":\"").append(record.getOuterId()).append("\",\"assignedToName\":\"").append(assignedToName).append("\",\"intentChange\":\"").append(intentChange).append("\",\"visitChange\":\"").append(visitChange).append("\",\"createDateName\":\"").append(createDateName).append("\"}");
        if (!isLast) {
            jsonBuilder.append(",");
        }
        if (isLast) {
            jsonBuilder.append("]}");
        }
    }

    public void getListJson(Iterator<Record> records, long totalRecords, SearchCondition searchCondition, boolean isList)
                                                                                                                         throws Exception {

        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append(getJsonHeader(totalRecords, searchCondition, isList));
        while (records.hasNext()) {
            Record instance = records.next();
            int id = instance.getId();
            User createdBy = instance.getCreated_by();
            String createdByName = "";
            if (createdBy != null) {
                createdByName = CommonUtil.fromNullToEmpty(createdBy.getName());
            }
            String createdOnName = instance.getCreatedOnName();
            RecordTypeEnum typeEnum = RecordTypeEnum.getEnum(instance.getType());
            if (typeEnum == null) {
                continue;
            }
            String typeName = typeEnum.getShowName();
            String recordText = instance.getMemo();

            if (isList) {
                jsonBuilder.append("{\"cell\":[\"").append(id).append("\",\"").append(typeName).append("\",\"");
                jsonBuilder.append(createdByName).append("\",\"").append(recordText).append("\",\"").append(createdOnName).append("\"]}");
            } else {
                jsonBuilder.append("{\"id\":\"").append(id).append("\",\"typeName\":\"").append(typeName).append("\",\"createdByName\":\"").append(createdByName).append("\",\"recordText\":\"").append(recordText).append("\",\"createdOnName\":\"").append(createdOnName).append("\"}");
            }
            if (records.hasNext()) {
                jsonBuilder.append(",");
            }
        }
        jsonBuilder.append("]}");

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(jsonBuilder.toString());
    }

    public IBaseService<Record> getBaseService() {
        return baseService;
    }

    public void setBaseService(IBaseService<Record> baseService) {
        this.baseService = baseService;
    }

    public IBaseService<SimpleAccount> getSimpleAccountService() {
        return simpleAccountService;
    }

    public void setSimpleAccountService(IBaseService<SimpleAccount> simpleAccountService) {
        this.simpleAccountService = simpleAccountService;
    }

    public IBaseService<User> getUserService() {
        return userService;
    }

    public void setUserService(IBaseService<User> userService) {
        this.userService = userService;
    }

}
