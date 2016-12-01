package cn.com.earth.vm;

import cn.com.earth.adapter.BaseViewHolder;
import cn.com.earth.adapter.IViewModelPosCalculator;
import cn.com.earth.adapter.animation.LoadMoreView;

/**
 * 介绍: pull up load more
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/12/1 下午4:19
 */

public class LoadMoreViewModel extends AbsViewMode<LoadMoreViewModel.LoadMoreBean, BaseViewHolder<LoadMoreViewModel.LoadMoreBean>> {
    public final static int LOADING_VIEW = IViewModelPosCalculator.LOADING_VIEW;

    LoadMoreView loadMoreView = new DefaultLoadMoreView();

    public LoadMoreViewModel() {
    }

    @Override
    protected int getDataItemType(int dataPos) {
        return startViewType + LOADING_VIEW;
    }

    @Override
    protected void bindViewHolder(BaseViewHolder hoder, int dataPos) {
        loadMoreView.convert(hoder, -1, dataPos);
    }

    @Override
    protected int getLayoutId(int viewTypeInVieMode) {
        return loadMoreView.getLayoutId();
    }

    public void loadMoreComplete() {
        loadMoreView.setmLoadMoreStatus(LoadMoreView.STATUS_DEFAULT);
        observer.notifyDataChanged();
    }

    public void startLoading() {
        loadMoreView.setmLoadMoreStatus(LoadMoreView.STATUS_DEFAULT);
        observer.notifyDataChanged();
    }

    public void loadMoreEnd() {
        if (loadMoreView.isLoadEndGone()) {
            observer.notifyItemRemoved(list.size());
        } else {
            loadMoreView.setmLoadMoreStatus(LoadMoreView.STATUS_END);
            observer.notifyItemChanged(list.size());
        }
    }

    public void setEnableLoadMore(boolean isGone) {
        loadMoreView.setLoadEndGone(isGone);
    }

    public void setEnabled(boolean enabled) {
        if (!enabled) {
            if (!list.isEmpty()) {
                remove(0);
            }
        } else {
            if (list.isEmpty()) {
                loadMoreView.setmLoadMoreStatus(LoadMoreView.STATUS_DEFAULT);
                add(new LoadMoreBean());
            }
        }
    }

    public void setmLoadMoreStatus(int status) {
        loadMoreView.setmLoadMoreStatus(status);
    }

    public static class LoadMoreBean {
        String content;
    }
}
