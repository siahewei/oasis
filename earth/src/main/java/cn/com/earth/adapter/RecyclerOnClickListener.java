package cn.com.earth.adapter;

import android.os.Build;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;

import java.util.Iterator;
import java.util.Set;

import static cn.com.earth.adapter.IViewModelPosCalculator.LOADING_VIEW;


/**
 * 介绍: recyclerView onclick listener
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/11/29 下午5:26
 */

public abstract class RecyclerOnClickListener implements RecyclerView.OnItemTouchListener {
    private GestureDetectorCompat mGestureDetector;
    private RecyclerView recyclerView;
    private Set<Integer> childClickViewIds;
    private Set<Integer> longClickViewIds;
    protected AbsRecyclerAdapter baseQuickAdapter;
    public static String TAG = "SimpleClickListener";
    private boolean mIsPrepressed = false;
    private boolean mIsShowPress = false;
    private View mPressedView = null;

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

        if (recyclerView == null) {
            this.recyclerView = rv;
            this.baseQuickAdapter = (AbsRecyclerAdapter) recyclerView.getAdapter();
            mGestureDetector = new GestureDetectorCompat(recyclerView.getContext(), new ItemTouchHelperGestureListener(recyclerView));
        }
        if (!mGestureDetector.onTouchEvent(e) && e.getActionMasked() == MotionEvent.ACTION_UP && mIsShowPress) {
            if (mPressedView != null) {
                BaseViewHolder vh = (BaseViewHolder) recyclerView.getChildViewHolder(mPressedView);
                if (vh == null || vh.getItemViewType() != LOADING_VIEW) {
                    mPressedView.setPressed(false);
                }
                mPressedView = null;
            }
            mIsShowPress = false;
            mIsPrepressed = false;


        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {

        private RecyclerView recyclerView;

        @Override
        public boolean onDown(MotionEvent e) {
            mIsPrepressed = true;
            mPressedView = recyclerView.findChildViewUnder(e.getX(), e.getY());

            super.onDown(e);
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            if (mIsPrepressed && mPressedView != null) {
//                mPressedView.setPressed(true);
                mIsShowPress = true;
            }
            super.onShowPress(e);
        }

        public ItemTouchHelperGestureListener(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (mIsPrepressed && mPressedView != null) {

                final View pressedView = mPressedView;
                BaseViewHolder vh = (BaseViewHolder) recyclerView.getChildViewHolder(pressedView);

                if (isHeaderOrFooterPosition(vh.getLayoutPosition())) {
                    return false;
                }
                childClickViewIds = vh.getChildClickViewIds();

                if (childClickViewIds != null && childClickViewIds.size() > 0) {
                    for (Iterator it = childClickViewIds.iterator(); it.hasNext(); ) {
                        View childView = pressedView.findViewById((Integer) it.next());
                        if (inRangeOfView(childView, e) && childView.isEnabled()) {
                            setPressViewHotSpot(e, childView);
                            childView.setPressed(true);
                            onItemChildClick(baseQuickAdapter, childView, baseQuickAdapter.onCalModePos(vh.getLayoutPosition()));///*vh.getLayoutPosition() - baseQuickAdapter.getHeaderLayoutCount()*/);
                            resetPressedView(childView);
                            return true;
                        } else {
                            childView.setPressed(false);
                        }
                    }
                    setPressViewHotSpot(e, pressedView);
                    mPressedView.setPressed(true);
                    for (Iterator it = childClickViewIds.iterator(); it.hasNext(); ) {
                        View childView = pressedView.findViewById((Integer) it.next());
                        childView.setPressed(false);
                    }
                    onItemClick(baseQuickAdapter, pressedView, baseQuickAdapter.onCalModePos(vh.getLayoutPosition()));
                } else {
                    setPressViewHotSpot(e, pressedView);
                    mPressedView.setPressed(true);
                    for (Iterator it = childClickViewIds.iterator(); it.hasNext(); ) {
                        View childView = pressedView.findViewById((Integer) it.next());
                        childView.setPressed(false);
                    }
                    onItemClick(baseQuickAdapter, pressedView, baseQuickAdapter.onCalModePos(vh.getLayoutPosition()));
                }
                resetPressedView(pressedView);

            }
            return true;
        }

        private void resetPressedView(final View pressedView) {
            if (pressedView != null) {
                pressedView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (pressedView != null) {
                            pressedView.setPressed(false);
                        }

                    }
                }, 100);
            }

