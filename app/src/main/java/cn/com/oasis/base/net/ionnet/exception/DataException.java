package cn.com.oasis.base.net.ionnet.exception;

/**
 * @author jackyhe
 * @time   2016/7/28 16:14
 */
public class DataException extends Exception {


    public static final String ERROR_PARSE = "数据解析错误";

    public static final String ERROR_NO_DATA = "无数据";

    public DataException(String detailMessage) {
        super(detailMessage);
    }

    public DataException(String message,Throwable throwable) {
        super(ERROR_PARSE,throwable);
    }

    public DataException(){
        super(ERROR_PARSE);
    }
}
