package cn.com.oasis.base.net.okhttpnet.callback;

import android.text.TextUtils;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import cn.com.oasis.base.net.ionnet.exception.NoDataException;
import cn.com.oasis.base.net.okhttpnet.converter.IConvertor;
import cn.com.oasis.base.net.okhttpnet.converter.JsonConverter;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/10/11 下午9:15
 */

public abstract class GeneralListCallback<T> extends Callback<List<T>> {
    protected IConvertor iConvertor;
    public GeneralListCallback() {
        iConvertor = new JsonConverter();
    }

    public GeneralListCallback(IConvertor iConvertor) {
        this.iConvertor = iConvertor;
    }
    @Override
    public List<T> parseNetworkResponse(Response response, int id) throws Exception {
        String jsonString = response.body().string();
        if (TextUtils.isEmpty(jsonString)) {
            throw new NoDataException();
        }
        Class<T> beanCls = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return iConvertor.converts(jsonString, beanCls);
    }

    @Override
    public void onError(Call call, Exception e, int id) {

    }

    @Override
    public void onResponse(List<T> response, int id) {

    }
}
