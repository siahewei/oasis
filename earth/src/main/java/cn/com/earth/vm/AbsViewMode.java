package cn.com.earth.vm;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.com.earth.adapter.BaseViewHolder;

/**
 * 介绍: ViewMode abstract class
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/11/30 下午9:28
 */

public abstract class AbsViewMode<T, VH extends BaseViewHolder<T>> implements IVmDataSet<T> {
    /**
     * divider of viewType
     */
    protected int startViewType;

    /**
     * data set
     */
    protected List<T> list;

    /**
     * for grid manager
     */
    protected int fullSpan = 1;

    /**
     * daset observer
     */
    protected IVmObserver observer;

    public AbsViewMode() {
        list = new ArrayList<T>();
    }

    public void setListener(IVmObserver observer) {
        this.observer = observer;
    }

    public void setFullSpan(int fullSpan) {
        this.fullSpan = fullSpan;
    }

    public List<T> getList() {
        return list;
    }

    protected int getDataItemCount() {
        return list.size();
    }

    protected abstract int getDataItemType(int dataPos);

    protected abstract void bindViewHolder(VH hoder, int dataPos);

    protected int getSpan(int dataPos) {
        return fullSpan;
    }


    public void setStartViewType(int startViewType) {
        this.startViewType = startViewType;
    }


    @Override
    public void clear() {
        list.clear();
        observer.notifyDataChanged();
    }

    @Override
    public void add(T t) {
        list.add(t);
        observer.notifyDataChanged();
    }

    @Override
    public void add(int location, T t) {
        list.add(location, t);
        observer.notifyDataChanged();
    }

    @Override
    public void addAll(Collection<T> ts) {
        list.addAll(ts);
        observer.notifyDataChanged();
    }

    @Override
    public void addAll(int location, List<T> ts) {
        list.addAll(location, ts);
        observer.notifyDataChanged();
    }

    @Override
    public void remove(int location) {
        list.remove(location);
        observer.notifyDataChanged();
    }

    @Override
    public void remove(T t) {
        list.remove(t);
        observer.notifyDataChanged();
    }

    @Override
    public Collection<T> getDataSet() {
        return list;
    }

    @Override
    public int getDataSize() {
        return list.size();
    }

    protected View getItemView(@LayoutRes int layoutResId, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return layoutInflater.inflate(layoutResId, parent, false);
    }

    protected abstract @LayoutRes int getLayoutId(int viewTypeInVieMode);

    protected BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewTypeInVieMode) {
        View view = getItemView(getLayoutId(viewTypeInVieMode), parent);
        return new BaseViewHolder<>(view);
    }
}
