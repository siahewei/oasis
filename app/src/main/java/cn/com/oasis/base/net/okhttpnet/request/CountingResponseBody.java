package cn.com.oasis.base.net.okhttpnet.request;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/10/13 下午9:13
 */

public class CountingResponseBody extends ResponseBody{
    private final ResponseBody responseBody;
    private final OnProgressListener onProgressListener;
    private BufferedSource bufferedSource;
    public CountingResponseBody(ResponseBody responseBody, OnProgressListener onProgressListener) {
        this.responseBody = responseBody;
        this.onProgressListener = onProgressListener;
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null){
            CountingSource countingSource = new CountingSource(responseBody.source());
            bufferedSource = Okio.buffer(countingSource);
        }
        return bufferedSource;
    }

     protected final class CountingSource extends ForwardingSource{
        private long total = 0;
        public CountingSource(okio.Source delegate) {
            super(delegate);
        }

        @Override
        public long read(Buffer sink, long byteCount) throws IOException {
            long bytesRead = super.read(sink, byteCount);
            total += bytesRead != -1 ? bytesRead : 0;
            onProgressListener.onProgress(total, contentLength());
            return super.read(sink, byteCount);
        }
    }
}
