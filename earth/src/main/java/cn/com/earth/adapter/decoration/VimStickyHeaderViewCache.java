package cn.com.earth.adapter.decoration;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/12/15 下午2:36
 */

public class VimStickyHeaderViewCache implements IStickyHeaderProvider {
    private SparseArray<View> headerViews = new SparseArray<>();

    @Override
    public View getHeader(IStickyHeaderDecoration iStickyHeaderDecoration, RecyclerView parent, int position, int viewType) {
        View header = headerViews.get(viewType);

        if (header != null) {
            return header;
        }

        header = iStickyHeaderDecoration.getHeaderView(position, viewType, parent);

        if (header != null) {
            headerViews.put(viewType, header);
            return header;
        }


        RecyclerView.Adapter adapter = parent.getAdapter();
        if (header == null) {
            RecyclerView.ViewHolder viewHolder = adapter.createViewHolder(parent, viewType);
            adapter.bindViewHolder(viewHolder, position);
            header = viewHolder.itemView;


            if (header.getLayoutParams() == null) {
                header.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }

            int widthSpec, heightSpec;
            //TODO: 只考虑垂直情况
            // measure
            widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY);
            heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(), View.MeasureSpec.UNSPECIFIED);

            int childWidth = ViewGroup.getChildMeasureSpec(widthSpec,
                    parent.getPaddingLeft() + parent.getPaddingRight(), header.getLayoutParams().width);
            int childHeight = ViewGroup.getChildMeasureSpec(heightSpec,
                    parent.getPaddingTop() + parent.getPaddingBottom(), header.getLayoutParams().height);
            header.layout(0, 0, header.getMeasuredWidth(), header.getMeasuredHeight());
            header.measure(childWidth, childHeight);
            headerViews.put(viewType, header);
        }
        return header;
    }

    @Override
    public void invalidate() {
        headerViews.clear();
    }
}
