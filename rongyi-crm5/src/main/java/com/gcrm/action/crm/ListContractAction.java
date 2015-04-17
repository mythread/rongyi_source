package com.gcrm.action.crm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.gcrm.domain.Account;
import com.gcrm.domain.Contract;
import com.gcrm.domain.User;
import com.gcrm.service.IBaseService;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.gcrm.util.security.UserUtil;
import com.gcrm.vo.SearchCondition;
import com.gcrm.vo.SearchResult;

/**
 * 合同列表action
 * 
 * @author jiejie 2014年3月11日 下午12:25:45
 */
public class ListContractAction extends BaseListAction {

    private static final long      serialVersionUID = -1872106217507103905L;
    private IBaseService<Contract> baseService;
    private IBaseService<User>     userService;
    private IBaseService<Account>  accountService;
    private static final String    CLAZZ            = Contract.class.getSimpleName();
    private String                 accountId;

    @Override
    public String list() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    public String listContractFull() throws Exception {
    	UserUtil.permissionCheck("view_contract");
        Map<String, String> fieldTypeMap = new HashMap<String, String>();
        fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
        fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);

        User loginUser = UserUtil.getLoginUser();
        SearchCondition searchCondition = getSearchCondition(fieldTypeMap, loginUser.getScope_contact(), loginUser);

        SearchResult<Contract> result = baseService.getPaginationObjects(CLAZZ, searchCondition);

        long totalRecords = result.getTotalRecords();
        Iterator<Contract> it = result.getResult().iterator();
        getListJson(it, totalRecords, searchCondition, true);
        return null;

    }

    public void getListJson(Iterator<Contract> contracts, long totalRecords, SearchCondition searchCondition,
                            boolean isList) throws Exception {

        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append(getJsonHeader(totalRecords, searchCondition, isList));
        while (contracts.hasNext()) {
            Contract instance = contracts.next();
            int id = instance.getId();
            String name = CommonUtil.fromNullToEmpty(instance.getContractName());
            Integer accountId = instance.getAccountId();
            String accountName = "";
            if (accountId != null) {
                Account ac = accountService.getEntityById(Account.class, accountId);
                accountName = ac != null ? ac.getName() : "";
            }
            String fileName = instance.getFileName();// 附件名
            String memo = "";
            String signName = "";
            if (instance.getSignedId() != null) {
                User signedUser = userService.getEntityById(User.class, instance.getSignedId());
                if (signedUser != null) {
                    signName = signedUser.getName();
                }
            }
            User createdBy = instance.getCreated_by();
            String createdByName = "";
            if (createdBy != null) {
                createdByName = CommonUtil.fromNullToEmpty(createdBy.getName());
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATE_FULL_FORMAT);
            Date createdOn = instance.getCreated_on();
            String createdOnName = "";
            if (createdOn != null) {
                createdOnName = dateFormat.format(createdOn);
            }
            if (isList) {
                jsonBuilder.append("{\"cell\":[\"").append(id).append("\",\"").append(name).append("\",\"").append(accountName).append("\",\"");
                jsonBuilder.append(createdByName).append("\",\"").append(signName).append("\",\"").append(memo).append("\",\"").append(createdOnName).append("\",\"").append(fileName).append("\"]}");
            } else {
                jsonBuilder.append("{\"id\":\"").append(id).append("\",\"name\":\"").append(name).append("\",\"accountName\":\"").append(accountName).append("\",\"createdByName\":\"").append(createdByName).append("\",\"signName\":\"").append(signName).append("\",\"memo\":\"").append(memo).append("\",\"createdOnName\":\"").append(createdOnName).append("\",\"fileName\":\"").append(fileName).append("\"}");
            }
            if (contracts.hasNext()) {
                jsonBuilder.append(",");
            }
        }
        jsonBuilder.append("]}");

        // Returns JSON data back to page
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(jsonBuilder.toString());
    }

    /**
     * 删除合同
     */
    public String delete() throws Exception {
        // TODO:权限验证
        UserUtil.permissionCheck("delete_contract");

        baseService.batchDeleteEntity(Contract.class, this.getSeleteIDs());
        return SUCCESS;
    }

    @Override
    protected String getEntityName() {
        return Contract.class.getSimpleName();
    }

    public String execute() throws Exception {
        return SUCCESS;
    }

    public IBaseService<Contract> getBaseService() {
        return baseService;
    }

    public void setBaseService(IBaseService<Contract> baseService) {
        this.baseService = baseService;
    }

    public IBaseService<User> getUserService() {
        return userService;
    }

    public void setUserService(IBaseService<User> userService) {
        this.userService = userService;
    }

    public IBaseService<Account> getAccountService() {
        return accountService;
    }

    public void setAccountService(IBaseService<Account> accountService) {
        this.accountService = accountService;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

}
