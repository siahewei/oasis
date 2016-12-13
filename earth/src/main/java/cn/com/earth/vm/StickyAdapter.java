package cn.com.earth.vm;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import cn.com.earth.adapter.decoration.StickyDecoration;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/12/2 上午11:40
 */

public class StickyAdapter extends VmRecyclerAdapter implements StickyDecoration.IStickerHeaderDecoration {

    public StickyAdapter(AbsViewMode... viewModes) {
        super(viewModes);
    }

    @Override
    public boolean isHeaderPosition(int position) {
        int[] dataPos = new int[1];
        AbsViewMode viewMode = adapterHelper.getViewModeByPos(position, dataPos);
        return viewMode.isHeaderPosition(dataPos[0]);
    }

    @Override
    public boolean hasStickHeader(int position) {
        if (adapterHelper.getItemCount() == 0) {
            return false;
        }
        AbsViewMode viewMode = adapterHelper.getViewModeByPos(position);
        return viewMode.hasStickHeader();
    }

    @Override
    public int getHeaderViewTag(int position, RecyclerView parent) {
        int viewType = getItemViewType(position);
        return viewType;
    }

    @Override
    public View getHeaderView(int position, int headerViewTag, RecyclerView parent) {
        int[] dataPos = new int[1];
        AbsViewMode viewMode = adapterHelper.getViewModeByPos(position, dataPos);
        int viewTypeInViewMode = adapterHelper.getViewTypeInViewMode(headerViewTag);
        return viewMode.getHeaderView(parent, viewTypeInViewMode, headerViewTag);
    }

    @Override
    public void setHeaderView(int position, int headerViewTag, RecyclerView parent, View headerView) {
        AbsViewMode viewMode = adapterHelper.getViewModeByPos(position);
        viewMode.setHeaderView(headerViewTag, parent, headerView);
    }

    @Override
    public boolean isBeenDecorated(int lastDecoratedPosition, int nowDecoratingPosition) {
        AbsViewMode oldVm = adapterHelper.getViewModeByPos(lastDecoratedPosition);
        AbsViewMode newVm = adapterHelper.getViewModeByPos(nowDecoratingPosition);

        if (oldVm.getClass().getName().equals(newVm.getClass().getName())) {
            return true;
        } else {
            return false;
        }
    }
}
