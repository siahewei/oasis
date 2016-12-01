package cn.com.oasis.base.net.ionnet;
import cn.com.oasis.base.net.ionnet.exception.DataException;
import cn.com.oasis.base.net.ionnet.exception.ServerException;
import cn.com.oasis.base.net.ionnet.exception.ServerTokenException;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/7/22 上午10:40
 */
public interface InterceptNet {
    /**
     *
     * @param resopne  接口返回的原始字符串
     * @return         返回结果见InterceptResult
     * @throws DataException
     * @throws ServerException
     */
    InterceptResult handler(String resopne) throws DataException,ServerException, ServerTokenException;
}
