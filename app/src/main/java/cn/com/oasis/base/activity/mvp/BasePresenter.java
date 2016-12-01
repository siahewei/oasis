/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/7/28 下午8:27
 */

package cn.com.oasis.base.activity.mvp;

import android.support.annotation.NonNull;

import cn.com.oasis.base.net.ionnet.NetInterfaceFactory;
import cn.com.oasis.base.net.ionnet.RequestParem;
import cn.com.oasis.base.net.ionnet.request.RequestListner;

public class BasePresenter<T extends IBaseView> {
    private final T view;
    public BasePresenter(@NonNull T view){
        this.view = view;
    }
    public <K> void request(@NonNull RequestParem requestParem, @NonNull RequestListner<K> requestListner){
        requestListner.setTag(view.getRequestTag());
        NetInterfaceFactory.getNetInterface().doRequest(requestParem,requestListner);
    }

}
