package cn.com.oasis.base.net.ionnet.convert;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cn.com.oasis.base.net.ionnet.exception.DataException;
import cn.com.oasis.base.net.ionnet.exception.NoDataException;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/7/28 下午8:55
 */
public  class GsonListConvertJson<T> extends BaseGsonConvertJson<List<T>> {
    private final Class<T> clazz;

    public GsonListConvertJson(Class<T> clazz) {
        this.clazz = clazz;
    }
    @Override
    public List<T> convert(String str) throws DataException {
        List<T> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(str);
            for(int i = 0;i<jsonArray.length();i++){
                String beanString = jsonArray.getString(i);
                list.add(gson.fromJson(beanString,clazz));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataException();
        }
        if(list.size()==0){
            throw new NoDataException();
        }
        return list;
    }
}
