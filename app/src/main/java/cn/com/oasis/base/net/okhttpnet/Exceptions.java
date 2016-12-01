package cn.com.oasis.base.net.okhttpnet;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/9/27 上午12:01
 */

public class Exceptions {
    public static void illegalArgument(String msg, Object... params){
        throw new IllegalArgumentException(String.format(msg, params));
    }
}
