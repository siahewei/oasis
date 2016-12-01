package cn.com.earth.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import cn.com.earth.adapter.animation.AlphaInAnimation;
import cn.com.earth.adapter.animation.BaseAnimation;
import cn.com.earth.adapter.animation.ScaleInAnimation;
import cn.com.earth.adapter.animation.SlideInBottomAnimation;
import cn.com.earth.adapter.animation.SlideInLeftAnimation;
import cn.com.earth.adapter.animation.SlideInRightAnimation;
import cn.com.earth.vm.DefaultLoadMoreView;
import cn.com.earth.vm.IViewHoderConverter;
import cn.com.earth.adapter.animation.LoadMoreView;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/11/28 下午8:36
 */

public abstract class BaseRecyclerAdapter<T, K extends BaseViewHolder<T>> extends AbsRecyclerAdapter<K> implements IViewHoderConverter<K>, IViewModelPosCalculator {

    protected List<T> list;
    private Context context;
    private LayoutInflater layoutInflater;
    private boolean mHeadAndEmptyEnable = false;
    private boolean mFootAndEmptyEnable = false;
    //header footer
    private LinearLayout mHeaderLayout;
    private LinearLayout mFooterLayout;
    private LoadMoreView mLoadMoreView = new DefaultLoadMoreView();

    private FrameLayout mEmptyView;
    private boolean mIsUseEmpty = true;

    private OnLoadMoreListener onLoadMoreListener;
    private boolean mLoading = false;
    private boolean mNextLoadEnable = false;
    private boolean mLoadMoreEnable = false;

    private int mLastPosition = -1;

    public BaseRecyclerAdapter(List<T> data) {
        this.list = data;
    }

    @Override
    public K onCreateViewHolder(ViewGroup parent, int viewType) {
        K baseViewHolder = null;
        this.context = parent.getContext();
        this.layoutInflater = LayoutInflater.from(context);
        switch (viewType) {
            case HEADER_VIEW:
                baseViewHolder = createBaseViewHolder(mHeaderLayout);
                break;
            case LOADING_VIEW:
                baseViewHolder = getLoadingView(parent);
                break;
            case FOOTER_VIEW:
                baseViewHolder = createBaseViewHolder(mFooterLayout);
                break;
            case EMPTY_VIEW:
                baseViewHolder = createBaseViewHolder(mEmptyView);
                break;
            default:
                baseViewHolder = onCreateDefViewHolder(layoutInflater, parent, viewType);
        }
        return baseViewHolder;
    }

    @Override
    public void onBindViewHolder(K holder, int position) {
        int viewType = holder.getItemViewType();
        int dataPos = -1;
        switch (viewType) {
            case 0:
                dataPos = position - getHeaderLayoutCount();
                if (holder.bindData(list.get(dataPos), position, dataPos)) {
                } else {
                    convert(holder, position, dataPos);
                }
                break;
            case LOADING_VIEW:
                mLoadMoreView.convert(holder, position, -1);
                break;

            case HEADER_VIEW:
                break;
            case EMPTY_VIEW:
                break;
            case FOOTER_VIEW:
                break;
            default:
                dataPos = position - getHeaderLayoutCount();
                if (holder.bindData(list.get(dataPos), position, dataPos)) {
                } else {
                    convert(holder, position, dataPos);
                }
                break;
        }
    }

