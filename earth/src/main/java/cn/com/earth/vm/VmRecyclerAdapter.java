package cn.com.earth.vm;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import cn.com.earth.adapter.AbsRecyclerAdapter;
import cn.com.earth.adapter.BaseViewHolder;
import cn.com.earth.adapter.animation.LoadMoreView;

/**
 * 介绍: ViewMode Adapter
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/11/30 下午9:38
 */

public class VmRecyclerAdapter extends AbsRecyclerAdapter<BaseViewHolder> implements ILoadingMore {
    /**
     * manager ViewMode
     */
    protected VmRecyclerAdapterHelper adapterHelper;

    protected boolean mLoadMoreEnabled = false;
    protected boolean nextLoadMoreEnabled = true;
    protected ILoadingMore loadingMoreController;

    public VmRecyclerAdapter(AbsViewMode... viewModes) {
        this.adapterHelper = new VmRecyclerAdapterHelper(this, viewModes);
        this.loadingMoreController = adapterHelper.getLoadingMoreController();
    }

    public void addViewModle(AbsViewMode viewMode) {
        adapterHelper.addViewModel(viewMode);
        this.loadingMoreController = adapterHelper.getLoadingMoreController();
        notifyItemRangeInserted(adapterHelper.getItemCount() - viewMode.getDataItemCount(), adapterHelper.getItemCount());
    }

    /**
     * set full span
     *
     * @param fullSpan
     */
    public void setFullSpan(int fullSpan) {
        this.adapterHelper.setSpan(fullSpan);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AbsViewMode viewMode = adapterHelper.getViewModeByViewType(viewType);
        int viewTypeInVieMode = adapterHelper.getViewTypeInViewMode(viewType);
        return viewMode.onCreateViewHolder(parent, viewTypeInVieMode);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        int[] dataPos = new int[1];
        AbsViewMode viewMode = adapterHelper.getViewModeByPos(position, dataPos);
        viewMode.bindViewHolder(holder, dataPos[0]);
    }

    @Override
    public int getItemViewType(int position) {
        int[] dataPos = new int[1];
        AbsViewMode viewMode = adapterHelper.getViewModeByPos(position, dataPos);
        onAutoLoadMore(viewMode, position, dataPos[0]);
        return viewMode.getDataItemType(dataPos[0]);
    }

    @Override
    public int getItemCount() {
        return adapterHelper.getItemCount();
    }

    @Override
    public void onViewAttachedToWindow(BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getAdapterSpanSize(position);
                }
            });
        }
    }

    public int getAdapterSpanSize(int position) {
        int[] dataPos = new int[1];
        AbsViewMode viewMode = adapterHelper.getViewModeByPos(position, dataPos);
        return viewMode.getSpan(dataPos[0]);
    }

    @Override
    public int onCalModePos(int ViewPos) {
        return ViewPos;
    }


    @Override
    public void onViewDetachedFromWindow(BaseViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    // load more controller
    private void onAutoLoadMore(AbsViewMode viewMode, int viewPosition, int dataPos) {
        if (mLoadMoreEnabled && nextLoadMoreEnabled) {
            if ((viewMode.getDataItemType(dataPos) - adapterHelper.getLastStartType()) == LOADING_VIEW) {
                if (onVmLoadMoreListener != null) {
                    onVmLoadMoreListener.onLoadMore();

                    if (loadingMoreController != null) {
                        loadingMoreController.setLoadStatus(LoadMoreView.STATUS_LOADING);
                    }
                }
            }
        }
    }

    protected OnVmLoadMoreListener onVmLoadMoreListener;

    public void setOnVmLoadMoreListener(OnVmLoadMoreListener onVmLoadMoreListener) {
        this.onVmLoadMoreListener = onVmLoadMoreListener;
        this.nextLoadMoreEnabled = true;
    }

    @Override
    public void loadmoreEnd() {
        if (loadingMoreController != null) {
            nextLoadMoreEnabled = false;
            loadingMoreController.loadmoreEnd();
        }
    }

    @Override
    public void loadmoreCompleted() {
        if (loadingMoreController != null) {
            loadingMoreController.loadmoreCompleted();
        }
    }

    @Override
    public void loadFailed() {
        if (loadingMoreController != null) {
            nextLoadMoreEnabled = false;
            loadingMoreController.loadFailed();
        }
    }

    @Override
    public void startLoading() {
        if (loadingMoreController != null) {
            loadingMoreController.startLoading();
        }
    }

    @Override
    public void setLoadEndGone(boolean isGone) {
        if (loadingMoreController != null) {
            loadingMoreController.setLoadEndGone(isGone);
        }
    }

    @Override
    public void setLoadmoreEnable(boolean enabled) {
        this.mLoadMoreEnabled = enabled;
        if (enabled){
            this.nextLoadMoreEnabled = true;
        }
        loadingMoreController.setLoadmoreEnable(enabled);
    }

    @Override
    public void setLoadStatus(int status) {
        if (loadingMoreController != null) {
            loadingMoreController.setLoadStatus(status);
        }
    }

    public void clear() {
        adapterHelper.clear();
    }

    public interface OnVmLoadMoreListener {
        void onLoadMore();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
