package cn.com.oasis;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/10/3 下午5:53
 */

public class OkioDemo {
    public static void main(String[] args){
        Source source = null;
        BufferedSource bufferedSource = null;

        /***
         * sink 和source
         * sink 等价于 OutputStream
         * source 等价于 InputStream
         *
         *  子类: BufferdSink RealBufferdSink 定义了一系列的写入缓存区的方法, byte, int ,long, short
         *   ForwardingSink
         *   GzipSink GzinpSource 压缩
         *   DeflaterSInk
         *
         * 支持读写超时设置
         *
         */


        File file = new File("/Users/jacky/androiddvp/prjs/planet/planet/javademo/src/main/file/test.txt");
        try {
            source = Okio.source(file);
            bufferedSource = Okio.buffer(source);
            String content = bufferedSource.readUtf8();
            System.out.println(content);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            close(bufferedSource);
        }

        Sink sink = null;
        BufferedSink bufferedSink = null;

        try {
            File dest = new File("/Users/jacky/androiddvp/prjs/planet/planet/javademo/src/main/file/dest.txt");
            sink = Okio.sink(dest);
            bufferedSink = Okio.buffer(sink);
            bufferedSink.writeUtf8("132rraddaa");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            close(bufferedSink);
        }
    }

    private static void close(Closeable closeable){
        if (closeable != null){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
