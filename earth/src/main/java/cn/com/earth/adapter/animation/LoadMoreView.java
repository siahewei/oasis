package cn.com.earth.adapter.animation;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;

import cn.com.earth.adapter.BaseViewHolder;
import cn.com.earth.vm.IViewHoderConverter;

/**
 * 介绍: 用于recyclerview的加载更多
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/11/28 下午6:52
 */

public abstract class LoadMoreView implements IViewHoderConverter {
    public static final int STATUS_DEFAULT = 1;     //默认
    public static final int STATUS_LOADING = 2;     // loading
    public static final int STATUS_FAIL = 3;        // 失败
    public static final int STATUS_END = 4;         //结束
    protected int mLoadMoreStatus = STATUS_DEFAULT;
    private boolean loadEndGone = false;

    public int getmLoadMoreStatus() {
        return mLoadMoreStatus;
    }

    public void setmLoadMoreStatus(int mLoadMoreStatus) {
        this.mLoadMoreStatus = mLoadMoreStatus;
    }

    public abstract
    @LayoutRes
    int getLayoutId();

    /**
     * loadingview
     *
     * @return
     */
    protected abstract
    @IdRes
    int getLoadingId();

    /**
     * failedview
     *
     * @return
     */
    protected abstract
    @IdRes
    int getLoadFaildId();

    /**
     * loadendview
     *
     * @return
     */
    protected abstract
    @IdRes
    int getLoadEndId();

    /**
     * @return
     */
    public boolean isLoadEndGone(){
        return loadEndGone;
    }

    public boolean isLoadFaild() {
        return mLoadMoreStatus == STATUS_FAIL;
    }

    public abstract void ifNeddStartLoading(BaseViewHolder holder);

    public abstract void ifNeddStopLoading(BaseViewHolder holder);

    public void setLoadEndGone(boolean loadEndGone) {
        this.loadEndGone = loadEndGone;
    }
}