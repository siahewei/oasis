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
    @Override
    public boolean isHeaderPosition(int position) {
        int[] viewpos = new int[1];
        AbsViewMode viewMode = adapterHelper.getViewModeByPos(position, viewpos);
        return false;
    }

    @Override
    public boolean hasStickHeader(int position) {
        AbsViewMode viewMode = adapterHelper.getViewModeByPos(position);
        return false;
    }

    @Override
    public int getHeaderViewTag(int position, RecyclerView parent) {
        int viewType = getItemViewType(position);
        return viewType;
    }

    @Override
    public View getHeaderView(int position, int headerViewTag, RecyclerView parent) {
        return null;
    }

    @Override
    public void setHeaderView(int position, int headerViewTag, RecyclerView parent, View headerView) {

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
