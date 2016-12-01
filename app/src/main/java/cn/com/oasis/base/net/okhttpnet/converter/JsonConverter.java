package cn.com.oasis.base.net.okhttpnet.converter;

import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/10/11 下午9:17
 */

public class JsonConverter implements IConvertor {
    public static Gson gson = new Gson();

    @Override
    public <T> T convert(String response, Class<T> cls) {
        return gson.fromJson(response, cls);
    }

    @Override
    public <T> List<T> converts(String response, Class<T> cls) {
        List<T> list = new ArrayList<>();
        if (response.equals("[]")){
            return list;
        }

        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                String beanString = jsonArray.getString(i);
                list.add(gson.fromJson(beanString, cls));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}
