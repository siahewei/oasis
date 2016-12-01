package cn.com.oasis.base.net.okhttpnet.request;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/10/13 下午9:17
 */

public interface OnProgressListener {
    void onProgress(long byteWritten, long contentLength);
}
