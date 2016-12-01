package cn.com.oasis.base.activity.mvp;
import cn.com.oasis.base.net.ionnet.ResultMessage;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/7/28 下午8:27
 */
public interface IBaseLodingView {

    void showNoNetView();

    void showOtherErrorView(ResultMessage resultMessage);

    void showLodingView();

    void showNoDataView();

    void showSuccessView();


}
