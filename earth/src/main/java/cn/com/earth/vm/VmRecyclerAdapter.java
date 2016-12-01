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

public class VmRecyclerAdapter extends AbsRecyclerAdapter<BaseViewHolder> {
    /**
     * manager ViewMode
     */
    private VmRecyclerAdapterHelper adapterHelper;

    protected boolean mLoadMoreEnabled = false;

    public VmRecyclerAdapter(AbsViewMode... viewModes) {
        this.adapterHelper = new VmRecyclerAdapterHelper(this, viewModes);
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
        if (mLoadMoreEnabled) {
            if ((viewMode.getDataItemType(dataPos) - adapterHelper.getLastStartType()) == LOADING_VIEW) {
                if (onVmLoadMoreListener != null) {
                    onVmLoadMoreListener.onLoadMore();

                    if (viewMode instanceof LoadMoreViewModel){
                        ((LoadMoreViewModel)viewMode).setmLoadMoreStatus(LoadMoreView.STATUS_LOADING);
                    }
                }
            }
            adapterHelper.getViewModeByPos(viewPosition);
        }
    }

    protected OnVmLoadMoreListener onVmLoadMoreListener;

    public void setOnVmLoadMoreListener(OnVmLoadMoreListener onVmLoadMoreListener) {
        this.onVmLoadMoreListener = onVmLoadMoreListener;
    }

    public void setmLoadMoreEnabled(boolean mLoadMoreEnabled) {
        this.mLoadMoreEnabled = mLoadMoreEnabled;
    }

    public interface OnVmLoadMoreListener {
        void onLoadMore();
    }
}
