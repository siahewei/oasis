package cn.com.oasis.base.net.okhttpnet.builder;

import android.net.Uri;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import cn.com.oasis.base.net.okhttpnet.request.GetRequest;
import cn.com.oasis.base.net.okhttpnet.request.RequestCall;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/9/26 下午11:19
 */

public class GetBuilder extends ReqestBuilder<GetBuilder> implements IHasParamsable{


    @Override
    public GetBuilder params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    @Override
    public GetBuilder params(String key, String val) {
        if (params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(key, val);
        return this;
    }

    @Override
    public RequestCall build() {
        if (params != null){
            url = appendParams(url, params);
        }
        return new GetRequest(url, tag, params, headers, id).build();
    }

    protected String appendParams(String url, Map<String, String> params){
        if (url == null || params == null || params.isEmpty()){
            return url;
        }

        Uri.Builder builder = Uri.parse(url).buildUpon();
        Set<String> keys = params.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            builder.appendQueryParameter(key, params.get(key));
        }
        return builder.build().toString();
    }


}
