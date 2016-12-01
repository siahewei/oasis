package cn.com.oasis.base.net.ionnet.request;


import java.util.List;
import java.util.UUID;

import cn.com.oasis.base.net.ionnet.ResultMessage;
import cn.com.oasis.base.net.ionnet.ion.IonNetInterface;


/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/7/22 上午10:40
 */
public abstract class RequestListner<T> {

    private final Class<T> clazz;
    private Object tag;


    public RequestListner(Class<T> clazz) {
        tag = UUID.randomUUID().toString();
        this.clazz = clazz;
    }

    public RequestListner(Object tag, Class<T> clazz) {
        this.tag = tag;
        this.clazz = clazz;
    }

    public final void setTag(Object tag) {
        this.tag = tag;
    }

    /**
     * 开始网络请求
     */
    public void onStart() {

    }

    /**
     * 网络请求结束
     * @param e 请求结束后的处理结果
     */
    public void onEnd(ResultMessage e) {

    }

    public final void cancel() {
        IonNetInterface.get().cancel(tag);
    }

    public final Object getTag() {
        return tag;
    }


    public boolean onSuccess(T t) throws Exception {
        return true;
    }

    public boolean onSuccess(List<T> tList) throws Exception {
        return true;
    }

    public final Class<T> getClazz() {
        return clazz;
    }


}