    protected int getDefItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (getEmptyViewCount() == 1) {
            count = 1;

            if (mHeadAndEmptyEnable && getHeaderLayoutCount() != 0) {
                count++;
            }

            if (mFootAndEmptyEnable && getFooterLayoutCount() != 0) {
                count++;
            }
        } else {
            count = getHeaderLayoutCount() + list.size() + getFooterLayoutCount() + getLoadMoreViewCount();
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        Log.e("jacky", "origin" + position);
        if (getEmptyViewCount() == 1) {
            boolean header = mHeadAndEmptyEnable && getHeaderLayoutCount() != 0;
            switch (position) {
                case 0:
                    if (header) {
                        return HEADER_VIEW;
                    } else {
                        return EMPTY_VIEW;
                    }
                case 1:
                    if (header) {
                        return EMPTY_VIEW;
                    } else {
                        return FOOTER_VIEW;
                    }
                case 2:
                    return FOOTER_VIEW;
                default:
                    return EMPTY_VIEW;
            }
        }
        Log.e("jacky", position + "");
        autoLoadMore(position);
        int numHeaders = getHeaderLayoutCount();
        if (position < numHeaders) {
            return HEADER_VIEW;
        } else {
            int realPosInDataList = position - numHeaders;
            int adapterCount = list.size();
            if (realPosInDataList < adapterCount) {
                return getDefItemViewType(realPosInDataList);
            } else {
                realPosInDataList = realPosInDataList - adapterCount;
                int numFooters = getFooterLayoutCount();
                if (realPosInDataList < numFooters) {
                    return FOOTER_VIEW;
                } else {
                    return LOADING_VIEW;
                }
            }
        }
    }

    @Override
    public void onViewAttachedToWindow(K holder) {
        super.onViewAttachedToWindow(holder);
        int type = holder.getItemViewType();
        if (type == EMPTY_VIEW || type == HEADER_VIEW || type == FOOTER_VIEW || type == LOADING_VIEW) {
            setFullSpan(holder);
            if (type == LOADING_VIEW) {
                mLoadMoreView.ifNeddStartLoading(holder);
            }
        } else {
            addAnimation(holder);
        }
    }

