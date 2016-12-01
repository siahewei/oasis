package cn.com.oasis.base.net.ionnet;

/**
 * Created by huyf on 2016/3/4.
 */
public interface ResultCode {
    int SUCCESS = -10000;
    int ERROR_DATA = -10001;
    int ERROR_UI = -10002;
    int EROOR_NET = -10003;
    int ERROR_CANCEL = -10004;
    int ERROR_NO_DATA = -10005;
    int ERROR_NO_NET = -10006;
    int ERROR_OTHER = -10007;
    int ERROR_TIMEOUT = -10008;
}
