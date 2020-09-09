package com.lingmeng.user.api;


import com.lingmeng.base.RestReturn;
import com.lingmeng.user.pojo.User;

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
    Boolean register(User user, String code);

     /**
      * @Author skin
      * @Date  2020/9/9
      * @Description 用户查询
      **/
    RestReturn query(String  userName, String  password);

    Boolean registerCheck(String  data,String type);
}
