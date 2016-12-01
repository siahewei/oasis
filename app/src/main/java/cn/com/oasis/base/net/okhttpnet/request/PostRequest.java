package cn.com.oasis.base.net.okhttpnet.request;

import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import cn.com.oasis.base.net.okhttpnet.OkHttpUtils;
import cn.com.oasis.base.net.okhttpnet.builder.PostBuilder;
import cn.com.oasis.base.net.okhttpnet.callback.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/10/2 下午1:24
 */

public class PostRequest extends OkHttpRequest {
    private static final MediaType CONTENT_FORM_TYPE =
            MediaType.parse("application/x-www-form-urlencoded");
    private MediaType mediaType;
    private List<PostBuilder.FileInput> files ;

    public PostRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers, MediaType mediaType, List<PostBuilder.FileInput> files, int id) {
        super(url, tag, params, headers, id);
        this.mediaType = mediaType;
        this.files = files;
        if (mediaType == null) {
            this.mediaType = CONTENT_FORM_TYPE;
        }
    }

    @Override
    protected RequestBody buildRequestBody() {
        if (files == null || files.isEmpty()) {

            if (CONTENT_FORM_TYPE.equals(mediaType)) {
                // form
                FormBody.Builder build = new FormBody.Builder();
                if (params == null || params.isEmpty()){
                    throw new IllegalArgumentException("body param is empty");
                }else {
                    for (String key : params.keySet()){
                        build.add(key, params.get(key));
                    }
                    return build.build();
                }
            } else {
                // json
                String content = getContent();
                if (content == null) {
                    throw new IllegalArgumentException("body param is empty");
                } else {
                    return RequestBody.create(mediaType, content);
                }
            }
        } else {
            // file
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            addParams(builder);
            for (PostBuilder.FileInput file :files){
                RequestBody fileBody = RequestBody.create(MediaType.parse(getMimeType(file.fileName)), file.file);
                builder.addFormDataPart(file.key, file.fileName, fileBody);
            }

            return builder.build();
        }
    }

    private String getMimeType(String path){
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = null;
        try {
            contentTypeFor = fileNameMap.getContentTypeFor(URLEncoder.encode(path, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (contentTypeFor == null){
            contentTypeFor = "application/octet-steam";
        }

        return contentTypeFor;
    }

    private void addParams(MultipartBody.Builder builder){
        if (params != null && !params.isEmpty()){
            for (String key : params.keySet()){
                builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + key + "\""),
                        RequestBody.create(null, params.get(key)));
            }
        }
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        return builder.post(requestBody).build();
    }


    private String getContent() {
        if (params != null && !params.isEmpty()) {
            return gson.toJson(params);
        }
        return null;
    }

    @Override
    protected RequestBody wrapRequestBody(RequestBody requestBody, final Callback callback) {
        if (callback == null) return requestBody;
        else {
            CountingRequestBody countingRequestBody = new CountingRequestBody(requestBody, new OnProgressListener() {
                @Override
                public void onProgress(final long byteWritten, final long contentLength) {
                    OkHttpUtils.getInstance().getDelivery().execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onProgress(byteWritten * 1.0f / contentLength, contentLength, id);
                        }
                    });
                }
            });
            return countingRequestBody;
        }
    }
}
