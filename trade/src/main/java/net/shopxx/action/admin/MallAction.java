package net.shopxx.action.admin;

import javax.annotation.Resource;

import net.shopxx.entity.Mall;
import net.shopxx.service.MallService;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

public class MallAction extends BaseAdminAction {

    private static final long serialVersionUID = -225094237752539018L;
    private Mall mall;
    
    @Resource
    private MallService mallService;
    
    public String add() {
        return INPUT;
    }

    /**
     * 保存添加的商场
     * 
     * @return
     */
    @Validations(requiredStrings = { @RequiredStringValidator(fieldName = "mall.name", message = "商场名不允许为空!") })
    @InputConfig(resultName = "error")
    public String save() {
       return null;
    }

    
    public Mall getMall() {
        return mall;
    }

    
    public void setMall(Mall mall) {
        this.mall = mall;
    }

}
