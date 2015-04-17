package com.fanxian.web.fanxian.controller.user;

import java.util.Map;

import com.fanxian.biz.common.tools.IResult;
import com.fanxian.biz.user.dataobject.UserDO;
import com.fanxian.commons.cookie.CookieNameEnum;
import com.fanxian.commons.cookie.manager.CookieManager;
import com.fanxian.commons.cookie.manager.CookieManagerLocator;
import com.fanxian.web.common.util.JsonResultUtils;
import com.fanxian.web.fanxian.controller.BaseController;
import com.fanxian.web.fanxian.webuser.FanxianWebUser;
import com.yue.commons.seine.web.annotations.ControllerAction;
import com.yue.commons.seine.web.servlet.result.WebResult;

public class UserController extends BaseController {

    /**
     * 用户支付宝帐号登入
     */
    @ControllerAction
    public WebResult login(Map<String, Object> model, String account) {
        UserDO userDO = userService.getByAccount(account);
        Integer id = null;
        if (userDO == null) {
            // 新用户
            FanxianWebUser webUser = FanxianWebUser.getCurrentUser();
            IResult<Integer> result = userService.insertUser(account, webUser.getCookieId());
            if (!result.isSuccess()) {
                return JsonResultUtils.error(result.getMessage());
            }
            id = result.getData();
        } else {
            id = userDO.getId();
        }
        // 将用户ID写入cookie
        CookieManager cookieManager = CookieManagerLocator.get(request, response);
        cookieManager.set(CookieNameEnum.fanxian_cookie_userid, id.toString());
        return JsonResultUtils.success(id, "帐号提交成功，请点击下面的【填好了去购买】");
    }
}
