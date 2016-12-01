package cn.com.oasis.base.net.okhttpnet.request;

import com.google.gson.Gson;

import java.util.Map;

import cn.com.oasis.base.net.okhttpnet.Exceptions;
import cn.com.oasis.base.net.okhttpnet.callback.Callback;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/9/26 下午11:56
 */

public abstract class OkHttpRequest {

    /**
     * 基本参数, url, tag, params, body,
     */

    protected static Gson gson = new Gson();
    protected String url;
    protected Object tag;
    protected Map<String, String> params;
    protected Map<String, String> headers;
    protected int id;

    protected Request.Builder builder = new Request.Builder();

    protected OkHttpRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers, int id) {
        this.url = url;
        this.tag = tag;
        this.params = params;
        this.headers = headers;
        this.id = id;

        if (url == null) {
            Exceptions.illegalArgument("url can not be null.");
        }
        initBuilder();
    }

    protected void initBuilder() {
        builder.url(url).tag(tag);
        addHeaders();
    }

    protected void addHeaders() {
        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers == null || headers.isEmpty()) return;
        for (String key : headers.keySet()) {
            headerBuilder.add(key, headers.get(key));
        }

        builder.headers(headerBuilder.build());
    }

    public int getId() {
        return id;
    }

    public Request generateRequest(Callback callback) {
        RequestBody requestBody =  buildRequestBody();
        RequestBody wrappedRequestBody = wrapRequestBody(requestBody, callback);
        Request request = buildRequest(wrappedRequestBody);
        return request;
    }

    protected RequestBody wrapRequestBody(RequestBody requestBody, final Callback callback) {
        return requestBody;
    }

    public RequestCall build(){
        return new RequestCall(this);
    }

    protected abstract RequestBody buildRequestBody();
    protected abstract Request buildRequest(RequestBody requestBody);
}