package com.lingmeng.controller.address;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lingmeng.address.model.LmUserExt;
import com.lingmeng.base.RestReturn;
import com.lingmeng.common.Interceptor.LoginInterceptor;
import com.lingmeng.common.config.JwtProperties;
import com.lingmeng.common.utils.auth.UserInfo;
import com.lingmeng.dao.address.LmUserExtMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private LmUserExtMapper lmUserExtMapper;

    @Autowired
    private JwtProperties prop;

    //得到当前用户的地址信息
    @GetMapping("/list")
    public RestReturn list() {
            UserInfo user = LoginInterceptor.getLoginUser();
            List<LmUserExt> userRelateAddress = lmUserExtMapper.selectList(new QueryWrapper<LmUserExt>().eq("user_id", user.getId()));
        return RestReturn.ok(userRelateAddress);
    }
}
