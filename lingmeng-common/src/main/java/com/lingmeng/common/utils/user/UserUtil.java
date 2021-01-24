package com.lingmeng.common.utils.user;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.lingmeng.common.config.JwtProperties;
import com.lingmeng.common.utils.auth.JwtUtils;
import com.lingmeng.common.utils.auth.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
  * @Author skin
  * @Date  2020/9/9
  * @Description 获取用户工具类
  **/

@Component
public class UserUtil {



    private static JwtProperties jwtProperties;

    @Autowired
    public void UserUtil(JwtProperties jwtProperties) {
        UserUtil.jwtProperties = jwtProperties;
    }
     /**
      * @Author skin
      * @Date  2020/8/28
      * @Description InheritableThreadLocal的使用
      * 需要注意的是一旦子线程被创建以后，再操作父线程中的ThreadLocal变量，
      * 那么子线程是不能感知的。因为父线程和子线程还是拥有各自的ThreadLocalMap,
      * 只是在创建子线程的“一刹那”将父线程的ThreadLocalMap复制给子线程，后续两者就没啥关系了。
      **/
    public static final InheritableThreadLocal<Object> USER_INFO = new InheritableThreadLocal<>();

     /**
      * @Author skin
      * @Date  2020/8/28
      * @Description 保存登陆用户信息
      **/
    public  static void set(Object value){
        USER_INFO.set(value);
    }
     /**
      * @Author skin
      * @Date  2020/8/28
      * @Description 得到当前线程登陆人用户信息
      **/
    public  static Object get(){
       return  USER_INFO.get();
    }
     /**
      * @Author skin
      * @Date  2020/8/28
      * @Description  从当前线程移除当前用户信息
      **/
    public  static void remove( ){
        USER_INFO.remove();
    }
     /**
      * @Author skin
      * @Date  2021/1/10
      * @Description  获取登陆人IDs
      **/

     public static String getUserId(HttpServletRequest request) throws Exception {
         String userNo = null;
             String token = CookieUtils.getCookieValue(request, "LM_TOKEN");
             if (StringUtils.isNotBlank(token)) {
                 System.out.println(jwtProperties.getPublicKey());
                 UserInfo user = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());

                 userNo = user.getId();
             }else{}
         if(StringUtils.isBlank(userNo)){
             try {
                 Object obj = get();
                 if (obj != null) {
                     String info = JSON.toJSONString(obj);
                     userNo = JSON.parseObject(info).getString("userId");
                 }
             } catch (Exception ex) {
             }
         }
         return userNo;
     }
}
