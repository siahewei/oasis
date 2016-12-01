package cn.com.oasis.base.net.ionnet.listener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/7/22 上午10:40
 */
public class NetWorkStateReceiver extends BroadcastReceiver {
    private boolean update = false;


    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {

            ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connManager.getActiveNetworkInfo();
            if (!update && info != null && info.isAvailable()){
                update = true;
                String name = info.getTypeName();
                if (name.equals("WIFI")) {
                    wifiConnected();
                } else {
                    mobileConnected();
                }
            } else {
                update = false;
                disConnected();
            }
        }
    }

    //wifi连接上
    private void wifiConnected(){

    }

    //移动网络连接上
    private void mobileConnected(){

    }

    //网络断开
    private void disConnected(){

    }

}
