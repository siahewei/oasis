package cn.com.oasis.base.net.ionnet.exception;
import cn.com.oasis.base.net.ionnet.ResultMessage;

/**
 * @author jackyhe
 * @time   2016/7/28 16:14
 * 服务器返回数据时 flag！=1时的各种异常，，如密码不正确等
 */
public class ServerTokenException extends Exception{
    private int errorCode;

    public ServerTokenException(String detailMessage, int errorCode) {
        super(detailMessage);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public ResultMessage parse(){
        return ResultMessage.error(errorCode, this);
    }
}
