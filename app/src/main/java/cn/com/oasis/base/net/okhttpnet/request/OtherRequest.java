package cn.com.oasis.base.net.okhttpnet.request;

import android.text.TextUtils;

import java.util.Map;

import cn.com.oasis.base.net.ionnet.INET;
import cn.com.oasis.base.net.okhttpnet.Exceptions;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.internal.http.HttpMethod;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/10/3 下午9:31
 */

public class OtherRequest extends OkHttpRequest implements INET {
    private static MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");
    private RequestBody requestBody;
    private String method;
    private String content;


    public OtherRequest(RequestBody requestBody, String url, Object tag, Map<String, String> params, Map<String, String> headers, int id, String method, String content) {
        super(url, tag, params, headers, id);
        this.requestBody = requestBody;
        this.method = method;
        this.content = content;
    }


    @Override
    protected RequestBody buildRequestBody() {
        if (requestBody == null && TextUtils.isEmpty(content) && HttpMethod.requiresRequestBody(method)) {
            Exceptions.illegalArgument("requestBody and content can not be null in method:" + method);
        }

        if (requestBody == null && !TextUtils.isEmpty(content)) {
            requestBody = RequestBody.create(MEDIA_TYPE_PLAIN, content);
        }

        return requestBody;
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        if (method.equals(PUT)) {
            builder.put(requestBody);
        } else if (method.equals(DELETE)) {
            if (requestBody == null) {
                builder.delete();
            } else {
                builder.delete(requestBody);
            }
        } else if (method.equals(HEAD)) {
            builder.head();
        } else if (method.equals(PATCH)) {

        }
        return builder.build();
    }
}
