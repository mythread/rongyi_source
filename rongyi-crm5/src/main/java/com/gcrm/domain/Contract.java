package com.gcrm.domain;

/**
 * 合同DO
 * 
 * @author jiejie
 */
public class Contract extends BaseEntity {

    private static final long serialVersionUID = 2622836797932783890L;
    private Integer           accountId;                              // 商场id
    private String            contractName;                           // 合同名称
    private Integer           attachment;
    private String            fileName;
    private Integer           signedId;                               // 签订者
    private String            memo;
   // private String            signName;

    @Override
    public String getName() {
        return "合同";
    }

    public Integer getAttachment() {
        return attachment;
    }

    public void setAttachment(Integer attachment) {
        this.attachment = attachment;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getSignedId() {
        return signedId;
    }

    public void setSignedId(Integer signedId) {
        this.signedId = signedId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }


}