    @Override
    public void onViewDetachedFromWindow(K holder) {
        super.onViewDetachedFromWindow(holder);
        int type = holder.getItemViewType();
        if (type == LOADING_VIEW) {
            mLoadMoreView.ifNeddStopLoading(holder);
            Log.e("jacky", "stop animation");
        }

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
                    int type = getItemViewType(position);
                    if (mSpanSizeLookup == null)
                        return (type == EMPTY_VIEW || type == HEADER_VIEW || type == FOOTER_VIEW || type == LOADING_VIEW) ? gridManager.getSpanCount() : 1;
                    else
                        return (type == EMPTY_VIEW || type == HEADER_VIEW || type == FOOTER_VIEW || type == LOADING_VIEW) ? gridManager.getSpanCount() : mSpanSizeLookup.getSpanSize(gridManager, position - getHeaderLayoutCount());
                }
            });
        }
    }


    protected void addAnimation(RecyclerView.ViewHolder holder) {
        if (mOpenAnimationEnable) {
            if (!mFirstOnlyEnable || holder.getLayoutPosition() > mLastPosition) {
                BaseAnimation animation = null;
                if (mCustomAnimation != null) {
                    animation = mCustomAnimation;
                } else {
                    animation = mSelectAnimation;
                }
                for (Animator anim : animation.getAnimators(holder.itemView)) {
                    startAnim(anim, holder.getLayoutPosition());
                }
                mLastPosition = holder.getLayoutPosition();
            }
        }
    }

    private K getLoadingView(ViewGroup parent) {
        View view = getItemView(mLoadMoreView.getLayoutId(), parent);
        K hoder = createBaseViewHolder(view);
        hoder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLoadMoreView.isLoadFaild()) {
                    mLoadMoreView.setmLoadMoreStatus(LoadMoreView.STATUS_DEFAULT);
                    notifyItemChanged(getHeaderLayoutCount() + list.size() + getFooterLayoutCount());
                }
            }
        });

        return hoder;
    }


    protected K createBaseViewHolder(View view) {
        return (K) new BaseViewHolder(view);
    }

    protected void setFullSpan(RecyclerView.ViewHolder holder) {
        if (holder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            params.setFullSpan(true);
        }
    }

    private View getItemView(@LayoutRes int layoutResId, ViewGroup parent) {
        return layoutInflater.inflate(layoutResId, parent, false);
    }

    public int getHeaderLayoutCount() {
        if (mHeaderLayout == null || mHeaderLayout.getChildCount() == 0) {
            return 0;
        }
        return 1;
    }

    public int getFooterLayoutCount() {
        if (mFooterLayout == null || mFooterLayout.getChildCount() == 0) {
            return 0;
        }

        return 1;
    }

    public int getEmptyViewCount() {
        if (mEmptyView == null || mEmptyView.getChildCount() == 0) {
            return 0;
        }

        if (!mIsUseEmpty) {
            return 0;
        }

        if (!list.isEmpty()) {
            return 0;
        }

        return 1;
    }

    protected abstract K onCreateDefViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType);


    public void addHeader(View header) {
        addHeader(header, -1);
    }

    public void addHeader(View header, int index) {
        addHeader(header, index, LinearLayout.VERTICAL);
    }

    public void addHeader(View header, int index, int orientation) {
        if (mHeaderLayout == null) {
            mHeaderLayout = new LinearLayout(header.getContext());
            if (orientation == LinearLayout.VERTICAL) {
                mHeaderLayout.setOrientation(LinearLayout.VERTICAL);
                mHeaderLayout.setLayoutParams(new RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            } else {
                mHeaderLayout.setOrientation(LinearLayout.HORIZONTAL);
                mHeaderLayout.setLayoutParams(new RecyclerView.LayoutParams(WRAP_CONTENT, MATCH_PARENT));
            }
        }

        index = index >= mHeaderLayout.getChildCount() ? -1 : index;
        mHeaderLayout.addView(header, index);
        if (mHeaderLayout.getChildCount() == 1) {
            int position = getHeaderViewPosition();
            if (position != -1) {
                notifyItemInserted(position);
            }
        }
    }

    public void addFooterView(View footer) {
        addFooterView(footer, -1);
    }

    public void addFooterView(View footer, int index) {
        if (mFooterLayout == null) {
            mFooterLayout = new LinearLayout(footer.getContext());
            mFooterLayout.setOrientation(LinearLayout.VERTICAL);
            mFooterLayout.setLayoutParams(new RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        }

        index = index >= mFooterLayout.getChildCount() ? -1 : index;
        mFooterLayout.addView(footer, index);
        if (mFooterLayout.getChildCount() == 1) {
            int postion = getFooterViewPosition();
            if (postion != -1) {
                notifyItemInserted(postion);
            }
        }
    }

    public void setHeaderAndEmpty(boolean isHeadAndEmpty) {
        setHeaderFooterEmpty(isHeadAndEmpty, false);
    }


    //set emptyView show if adapter is empty and want to show headview and footview
    public void setHeaderFooterEmpty(boolean isHeadAndEmpty, boolean isFootAndEmpty) {
        mHeadAndEmptyEnable = isHeadAndEmpty;
        mFootAndEmptyEnable = isFootAndEmpty;
    }

    public void setEmptyView(View emptyView) {
        boolean insert = false;
        if (mEmptyView == null) {
            mEmptyView = new FrameLayout(emptyView.getContext());
            mEmptyView.setLayoutParams(new RecyclerView.LayoutParams(MATCH_PARENT, MATCH_PARENT));
            insert = true;
        }
        mEmptyView.removeAllViews();
        mEmptyView.addView(emptyView);
        mIsUseEmpty = true;
        if (insert) {
            if (getEmptyViewCount() == 1) {
                int position = 0;
                if (mHeadAndEmptyEnable && getHeaderLayoutCount() != 0) {
                    position++;
                }
                notifyItemInserted(position);
            }
        }
    }

    public void removeHeaderView(View header) {
        if (getHeaderLayoutCount() == 0) return;
        mHeaderLayout.removeView(header);
        if (mHeaderLayout.getChildCount() == 0) {
            int postion = getHeaderViewPosition();
            if (postion != -1) {
                notifyItemRemoved(postion);
            }
        }
    }

    public void removeFooterView(View footer) {
        if (getHeaderLayoutCount() == 0) return;
        mFooterLayout.removeView(footer);
        if (mFooterLayout.getChildCount() == 0) {
            int postion = getHeaderViewPosition();
            if (postion != -1) {
                notifyItemRemoved(postion);
            }
        }
    }

    public void removeAllHeaderView() {
        if (getHeaderLayoutCount() == 0) return;

        mHeaderLayout.removeAllViews();
        int position = getHeaderViewPosition();
        if (position != -1) {
            notifyItemRemoved(position);
        }
    }

    public void removeAllFooterView() {
        if (getFooterLayoutCount() == 0) return;

        mFooterLayout.removeAllViews();
        int postion = getFooterViewPosition();
        if (postion != -1) {
            notifyItemRemoved(postion);
        }
    }


    private int getHeaderViewPosition() {
        if (getEmptyViewCount() == 1) {
            if (mHeadAndEmptyEnable) {
                return 0;
            }
        } else {
            return 0;
        }

        return -1;
    }

    private int getFooterViewPosition() {
        //Return to footer view notify position
        if (getEmptyViewCount() == 1) {
            int position = 1;
            if (mHeadAndEmptyEnable && getHeaderLayoutCount() != 0) {
                position++;
            }
            if (mFootAndEmptyEnable) {
                return position;
            }
        } else {
            return getHeaderLayoutCount() + list.size();
        }
        return -1;
    }

    private SpanSizeLookUp mSpanSizeLookup;

    public void setmSpanSizeLookup(SpanSizeLookUp mSpanSizeLookup) {
        this.mSpanSizeLookup = mSpanSizeLookup;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
        this.mNextLoadEnable = true;
        this.mLoadMoreEnable = true;
        this.mLoading = false;
    }

    public void loadMoreFail() {
        if (getLoadMoreViewCount() == 0) {
            return;
        }

        mLoading = false;
        mLoadMoreView.setmLoadMoreStatus(LoadMoreView.STATUS_FAIL);
        notifyItemChanged(getHeaderLayoutCount() + list.size() + getFooterLayoutCount());
    }

    public void loadMoreComplete() {
        if (getLoadMoreViewCount() == 0) {
            return;
        }

        mLoading = false;
        mLoadMoreView.setmLoadMoreStatus(LoadMoreView.STATUS_DEFAULT);
        notifyItemChanged(getHeaderLayoutCount() + list.size() + getFooterLayoutCount());
    }

    public void loadMoreEnd() {
        if (getLoadMoreViewCount() == 0) {
            return;
        }
        mLoading = false;
        mNextLoadEnable = false;
        if (mLoadMoreView.isLoadEndGone()) {
            notifyItemRemoved(getHeaderLayoutCount() + list.size() + getFooterLayoutCount());
        } else {
            mLoadMoreView.setmLoadMoreStatus(LoadMoreView.STATUS_END);
            notifyItemChanged(getHeaderLayoutCount() + list.size() + getFooterLayoutCount());
        }
    }

    public void setEnableLoadMore(boolean mLoadMoreEnable) {
        int oldLoadMoreCount = getLoadMoreViewCount();
        this.mLoadMoreEnable = mLoadMoreEnable;
        int newLoadMoreCount = getLoadMoreViewCount();
        if (oldLoadMoreCount == 1) {
            if (newLoadMoreCount == 0) {
                notifyItemRemoved(getHeaderLayoutCount() + list.size() + getFooterLayoutCount());
            }
        } else {
            if (newLoadMoreCount == 1) {
                mLoadMoreView.setmLoadMoreStatus(LoadMoreView.STATUS_DEFAULT);
                notifyItemInserted(getHeaderLayoutCount() + list.size() + getFooterLayoutCount());
            }
        }
    }

    public void addData(int position, T data) {
        if (0 <= position && position < list.size()) {
            list.add(position, data);
            notifyItemInserted(position + getHeaderLayoutCount());
        } else {
            throw new ArrayIndexOutOfBoundsException("inserted position most greater than 0 and less than data size");
        }
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    public void addDatas(List<T> data) {
        Log.e("jacky", "add:" + data.size());
        if (data != null && !data.isEmpty()) {
            this.list.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void setNewData(List<T> data) {
        this.list = data == null ? new ArrayList<T>() : data;
        if (onLoadMoreListener != null) {
            mNextLoadEnable = true;
            mLoadMoreEnable = true;
            mLoading = false;
            mLoadMoreView.setmLoadMoreStatus(LoadMoreView.STATUS_DEFAULT);
        }
        mLastPosition = -1;
        notifyDataSetChanged();
    }

    private int mAutoLoadMoreSize = 1;

    public void setAutoLoadMoreSize(int autoLoadMoreSize) {
        if (autoLoadMoreSize > 1) {
            mAutoLoadMoreSize = autoLoadMoreSize;
        }
    }

    private void autoLoadMore(int position) {
        if (getLoadMoreViewCount() == 0) {
            return;
        }
        if (position < getItemCount() - mAutoLoadMoreSize) {
            return;
        }
        if (mLoadMoreView.getmLoadMoreStatus() != LoadMoreView.STATUS_DEFAULT) {
            return;
        }
        mLoadMoreView.setmLoadMoreStatus(LoadMoreView.STATUS_LOADING);
        if (!mLoading) {
            mLoading = true;
            Log.e("jacky", "触发");
            onLoadMoreListener.onLoadMore();
        }
    }

    private int getLoadMoreViewCount() {
        if (onLoadMoreListener == null || !mLoadMoreEnable) {
            return 0;
        }

        if (!mNextLoadEnable && mLoadMoreView.isLoadEndGone()) {
            return 0;
        }

        if (list.isEmpty()) {
            return 0;
        }

        return 1;
    }

    public interface SpanSizeLookUp {
        int getSpanSize(GridLayoutManager gridLayoutManager, int position);
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void openLoadAnimation() {
        this.mOpenAnimationEnable = true;
    }


    public void openLoadAnimation(@AnimationType int animationType) {
        this.mOpenAnimationEnable = true;
        mCustomAnimation = null;
        switch (animationType) {
            case ALPHAIN:
                mSelectAnimation = new AlphaInAnimation();
                break;
            case SCALEIN:
                mSelectAnimation = new ScaleInAnimation();
                break;
            case SLIDEIN_BOTTOM:
                mSelectAnimation = new SlideInBottomAnimation();
                break;
            case SLIDEIN_LEFT:
                mSelectAnimation = new SlideInLeftAnimation();
                break;
            case SLIDEIN_RIGHT:
                mSelectAnimation = new SlideInRightAnimation();
                break;
            default:
                break;
        }
    }

    public static final int ALPHAIN = 0x00000001;
    public static final int SCALEIN = 0x00000002;
    public static final int SLIDEIN_BOTTOM = 0x00000003;
    public static final int SLIDEIN_LEFT = 0x00000004;
    public static final int SLIDEIN_RIGHT = 0x00000005;

    //@AnimationType
    private BaseAnimation mCustomAnimation;
    private BaseAnimation mSelectAnimation = new AlphaInAnimation();
    private boolean mOpenAnimationEnable = false;
    private Interpolator mInterpolator = new LinearInterpolator();
    private boolean mFirstOnlyEnable = true;
    private int mDuration = 300;

    public void isFirstOnly(boolean firstOnly) {
        this.mFirstOnlyEnable = firstOnly;
    }

    protected void startAnim(Animator anim, int index) {
        anim.setDuration(mDuration).start();
        anim.setInterpolator(mInterpolator);
    }

    @IntDef({ALPHAIN, SCALEIN, SLIDEIN_BOTTOM, SLIDEIN_LEFT, SLIDEIN_RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AnimationType {
    }


    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public int onCalModePos(int viewPos) {
        return viewPos - getHeaderLayoutCount();
    }
}
