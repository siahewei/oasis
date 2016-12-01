package cn.com.oasis.base.net.okhttpnet.callback;

import android.text.TextUtils;

import java.lang.reflect.ParameterizedType;

import cn.com.oasis.base.net.ionnet.exception.NoDataException;
import cn.com.oasis.base.net.okhttpnet.converter.IConvertor;
import cn.com.oasis.base.net.okhttpnet.converter.JsonConverter;
import okhttp3.Response;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/10/8 下午5:15
 */

public abstract class GeneralCallback<T> extends Callback<T> {
    protected IConvertor iConvertor;

    public GeneralCallback() {
        iConvertor = new JsonConverter();
    }

    public GeneralCallback(IConvertor iConvertor) {
        this.iConvertor = iConvertor;
    }

    @Override
    public T parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        if (TextUtils.isEmpty(string)) {
            throw new NoDataException();
        }

        Class<T> beanCls = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (beanCls == String.class) {
            return (T) string;
        } else {
            T bean = iConvertor.convert(string, beanCls);
            return bean;
        }
    }
}
