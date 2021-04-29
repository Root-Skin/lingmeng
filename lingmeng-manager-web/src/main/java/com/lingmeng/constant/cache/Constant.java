package com.lingmeng.constant.cache;

import java.util.ArrayList;
import java.util.List;

public class Constant {


    /**
     * redis-OK
     */
    public final static String OK = "OK";

    /**
     * redis过期时间，以秒为单位，一分钟
     */
    public final static int EXRP_MINUTE = 60;

    /**
     * redis过期时间，以秒为单位，一小时
     */
    public final static int EXRP_HOUR = 60 * 60;

    /**
     * redis过期时间，以秒为单位，一天
     */
    public final static int EXRP_DAY = 60 * 60 * 24;


    /**
     * redis-key-前缀-shiro:cache:
     */
    public final static String PREFIX_SHIRO_CACHE = "shiro:cache:";

    /**
     * redis-key-前缀-shiro:access_token:
     */
    public final static String PREFIX_SHIRO_ACCESS_TOKEN = "shiro:access_token:";

    /**
     * redis-key-前缀-shiro:refresh_token:
     */
    public final static String PREFIX_SHIRO_REFRESH_TOKEN = "shiro:refresh_token:";

    /**
     *  渠道账户
     */
    public final static String CHANNEL_ACCOUNT = "channel:account:";

    /**
     * JWT-account:
     */
    public final static String ACCOUNT = "userNo";

    // ACCOUNT_PHONE
    public final static String ACCOUNT_PHONE = "accountPhone";

    public final static String PASSWORD = "password";
    /**
     * JWT-currentTimeMillis:
     */
    public final static String CURRENT_TIME_MILLIS = "currentTimeMillis";
    /**
     * PASSWORD_MAX_LEN
     */
    public static final Integer PASSWORD_MAX_LEN = 8;

    public static String X_ACCESS_TOKEN = "X-Access-Token";

    public static String PREFIX_USER_ROLE = "PREFIX_USER_ROLE";


    public final static String PREFIX_SHIRO_USER = "shiro:user:";

    /**
     * 前端允许上传的图片格式
     */
    public final static List<String> ALLOWED_PIC_TYPES = new ArrayList<String>(){{
        add("png");
        add("jpg");
        add("jpeg");
        add("gif");
        add("bmp");
    }};

}
