package cn.com.oasis.base.net.ionnet.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/8/6 下午2:46
 */
public class ErroCode {
    static Map<Integer, String> sErroMap = new HashMap<>();
    public static void init(){
    }

    public static String getErrorMsg(int code){
        if (sErroMap.containsKey(code)){
            return sErroMap.get(code);
        }else {
            return "未知错误";
        }
    }

}
