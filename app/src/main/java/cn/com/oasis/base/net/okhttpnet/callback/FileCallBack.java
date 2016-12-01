package cn.com.oasis.base.net.okhttpnet.callback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.com.oasis.base.net.okhttpnet.OkHttpUtils;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/10/4 上午12:00
 */

public abstract class FileCallBack extends Callback<File> {
    private String destFileDir;
    private String destFileName;

    public FileCallBack(String destFileDir, String destFileName) {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
    }

    @Override
    public File parseNetworkResponse(Response response, int id) throws Exception {
        return saveFile(response, id);
    }

    @Override
    public void onError(Call call, Exception e, int id) {

    }

    private File saveFile(Response response, final int id) {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        is = response.body().byteStream();
        final long total = response.body().contentLength();
        long sum = 0;
        File dir = new File(destFileDir);
        if (!dir.exists()) {
            dir.mkdir();
        }

        File file = new File(destFileDir, destFileName);
        try {
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                sum += len;
                fos.write(buf, 0, len);
                final long finalSum = sum;
                OkHttpUtils.getInstance().getDelivery().execute(new Runnable() {
                    @Override
                    public void run() {
                        onProgress(finalSum * 1.0f / total, total, id);
                    }
                });
            }

            fos.flush();

            return file;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            response.body().close();
            if (is != null) try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}