            mIsPrepressed = false;
            mPressedView = null;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            boolean isChildLongClick = false;
            if (mIsPrepressed && mPressedView != null) {
                mPressedView.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                BaseViewHolder vh = (BaseViewHolder) recyclerView.getChildViewHolder(mPressedView);
                if (!isHeaderOrFooterPosition(vh.getLayoutPosition())) {
                    longClickViewIds = vh.getChildLongClickViewIds();
                    if (longClickViewIds != null && longClickViewIds.size() > 0) {
                        for (Iterator it = longClickViewIds.iterator(); it.hasNext(); ) {
                            View childView = mPressedView.findViewById((Integer) it.next());
                            if (inRangeOfView(childView, e) && childView.isEnabled()) {
                                setPressViewHotSpot(e, childView);
                                onItemChildLongClick(baseQuickAdapter, childView, baseQuickAdapter.onCalModePos(vh.getLayoutPosition()));
                                childView.setPressed(true);
                                mIsShowPress = true;
                                isChildLongClick = true;
                                break;
                            }
                        }
                    }
                    if (!isChildLongClick) {
                        onItemLongClick(baseQuickAdapter, mPressedView, baseQuickAdapter.onCalModePos(vh.getLayoutPosition()));
                        setPressViewHotSpot(e, mPressedView);
                        mPressedView.setPressed(true);
                        for (Iterator it = longClickViewIds.iterator(); it.hasNext(); ) {
                            View childView = mPressedView.findViewById((Integer) it.next());
                            childView.setPressed(false);
                        }
                        mIsShowPress = true;
                    }

                }

            }
        }


    }

    private void setPressViewHotSpot(final MotionEvent e, final View mPressedView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            /**
             * when   click   Outside the region  ,mPressedView is null
             */
            if (mPressedView != null && mPressedView.getBackground() != null) {
                mPressedView.getBackground().setHotspot(e.getRawX(), e.getY() - mPressedView.getY());
            }
        }
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     *
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     */
    public abstract void onItemClick(AbsRecyclerAdapter adapter, View view, int position);

    /**
     * callback method to be invoked when an item in this view has been
     * click and held
     *
     * @param view     The view whihin the AbsListView that was clicked
     * @param position The position of the view int the adapter
     * @return true if the callback consumed the long click ,false otherwise
     */
    public abstract void onItemLongClick(AbsRecyclerAdapter adapter, View view, int position);

    public abstract void onItemChildClick(AbsRecyclerAdapter adapter, View view, int position);

    public abstract void onItemChildLongClick(AbsRecyclerAdapter adapter, View view, int position);

    public boolean inRangeOfView(View view, MotionEvent ev) {
        int[] location = new int[2];
        if (view.getVisibility() != View.VISIBLE) {
            return false;
        }
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        if (ev.getRawX() < x
                || ev.getRawX() > (x + view.getWidth())
                || ev.getRawY() < y
                || ev.getRawY() > (y + view.getHeight())) {
            return false;
        }
        return true;
    }

    private boolean isHeaderOrFooterPosition(int position) {
        /**
         *  have a headview and EMPTY_VIEW FOOTER_VIEW LOADING_VIEW
         */
        if (baseQuickAdapter == null) {
            if (recyclerView != null) {
                baseQuickAdapter = (AbsRecyclerAdapter) recyclerView.getAdapter();
            } else {
                return false;
            }
        }
        int type = baseQuickAdapter.getItemViewType(position);
        return (type == IViewModelPosCalculator.EMPTY_VIEW || type == IViewModelPosCalculator.HEADER_VIEW || type == IViewModelPosCalculator.FOOTER_VIEW || type == IViewModelPosCalculator.LOADING_VIEW);
    }
}
