package cn.com.oasis.demo;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import cn.com.earth.adapter.BaseRecyclerAdapter;
import cn.com.earth.adapter.BaseViewHolder;
import cn.com.oasis.R;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/11/28 下午11:12
 */

public class TestAdapter extends BaseRecyclerAdapter<Entity, BaseViewHolder<Entity>> {

    public TestAdapter(List<Entity> data) {
        super(data);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new BaseViewHolder(inflater.inflate(R.layout.item, parent, false));
    }

    @Override
    public void convert(BaseViewHolder<Entity> holder, int ViewPos, int dataPos) {
        holder.setText(R.id.text, list.get(dataPos).getData());

    }
}
