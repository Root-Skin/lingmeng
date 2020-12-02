package com.lingmeng.api.user;


import com.lingmeng.base.RestReturn;
import com.lingmeng.user.vo.req.UserReq;


public interface UserService {


     /**
      * @Author skin
      * @Date  2020/9/6
      * @Description 发送邮箱验证码
      **/
    Boolean sendEmailCode(String receiver);

     /**
     * @Author skin
     * @Date  2020/9/6
     * @Description 用户注册
     **/
    Boolean register(UserReq userReq);

     /**
      * @Author skin
      * @Date  2020/9/9
      * @Description 用户查询
      **/
    RestReturn query(String userName, String password);

    Boolean registerCheck(String data, String type);
}
