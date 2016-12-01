package cn.com.oasis.base.net.okhttpnet.builder;

import cn.com.oasis.base.net.okhttpnet.request.OtherRequest;
import cn.com.oasis.base.net.okhttpnet.request.RequestCall;
import okhttp3.RequestBody;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/10/3 下午9:29
 */

public class OtherRequestBuilder extends ReqestBuilder<OtherRequestBuilder>{
    private RequestBody requestBody;
    private String method;
    private String content;

    public OtherRequestBuilder(String method) {
        this.method = method;

    }

    @Override
    public RequestCall build() {
        return new OtherRequest(requestBody, url, tag, params, headers, id, method, content).build();
    }

    public OtherRequestBuilder requestBody(String content){
        this.content = content;
        return this;
    }

    public OtherRequestBuilder requestBody(RequestBody requestBody){
        this.requestBody = requestBody;
        return this;
    }
}
