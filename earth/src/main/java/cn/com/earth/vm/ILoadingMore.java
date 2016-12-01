package cn.com.earth.vm;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/12/2 上午1:09
 */

public interface ILoadingMore {
    void loadmoreEnd();

    void loadmoreCompleted();

    void loadFailed();

    void startLoading();

    void setLoadEndGone(boolean isGone);

    void setLoadmoreEnable(boolean enabled);

    void setLoadStatus(int status);
}
