package cn.com.oasis.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import cn.com.oasis.R;
import cn.com.oasis.base.net.okhttpnet.OkHttpUtils;
import cn.com.oasis.base.net.okhttpnet.callback.Callback;
import cn.com.oasis.base.net.okhttpnet.callback.FileCallBack;
import cn.com.oasis.base.net.okhttpnet.callback.GeneralCallback;
import cn.com.oasis.base.net.okhttpnet.callback.GeneralListCallback;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/10/8 下午12:52
 */

public class OkhttpDemo extends Activity {
    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.okhttp_demo);
        initView();
    }

    private void initView() {
        tvContent = (TextView) findViewById(R.id.tv_content);
        findViewById(R.id.tv_get).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getString();
            }
        });

        findViewById(R.id.tv_get_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUser();
            }
        });

        findViewById(R.id.tv_get_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListUser();
            }
        });

        findViewById(R.id.tv_get_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        findViewById(R.id.tv_post_string).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postString("cc");
            }
        });

        findViewById(R.id.tv_post_json).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.tv_post_file).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.tv_post_url).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    private void getString() {
        String getUrl = OkUrl.getUrl();
        OkHttpUtils.get().url(getUrl).id(100).build().execute(new Callback<String>() {
            @Override
            public String parseNetworkResponse(Response response, int id) throws Exception {
                return response.body().string();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                int a = 0;
            }

            @Override
            public void onResponse(String response, int id) {
                setTvContent(response + ":" + id);
            }
        });
    }

    private void getUser() {
        String getUrser = OkUrl.getUser();
        OkHttpUtils.get().url(getUrser).id(101)
                .params("user", "123")
                .build().execute(new GeneralCallback<User>() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (e != null) {
                    setTvContent(e.toString());
                }
            }

            @Override
            public void onResponse(User response, int id) {
                if (response != null) {
                    setTvContent(response.toString());
                }
            }
        });
    }

    private void getListUser() {
        String getuuses = OkUrl.getUserList();
        OkHttpUtils.get().url(getuuses).id(102).build().execute(new GeneralListCallback<User>() {
            @Override
            public void onError(Call call, Exception e, int id) {
                setTvContent(e.toString());
            }

            @Override
            public void onResponse(List<User> response, int id) {
                setTvContent(getListString(response));
            }
        });
    }

    private String getListString(List<User> list) {
        StringBuilder sb = new StringBuilder();
        for (User user : list) {
            sb.append(user.toString());
        }
        return sb.toString();
    }

    private void postString(String param) {
        String postString = OkUrl.postString();
        OkHttpUtils.post().url(postString).params("adda", param)
                .addHeader("cc", "dd")
                .build().execute(new GeneralCallback<String>() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                setTvContent(response);
            }
        });
    }

    private void postFile() {
        String postFile = OkUrl.postFile();
        File file = new File(Environment.getExternalStorageDirectory(), "test.png");
        if (!file.exists()) {
            OkHttpUtils.post().url(postFile).addFile("file", "test.png", file).build().execute(new GeneralCallback() {

                @Override
                public void onError(Call call, Exception e, int id) {
                }

                @Override
                public void onResponse(Object response, int id) {

                }

                @Override
                public void onProgress(float progress, long total, int id) {
                    super.onProgress(progress, total, id);
                }
            });
        }
    }

    protected void downLoadFile() {
        String postFile = OkUrl.postFile();
        String filePath = "";
        String fileName = "";
        OkHttpUtils.post().url(postFile).build().execute(new FileCallBack(fileName, filePath) {
            @Override
            public void onResponse(File response, int id) {

            }

            @Override
            public void onProgress(float progress, long total, int id) {
                super.onProgress(progress, total, id);
            }
        });
    }

    private void setTvContent(String response) {
        tvContent.setText(response);
    }

}
