package cn.com.oasis.base.activity.mvp;


/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/7/28 下午8:27
 */
public interface IBaseDialogView {
    void showWaitDialog(String title);

    void delayDismissWaitDilaog(String msg);

    void dismissWaitDialog();

}
