package cn.com.earth.vm;

import cn.com.earth.R;
import cn.com.earth.adapter.BaseViewHolder;
import cn.com.earth.adapter.animation.LoadMoreView;
import cn.com.earth.widget.loadview.AppProgressBar;

/**
 * 介绍:
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/11/28 下午9:38
 */

public class DefaultLoadMoreView extends LoadMoreView {
    @Override
    public int getLayoutId() {
        return R.layout.default_loading_more_view;
    }

    @Override
    protected int getLoadingId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFaildId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndId() {
        return R.id.load_more_load_end_view;
    }

    @Override
    public boolean isLoadEndGone() {
        return false;
    }

    private void visibleLoading(BaseViewHolder holder, boolean b) {
        holder.setVisible(getLoadingId(), b);
    }

    private void visibleLoadFail(BaseViewHolder holder, boolean b) {
        holder.setVisible(getLoadFaildId(), b);
    }

    void visibleLoadEnd(BaseViewHolder holder, boolean b) {
        holder.setVisible(getLoadEndId(), b);
    }

    @Override
    public void convert(BaseViewHolder holder, int ViewPos, int dataPos) {
        switch (mLoadMoreStatus) {
            case STATUS_LOADING:
                visibleLoading(holder, true);
                visibleLoadFail(holder, false);
                visibleLoadEnd(holder, false);
                break;
            case STATUS_FAIL:
                visibleLoading(holder, false);
                visibleLoadFail(holder, true);
                visibleLoadEnd(holder, false);
                break;
            case STATUS_END:
                visibleLoading(holder, false);
                visibleLoadFail(holder, false);
                visibleLoadEnd(holder, true);
                break;
        }
    }

    public void ifNeddStartLoading(BaseViewHolder holder) {
        if (mLoadMoreStatus == STATUS_LOADING) {
            ((AppProgressBar) holder.getView(R.id.progress)).startAnimation();
        }
    }

    public void ifNeddStopLoading(BaseViewHolder holder) {
        if (mLoadMoreStatus == STATUS_LOADING) {
            ((AppProgressBar) holder.getView(R.id.progress)).stopAnimation();
        }
    }
}
