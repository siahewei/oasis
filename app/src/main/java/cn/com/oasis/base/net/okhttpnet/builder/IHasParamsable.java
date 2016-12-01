package cn.com.oasis.base.net.okhttpnet.builder;


import java.util.Map;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/10/2 上午11:47
 */

public interface IHasParamsable {
    ReqestBuilder params(Map<String, String> params);
    ReqestBuilder params(String key, String val);
}
