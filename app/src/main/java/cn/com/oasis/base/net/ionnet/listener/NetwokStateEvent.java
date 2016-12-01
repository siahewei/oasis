package cn.com.oasis.base.net.ionnet.listener;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/7/22 上午10:40
 */
public class NetwokStateEvent {

    private boolean isOk;

    public NetwokStateEvent(boolean isOk) {
        this.isOk = isOk;
    }

    public boolean isOk() {
        return isOk;
    }
}
