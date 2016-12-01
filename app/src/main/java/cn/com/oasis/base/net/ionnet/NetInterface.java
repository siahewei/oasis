package cn.com.oasis.base.net.ionnet;


import android.content.Context;

import cn.com.oasis.base.net.ionnet.convert.ConvertJson;
import cn.com.oasis.base.net.ionnet.request.RequestListner;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/7/22 上午10:40
 */
public interface NetInterface {
    /**
     * 初始化，在application的oncreate中调用
     *
     * @param app
     */
    void start(Context app);

    /**
     * 在合适的地方取消所有接口
     * 此处需要注意：如在退出最后一个Activity调用，会清掉appliaction的对象
     * 而系统会缓存app（时间不确定），如未被系统杀死，再次启动APP，不会执行oncreat方法
     * <p/>
     * 正确处理：1，在退出app时强制杀死进程，2不调用（建议）
     */
    void stop();

    <T> void doRequest(RequestParem requestParem, RequestListner<T> requestListner);

    <T> void doRequest(RequestParem requestParem, RequestListner<T> requestListner, ConvertJson<T> convertJson);

    <T> String doSyncRequest(String tag, RequestParem requestParem);

    void cancel(Object tag);
}
