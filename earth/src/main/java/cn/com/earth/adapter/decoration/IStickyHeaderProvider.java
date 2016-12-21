package cn.com.earth.adapter.decoration;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/12/15 下午2:37
 */

public interface IStickyHeaderProvider {
    View getHeader(IStickyHeaderDecoration iStickyHeaderDecoration, RecyclerView recyclerView, int position, int viewType);
    void invalidate();
}
