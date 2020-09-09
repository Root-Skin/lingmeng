package com.lingmeng.util;

 /**
  * @Author skin
  * @Date  2020/9/9
  * @Description 获取用户工具类
  **/
public class UserUtil {

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
       return  USER_INFO;
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
      * @Date  2020/8/28
      * @Description 获取当前登陆人ID
      **/
     //todo 留给将来扩充
//     public static String getUserId() {
//
//     }
}
