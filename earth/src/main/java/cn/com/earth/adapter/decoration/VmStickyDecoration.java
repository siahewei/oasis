package cn.com.earth.adapter.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/12/14 下午11:36
 */

public class VmStickyDecoration extends RecyclerView.ItemDecoration {
    protected IStickyHeaderDecoration mHeaderHandler = null;
    private DimensCalculator dimensCalculator;
    private final Rect mTempRect = new Rect();
    private IStickyHeaderProvider stickyHeaderProvider;
    SparseArray<Rect> mHeaderRects = new SparseArray<>();

    public VmStickyDecoration(IStickyHeaderDecoration mHeaderHandler) {
        this.mHeaderHandler = mHeaderHandler;
        dimensCalculator = new DimensCalculator();
        stickyHeaderProvider = new VimStickyHeaderViewCache();
    }


    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);


        RecyclerView.Adapter adapter = parent.getAdapter();
        if (adapter == null) {
            return;
        }

        int childCount = parent.getChildCount();
        if (childCount == 0) {
            return;
        }

        int lastShowHeaderPostion = -1;

        for (int i = 0; i < childCount; i++) {
            View itemView = parent.getChildAt(i);

            int position = parent.getChildAdapterPosition(itemView);

            if (position == RecyclerView.NO_POSITION) {
                continue;
            }
            boolean hasStickyHeader = hasStickyHeader(itemView, LinearLayout.VERTICAL, position, i);
            boolean hasNewHeader = hasNewHeader(parent, position, false);

            if (hasStickyHeader) {
                lastShowHeaderPostion = position;
            }

            if (hasStickyHeader || hasNewHeader) {
                // 2中情况, (1)当前要显示(顶部的 规则(top margin) (2) 与上一条数据临界或者即将显示, 上一条要显示,且上一条数据处于离开页面状态)
                Log.i("jacky111", "position:" + position);

                int type = mHeaderHandler.getHeaderViewTag(position, parent);
                View headerView = null;

                if (hasNewHeader && (position - lastShowHeaderPostion) == 1) {
                    // (1) 新的要显示header,且上一条离开页面
                    headerView = stickyHeaderProvider.getHeader(mHeaderHandler, parent, position, type);
                    Rect tmpRect = mHeaderRects.get(type);
                    if (tmpRect == null) {
                        tmpRect = new Rect();
                    }
                    calculateHeaderRect(tmpRect, parent, headerView, itemView, hasStickyHeader, position);
                    mHeaderRects.put(type, tmpRect);

                    int preType = mHeaderHandler.getHeaderViewTag(position - 1, parent);
                    Rect preRect = mHeaderRects.get(preType);
                    if (tmpRect.top - preRect.bottom <= 0) {
                        //计算上一条数据显示的位置
                        Rect rect = new Rect(preRect);
                        rect.top += tmpRect.top - preRect.bottom;
                        rect.bottom += tmpRect.top - preRect.bottom;

                        // 找到上一条header的view, 并且绘制
                        View preHeaderView = stickyHeaderProvider.getHeader(mHeaderHandler, parent, position - 1, preType);
                        if (preHeaderView != null) {
                            //Log.i("jacky111", "draw position:" + (position - 1) + ", type" + type);
                            drawHeader(parent, c, preHeaderView, rect);
                        }
                        //Log.i("jacky111", "draw position:" + position + ", type:" + type);
                        // 绘制本次的header的view
                        drawHeader(parent, c, headerView, tmpRect);

                    }
                } else if (hasStickyHeader) {
                    headerView = stickyHeaderProvider.getHeader(mHeaderHandler, parent, position, type);
                    if (headerView != null) {
                        Rect rect = mHeaderRects.get(type);
                        if (rect == null) {
                            rect = new Rect(0, 0, 0, 0);
                        }
                        calculateHeaderRect(rect, parent, headerView, itemView, hasStickyHeader, position);
                        drawHeader(parent, c, headerView, rect);
                        mHeaderRects.put(type, rect);
                    }
                }

            }
        }
    }


    public boolean hasNewHeader(RecyclerView parent, int position, boolean isReverseLayout) {
        // 只有当前已经有了viewHeader,这种判断才有意义

        if (!mHeaderHandler.hasStickHeader(position)) {
            return false;
        }

        int headerId = mHeaderHandler.getHeaderViewTag(position, parent);
        RecyclerView.Adapter adapter = parent.getAdapter();
        long nextItemHeaderId = -1;
        int nextItemPosition = position + (isReverseLayout ? 1 : -1);
        if (!indexOutOfBounds(adapter, nextItemPosition)) {
            nextItemHeaderId = mHeaderHandler.getHeaderViewTag(nextItemPosition, parent);
        }
        int firstItemPosition = isReverseLayout ? adapter.getItemCount() - 1 : 0;

        return position == firstItemPosition || headerId != nextItemHeaderId;
    }

    private boolean indexOutOfBounds(RecyclerView.Adapter adapter, int position) {
        return position < 0 || position >= adapter.getItemCount();
    }

    private void calculateHeaderRect(Rect rect, RecyclerView parent, View headerView, View itemView, boolean hasStickyHeader, int postion) {

        ViewGroup.LayoutParams layoutParam = itemView.getLayoutParams();

        int leftMarigin = 0;
        int topMargin = 0;

        if (layoutParam instanceof ViewGroup.MarginLayoutParams) {
            leftMarigin = ((ViewGroup.MarginLayoutParams) layoutParam).leftMargin;
            topMargin = ((ViewGroup.MarginLayoutParams) layoutParam).topMargin;
        }

        // 暂时只考虑垂直情况
        int tx = 0;
        if (mHeaderHandler.isFullSpan(postion)) {
            dimensCalculator.initMargins(rect, headerView);
            tx = itemView.getLeft() - leftMarigin + rect.left;
        } else {
            tx = rect.left;
        }

        int ty = getRecylerViewTop(parent) + topMargin;

        if (!hasStickyHeader) {
            ty = itemView.getTop();
        }

        int headerHeight = headerView.getHeight();
        rect.set(tx, ty, tx + headerView.getWidth(), ty + headerHeight);
    }


    private int getRecylerViewTop(RecyclerView view) {
        if (view.getLayoutManager().getClipToPadding()) {
            return view.getPaddingTop();
        } else {
            return 0;
        }
    }


    /**
     * ------------------------------------------------------------------------------------------------------------
     */
    class DimensCalculator {
        public void initMargins(Rect margins, View view) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                initMarginRect(margins, marginLayoutParams);
            } else {
                margins.set(0, 0, 0, 0);
            }
        }


        private void initMarginRect(Rect marginRect, ViewGroup.MarginLayoutParams marginLayoutParams) {
            marginRect.set(
                    marginLayoutParams.leftMargin,
                    marginLayoutParams.topMargin,
                    marginLayoutParams.rightMargin,
                    marginLayoutParams.bottomMargin
            );
        }

    }


    /**
     * 判断itemView 是否位于顶部
     *
     * @param itemView
     * @param orientation
     * @param position
     * @return
     */
    public boolean hasStickyHeader(View itemView, int orientation, int position, int i) {
        int offset, margin;
        dimensCalculator.initMargins(mTempRect, itemView);
        if (orientation == LinearLayout.VERTICAL) {
            offset = itemView.getTop();
            margin = mTempRect.top;
        } else {
            offset = itemView.getLeft();
            margin = mTempRect.left;
        }

        // itemview 到顶部的距离小于 margin
        // 且在在改position的位置要显示view
        return offset <= margin && mHeaderHandler.hasStickHeader(position);
    }


    /**
     * 绘制 header
     *
     * @param recyclerView
     * @param canvas
     * @param header
     * @param offset
     */
    private void drawHeader(RecyclerView recyclerView, Canvas canvas, View header, Rect offset) {
        canvas.save();
        if (recyclerView.getLayoutManager().getClipToPadding()) {
            initClipRectForHeader(mTempRect, recyclerView, header);
            canvas.clipRect(mTempRect);
        }

        canvas.translate(offset.left, offset.top);
        header.draw(canvas);
        canvas.restore();
    }


    protected void initClipRectForHeader(Rect clipRect, RecyclerView recyclerView, View header) {
        dimensCalculator.initMargins(clipRect, header);
        //默认垂直
        clipRect.set(
                recyclerView.getPaddingLeft(),
                recyclerView.getPaddingTop(),
                recyclerView.getWidth() - recyclerView.getPaddingRight() - clipRect.right,
                recyclerView.getHeight() - recyclerView.getPaddingBottom());
    }

}
