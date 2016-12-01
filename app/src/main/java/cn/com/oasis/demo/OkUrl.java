package cn.com.oasis.demo;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/10/8 下午2:28
 */

public class OkUrl {
    public static final String Domain = "http://10.10.11.51:8000";

    public static String getUrl(){
        return Domain + "/todo";
    }

    public static String getUser(){
        return Domain + "/getuser";
    }

    public static String getUserList(){
        return Domain + "/getusers";
    }

    public static String postString(){
        return Domain + "/poststring";
    }

    public static String postFile() {
        return Domain + "/postfile";
    }
}
