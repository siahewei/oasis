package cn.com.oasis.base.net.okhttpnet.converter;

import java.util.List;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/10/9 上午9:24
 */

public interface IConvertor {
    <T> T convert(String response, Class<T> cls);
    <T> List<T> converts(String response, Class<T> cls);
}
