package com.fh.util;


public class SystemConstant {
    public final static String TEMPLATE_ROOT_PATH = "/template";
    public final static String TEMPLATE_WORD  = "word-template.xml";
    public final static String TEMPLATE_EXCEL  = "excel-template.xml";
    public final static String TEMPLATE_PDF  = "pdf-template.html";
    public final static String SUFFIX_EXCEL  = ".xlsx";
    public final static String SUFFIX_WORD  = ".docx";

    public final static String SESSION_KEY  = "user";
    public final static String MESSAGE_CODE  = "code";
    public final static String COOKIE_LOGIN_KEY  = "cookie_login";
    public final static String COOKIE_USER_KEY  = "cookie_user_key";
    public final static String REDIS_CART_KEY  = "order:";
    public final static String TOKEN_KEY  = "token:";
    public final static String CATEGORY_REDIS  = "category:";
    public final static int COOKIE_EXPIRY_TIME  = 7*24*60*60;
    public final static int TOKEN_EXPIRY_TIME  = 5*60*60*1000;
    public final static int REDIS_EXPIRY_TIME  = 5*60;
}
