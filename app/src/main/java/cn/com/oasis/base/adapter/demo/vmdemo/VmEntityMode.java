package cn.com.oasis.base.adapter.demo.vmdemo;


import android.support.v7.widget.RecyclerView;
import android.view.View;

import cn.com.earth.adapter.BaseViewHolder;
import cn.com.earth.vm.AbsViewMode;
import cn.com.oasis.R;
import cn.com.oasis.demo.Entity;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/11/30 下午11:35
 */

public class VmEntityMode extends AbsViewMode<Entity, BaseViewHolder<Entity>> {
    @Override
    protected int getDataItemType(int dataPos) {
        return list.get(dataPos).getType() + startViewType;
    }

    @Override
    protected void bindViewHolder(BaseViewHolder<Entity> hoder, int dataPos) {
        hoder.setText(R.id.text, list.get(dataPos).getData());
        if (list.get(dataPos).getType() == 0) {
            hoder.setText(R.id.text, list.get(dataPos).getData());
        } else {
            hoder.setText(R.id.footer, list.get(dataPos).getData());
        }
    }

    @Override
    protected int getSpan(int dataPos) {
        if (dataPos >= list.size()) {
            return super.getSpan(dataPos);
        }
        int type = list.get(dataPos).getType();
        switch (type) {
            case 0:
                return fullSpan >= 4 ? 4 : 1;
            case 2:
                return fullSpan;
            case 1:
                return 1;
        }

        return fullSpan > 2 ? 2 : 1;
    }

    @Override
    protected int getLayoutId(int viewTypeInVieMode) {
        if (viewTypeInVieMode == 0) {
            return R.layout.item_header;
        }
        if (viewTypeInVieMode == 5) {
            return R.layout.item_header1;
        } else {
            return R.layout.footer_item;
        }
    }


    @Override
    public View getHeaderView(RecyclerView parent, int headerViewTag, int viewPos, int viewTypeStarIntViewPos) {
        RecyclerView.ViewHolder holder = parent.findViewHolderForAdapterPosition(headerViewTag);
        if (holder != null) {
            return holder.itemView;
        }

        return null;
    }

    @Override
    public int getHeaderTag(int dataPos) {
        if (dataPos < 2) {
            return startViewType + 0;
        } else {
            return startViewType + 2;
        }
    }


    @Override
    public boolean isFullSpan(int dataPos) {
        return list.get(dataPos).getType() == 0 || list.get(dataPos).getType() == 2;
    }

    @Override
    public boolean hasStickyHeader(int dataPos) {
        return true;
    }
}
