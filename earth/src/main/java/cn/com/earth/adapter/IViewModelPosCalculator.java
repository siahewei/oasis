package cn.com.earth.adapter;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/11/29 下午5:35
 */

public interface IViewModelPosCalculator {
    int HEADER_VIEW = 0x00000111;
    int LOADING_VIEW = 0x00000222;
    int FOOTER_VIEW = 0x00000333;
    int EMPTY_VIEW = 0x00000555;
    int onCalModePos(int ViewPos);
}
