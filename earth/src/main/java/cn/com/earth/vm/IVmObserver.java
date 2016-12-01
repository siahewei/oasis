package cn.com.earth.vm;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/11/30 下午11:05
 */

public interface IVmObserver {

    void notifyItemChanged(int pos);
    void notifyItemInserted(int pos);
    void notifyItemRemoved(int pos);
    void notifyItemRangeChanged(int startPos, int endPos);
    void notifyItemRangeInserted(int startPos, int endPos);
    void notifyItemRangeRemoved(int statPos, int endPos);
    void notifyDataChanged();
}
