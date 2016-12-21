package cn.com.earth.adapter.decoration;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/12/15 下午3:09
 */

public interface IStickyHeaderDecoration {


    /**
     * 判断当前位置的item是否需要一个显示stickyheader
     *
     * @param position adapter中的item位置
     * @return
     */
    boolean hasStickHeader(int position);


    /**
     * 获取指定位置需要显示的headerView的标志,该标志用于缓存唯一的一个header类型的view.
     * 不同的headerView应该使用不同的tag,否则会被替换
     *
     * @param position adapter中的item位置
     * @param parent
     * @return
     */
    int getHeaderViewTag(int position, RecyclerView parent);

    /**
     * 根据header标志或者position获取需要的headerView
     *
     * @param position      adapter中的item位置,当前需要显示headerView的位置
     * @param headerViewTag headerView的标志
     * @param parent
     * @return
     */
    View getHeaderView(int position, int headerViewTag, RecyclerView parent);


    /**
     * 当前位置是否是全格
     *
     * @param position
     * @return
     */
    boolean isFullSpan(int position);
}
