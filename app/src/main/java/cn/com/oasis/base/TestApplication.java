package cn.com.oasis.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import java.util.List;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/12/12 上午7:24
 */

public class TestApplication extends Application {

    // 设置服务器的地址
    private final static String serverUrl = "http://collector.yeahfans.com:8088/collector/record/info";

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * 注意只有进程名和包名一样 才执行初始化操作初始化, 如果该应用中不存在多个进程, 直接初始化
         */
        //if (null != getPackageName() && getPackageName().equals(getProcessName(this, android.os.Process.myPid()))) {
//        GegoSdkAPI.init(this, true, serverUrl);
//        GegoSdkAPI.getInstance().updateLocation(0f, 0f);
        //}
    }

    /**
     * 注意只有进程名和包名一样 才执行初始化操作初始化
     * 为了避免,其他进程被杀死会系统会自动 初始化多次
     *
     * @param cxt
     * @param pid
     * @return
     */
    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }
}
