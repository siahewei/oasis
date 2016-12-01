package cn.com.oasis.base.env;

import com.google.gson.Gson;

/**
 * 全局公用参数
 * <p/>
 * 作者:hewei
 * 时间：2016-03-03 下午12:35
 */
public class Constant {

    /*app是否处于测试环境*/
    public static boolean isDebug = true;

    public static boolean isDebug() {
        return isDebug;
    }

    /********************
     * App相关
     ***********************/

    /*包名*/
    public static String PACKAGE_NAME;

    /*版本名称*/
    public static String VERSION = "1.0.0";

    /*版本号*/
    public static int VERSION_CODE;

    public static Gson gson = new Gson();

    /********************
     * 设备相关
     ***********************/
    /*屏幕宽度*/
    public static int SCREEN_WIDTH;

    /*屏幕高度*/
    public static int SCREEN_HEIGHT;
    /*状态栏高*/
    public static int STATUSBAR_HEIGHT;

    /*获取安装渠道的key*/
    public static final String CHANNEL_KEY = "UMENG_CHANNEL";

    /*手机号码*/
    public static String PHONE_NUM;


    /*双击页面头部时列表滑动到顶部的key*/
    public static String DEVICE_ID = "";

    /*获取本地的mac地址*/
    public static String MAC_ADDRESS = "";


    /**
     * 用户相关的常量
     */
    public static String openId = "";
    public static String userName = "";
    public static String userAvatar = "";
    public static String userPhone = "";
    public static String token = "";

    public static boolean isLogin;
    public static boolean appRunning;
}
