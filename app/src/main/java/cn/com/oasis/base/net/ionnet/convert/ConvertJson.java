package cn.com.oasis.base.net.ionnet.convert;

import cn.com.oasis.base.net.ionnet.exception.DataException;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/7/28 下午8:55
 */
public interface ConvertJson<T> {
    T convert(String str) throws DataException;
}
