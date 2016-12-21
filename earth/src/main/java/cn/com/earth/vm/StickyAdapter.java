package cn.com.earth.vm;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import cn.com.earth.adapter.decoration.IStickyHeaderDecoration;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/12/2 上午11:40
 */

public class StickyAdapter extends VmRecyclerAdapter implements IStickyHeaderDecoration {

    public StickyAdapter(AbsViewMode... viewModes) {
        super(viewModes);
    }


    @Override
    public boolean hasStickHeader(int position) {

        int[] dataPos = new int[1];
        AbsViewMode viewMode = adapterHelper.getViewModeByPos(position, dataPos);
        return viewMode.hasStickyHeader(dataPos[0]);
    }


    @Override
    public int getHeaderViewTag(int position, RecyclerView parent) {
        int[] dataPos = new int[1];
        AbsViewMode viewMode = adapterHelper.getViewModeByPos(position, dataPos);
        return viewMode.getHeaderTag(dataPos[0]);
    }

    @Override
    public View getHeaderView(int position, int headerViewTag, RecyclerView parent) {
        int[] dataPos = new int[2];
        AbsViewMode viewMode = adapterHelper.getViewModeByPos(position, dataPos);
        int viewTypeInViewMode = adapterHelper.getViewTypeInViewMode(headerViewTag);
        // 找到所属的vm 的第一个元素所在的view中的位置

        return viewMode.getHeaderView(parent, viewTypeInViewMode, dataPos[0], dataPos[1]);
    }

    @Override
    public boolean isFullSpan(int position) {
        int[] dataPos = new int[1];
        AbsViewMode viewMode = adapterHelper.getViewModeByPos(position, dataPos);
        return viewMode.isFullSpan(dataPos[0]);
    }
}
