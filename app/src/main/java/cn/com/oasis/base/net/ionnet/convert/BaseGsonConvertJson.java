package cn.com.oasis.base.net.ionnet.convert;

import com.google.gson.Gson;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/7/28 下午8:55
 */
public abstract class BaseGsonConvertJson<T>  implements ConvertJson<T> {
    protected static Gson gson = new Gson();
    public static void initGson(Gson gson){
        BaseGsonConvertJson.gson = gson;
    }
}
