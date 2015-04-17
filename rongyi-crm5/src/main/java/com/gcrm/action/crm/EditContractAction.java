package com.gcrm.action.crm;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.gcrm.domain.Account;
import com.gcrm.domain.Attachment;
import com.gcrm.domain.Contract;
import com.gcrm.domain.Record;
import com.gcrm.domain.Record.RecordTypeEnum;
import com.gcrm.service.IBaseService;
import com.gcrm.util.Constant;
import com.gcrm.util.security.UserUtil;
import com.opensymphony.xwork2.Preparable;

/**
 * 编辑合同
 * 
 * @author jiejie 2014年3月11日 上午11:43:17
 */
public class EditContractAction extends BaseEditAction implements Preparable {

    private static final long        serialVersionUID = -8482074658696795624L;
    private IBaseService<Contract>   baseService;
    private IBaseService<Record>     recordService;
    private IBaseService<Attachment> attachmentService;
    private IBaseService<Account>    accountService;

    private Integer                  id;
    private Contract                 contract;
    private File                     upload;                                  // 附件
    private String                   uploadFileName;
    private String                   uploadContentType;
    private Integer                  accountId;                               // 商场id
    private String                   accountName;
    private Integer                  signedId;
    private String                   signedName;                              // 签订者

    /**
     * 跳到合同页面模板 editContract.action
     */
    public String get() {
        Integer id = getId();
        if (id != null) {
            contract = baseService.getEntityById(Contract.class, id);
            this.getBaseInfo(contract, Contract.class.getSimpleName(), Constant.CRM_NAMESPACE);
            if(contract.getAccountId()!=null && contract.getAccountId().intValue() > 0){
            	Account ac = accountService.getEntityById(Account.class,  contract.getAccountId());
                setAccountName(ac.getName());
            }
        } else {
            this.initBaseInfo();
        }
       
        if ((accountId != null && accountId.intValue() > 0)) {
            
        	Account ac = accountService.getEntityById(Account.class, accountId);
            setAccountName(ac.getName());
        }
        signedId = getLoginUser().getId();
        signedName = getLoginUser().getName();

        return SUCCESS;
    }

    /**
     * 保存
     */
    public String save() throws Exception {
        saveEntity();
        contract = getBaseService().makePersistent(contract);
        this.setId(contract.getId());
        this.setSaveFlag("true");
        String recordText = String.format("【%s】上传了合同", getLoginUser().getLast_name());
        Record record = getRecordOjb(RecordTypeEnum.UPLOAD_CONTRACT, contract.getId(), null, null, null, null, 0, null,
                                     "contract", recordText);
        recordService.makePersistent(record);
        this.setAccountId(contract.getAccountId());
        return SUCCESS;
    }

    private String saveEntity() throws Exception {
        Contract originalContract = null;
        if (contract.getId() != null) {
            UserUtil.permissionCheck("update_visit");
            originalContract = baseService.getEntityById(Contract.class, contract.getId());
            if (upload == null) {
                contract.setAttachment(originalContract.getAttachment());
            }
        }else{
            UserUtil.permissionCheck("create_contract");
        }
        File file = this.getUpload();
        HttpServletRequest request = ServletActionContext.getRequest();
        request.getHeader("content-disposition");
        if (file != null) {
            InputStream stream = null;
            byte[] input = null;
            try {
                stream = new BufferedInputStream(new FileInputStream(file));
                input = new byte[stream.available()];
                stream.read(input);
            } finally {
                stream.close();
            }
            if (input.length > 1024 * 1024 * 5) {
                addActionMessage("附件大小不能超过5MB");
                return INPUT;
            }
            Attachment attachment = new Attachment();
            attachment.setName(uploadFileName);
            attachment.setContent(input);
            updateBaseInfo(attachment);
            Attachment newAttachment = attachmentService.makePersistent(attachment);
            contract.setAttachment(newAttachment.getId());
            contract.setFileName(newAttachment.getName());
        }
        super.updateBaseInfo(contract);
        return null;
    }

    /**
     * 下载
     * 
     * @return
     */
    public String download() throws Exception {
        if (id == null) {
            return null;
        } else {
            Contract contract = baseService.getEntityById(Contract.class, id);
            if (contract == null) {
                return null;
            }
            Attachment attach = attachmentService.getEntityById(Attachment.class, contract.getAttachment());
            byte[] downloadData = attach.getContent();
            response2download(downloadData, contract.getFileName());
        }
        return null;
    }

    @Override
    public void prepare() throws Exception {
        // TODO Auto-generated method stub
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public IBaseService<Contract> getBaseService() {
        return baseService;
    }

    public void setBaseService(IBaseService<Contract> baseService) {
        this.baseService = baseService;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public IBaseService<Attachment> getAttachmentService() {
        return attachmentService;
    }

    public void setAttachmentService(IBaseService<Attachment> attachmentService) {
        this.attachmentService = attachmentService;
    }

    public File getUpload() {
        return upload;
    }

    public void setUpload(File upload) {
        this.upload = upload;
    }

    public String getUploadContentType() {
        return uploadContentType;
    }

    public void setUploadContentType(String uploadContentType) {
        this.uploadContentType = uploadContentType;
    }

    public IBaseService<Record> getRecordService() {
        return recordService;
    }

    public void setRecordService(IBaseService<Record> recordService) {
        this.recordService = recordService;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getSignedName() {
        return signedName;
    }

    public void setSignedName(String signedName) {
        this.signedName = signedName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public IBaseService<Account> getAccountService() {
        return accountService;
    }

    public void setAccountService(IBaseService<Account> accountService) {
        this.accountService = accountService;
    }

    public Integer getSignedId() {
        return signedId;
    }

    public void setSignedId(Integer signedId) {
        this.signedId = signedId;
    }

}
