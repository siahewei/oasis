package cn.com.oasis.base.net.ionnet.convert;

import cn.com.oasis.base.net.ionnet.exception.DataException;
import cn.com.oasis.base.net.ionnet.exception.NoDataException;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/7/28 下午8:55
 */
public class GsonConvertJson<T> extends BaseGsonConvertJson<T> {
    private final Class<T> clazz;

    public GsonConvertJson(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T convert(String str) throws DataException {
        T t ;
        try {
            t = gson.fromJson(str,clazz);
        }catch (Exception e){
            throw new DataException();
        }
        if(t==null){
            throw new NoDataException();
        }
        return t;
    }
}
