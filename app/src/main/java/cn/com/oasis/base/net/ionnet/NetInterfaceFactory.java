package cn.com.oasis.base.net.ionnet;

import cn.com.oasis.base.net.ionnet.ion.IonNetInterface;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/7/22 上午10:40
 */
public class NetInterfaceFactory {
    public static NetInterface getNetInterface(){
        return IonNetInterface.get();
    }
}
