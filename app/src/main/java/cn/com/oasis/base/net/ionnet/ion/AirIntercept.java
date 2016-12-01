package cn.com.oasis.base.net.ionnet.ion;

import android.text.TextUtils;

import org.json.JSONObject;

import cn.com.oasis.base.net.ionnet.InterceptNet;
import cn.com.oasis.base.net.ionnet.InterceptResult;
import cn.com.oasis.base.net.ionnet.exception.DataException;
import cn.com.oasis.base.net.ionnet.exception.ErroCode;
import cn.com.oasis.base.net.ionnet.exception.ServerException;
import cn.com.oasis.base.net.ionnet.exception.ServerTokenException;


/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/7/28 下午8:55
 */
public class AirIntercept implements InterceptNet {
    @Override
    public InterceptResult handler(String resopne) throws DataException, ServerException, ServerTokenException {
        String message = null;
        int code = -1;
        try {
            JSONObject jsonObject = new JSONObject(resopne);
            int errorcode = jsonObject.optInt("flag");
            code = errorcode;
            message = jsonObject.optString("msg");
            if (errorcode == 0) {
                return new InterceptResult(resopne, message);
            } else {
                message = ErroCode.getErrorMsg(errorcode);
                throw new Exception();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (code == 40006 || code == 40007) {
                throw new ServerTokenException(message, code);
            } else if (TextUtils.isEmpty(message)){
                throw new DataException();
            }else{
                throw new ServerException(message, code);
            }
        }
    }
}
