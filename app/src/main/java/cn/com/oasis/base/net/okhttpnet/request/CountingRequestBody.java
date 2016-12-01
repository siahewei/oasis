package cn.com.oasis.base.net.okhttpnet.request;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/10/3 下午3:55
 */

public class CountingRequestBody extends RequestBody{
    protected RequestBody delegate;
    protected OnProgressListener listener;
    protected CountingSink countingSink;
    @Override
    public MediaType contentType() {
        return delegate.contentType();
    }

    public CountingRequestBody(RequestBody delegate,OnProgressListener listener) {
        this.delegate = delegate;
        this.listener = listener;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        countingSink = new CountingSink(sink);
        BufferedSink bufferedSink = Okio.buffer(countingSink);
        delegate.writeTo(bufferedSink);
        bufferedSink.flush();
    }

    @Override
    public long contentLength() throws IOException {
        try{
            return delegate.contentLength();
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    protected final class CountingSink extends ForwardingSink{
        private long byteWritten = 0;
        public CountingSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            byteWritten += byteCount;
            listener.onProgress(byteWritten, contentLength());
        }
    }
}
