package cn.com.oasis.base.adapter.demo.vmdemo;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.com.earth.adapter.BaseViewHolder;
import cn.com.earth.vm.AbsViewMode;
import cn.com.oasis.R;
import cn.com.oasis.base.adapter.demo.Task;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/11/30 下午11:35
 */

public class VmTaskMode extends AbsViewMode<Task, BaseViewHolder<Task>> {
    @Override
    protected int getDataItemType(int dataPos) {
        return list.get(dataPos).getType() + startViewType;
    }

    @Override
    protected void bindViewHolder(BaseViewHolder<Task> hoder, int dataPos) {
        if (list.get(dataPos).getType() == 0) {
            hoder.setText(R.id.text, list.get(dataPos).getData());
        } else {
            hoder.setText(R.id.footer, list.get(dataPos).getData());
        }
    }

    @Override
    protected BaseViewHolder<Task> onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == 0) {
            return new BaseViewHolder<>(inflater.inflate(R.layout.item, parent, false));
        } else {
            return new BaseViewHolder<>(inflater.inflate(R.layout.footer_item, parent, false));

        }
    }

    @Override
    protected int getSpan(int dataPos) {
        int type = list.get(dataPos).getType();
        switch (type) {
            case 0:
                return fullSpan;
            case 1:
                return fullSpan > 2 ? 2 : 1;
        }

        return fullSpan > 3 ? 3 : 1;
    }

    @Override
    protected int getLayoutId(int viewTypeInVieMode) {
        if (viewTypeInVieMode == 0) {
            return R.layout.item;
        } else {
            return R.layout.footer_item;
        }
    }

    @Override
    public View getHeaderView(ViewGroup parent, int headerViewTag, int viewPos) {
        if (headerViewTag == 0) {
            BaseViewHolder viewHolder = onCreateViewHolder(parent, headerViewTag);
            return viewHolder.itemView;
        }else {
            return null;
        }
    }

    @Override
    public boolean hasStickHeader() {
       /* if (list.isEmpty()){
            return false;
        }*/
        return true;
    }

    public boolean isHeaderPosition(int dataPos){
        return dataPos == 0;
    }
}
