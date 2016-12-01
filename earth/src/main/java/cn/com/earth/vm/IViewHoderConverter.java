package cn.com.earth.vm;

import cn.com.earth.adapter.BaseViewHolder;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/11/29 上午10:34
 */

public interface IViewHoderConverter<K extends BaseViewHolder> {
    void convert(K holder, int ViewPos, int dataPos);
}
