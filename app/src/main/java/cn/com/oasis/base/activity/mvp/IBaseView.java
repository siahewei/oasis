package cn.com.oasis.base.activity.mvp;

import android.app.Activity;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/7/28 下午8:27
 */
public interface IBaseView extends IBaseDialogView,IBaseLodingView {
    String getRequestTag();

    Activity getBaseActivity();

    //fragment中是否有数据，请求成功后赋予数据
    boolean isHaveSuccessData();

    void setHaveSuccessData(boolean have);

    void toast(String hint);
}
