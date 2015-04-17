package net.shopxx.action.admin;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

import net.shopxx.bean.ProductImage;
import net.shopxx.cons.StatusEnum;
import net.shopxx.entity.Activity;
import net.shopxx.entity.Activity.ActivityStatusEnum;
import net.shopxx.service.ActivityService;
import net.shopxx.service.ProductImageService;

/**
 * @author jiejie 2014年3月5日 下午1:56:44
 */
@ParentPackage("admin")
public class ActivityAction extends BaseAdminAction {

    private static final long   serialVersionUID = -8413935245281321912L;

    @Resource
    public ActivityService      activityService;

    @Resource
    private ProductImageService productImageService;

    private File                activityPic;                             // 活动图片
    private Activity            activity;

    public String add() {
        return INPUT;
    }

    /**
     * 保存活动信息
     */
    @Validations(requiredFields = { @RequiredFieldValidator(fieldName = "activity.mallId", message = "请选择商场") })
    public String save() {

        // TODO:xujie 图片大小 格式检查
        List<ProductImage> productImageList = new ArrayList<ProductImage>();
        ProductImage productImage = productImageService.buildProductImage(activityPic);
        productImageList.add(productImage);
        activity.setActivityPic(productImageList);
        activity.setStatus(StatusEnum.ENABLE.getValue());
        activity.setActivityStatus(ActivityStatusEnum.WAIT_RELEASE);
        Date date = new Date();
        activity.setCreateDate(date);
        activity.setModifyDate(date);
        activityService.save(activity);
        return SUCCESS;

    }

    public String list() {
        pager = activityService.findByPager(pager);
        return LIST;
    }
    
    public String edit(){
        activity = activityService.load(NumberUtils.toInt(id));
        return INPUT;
    }
    
    public String delete(){
        //活动状态“待发布”的进行删除操作
        if(ids == null){
            return null;
        }
        Integer i =null;
        for(String id : ids){
            i = Integer.valueOf(id);
          Activity ac =  activityService.load(i);
          if(ac.isWaitRelease()){
              activityService.delete(i);
          }else{
              return ajaxJsonErrorMessage(String.format("活动ID【%d】的状态是%s，无法进行删除操作", i,ac.getActivityStatus().name()));
          }
        }
        return ajaxJsonSuccessMessage("删除操作成功");
    }
    
    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public File getActivityPic() {
        return activityPic;
    }

    public void setActivityPic(File activityPic) {
        this.activityPic = activityPic;
    }

 }
