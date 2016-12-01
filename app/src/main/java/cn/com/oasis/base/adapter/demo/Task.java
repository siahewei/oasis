package cn.com.oasis.base.adapter.demo;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/11/30 上午1:41
 */

public class Task {
    public String data;
    public int type;

    public Task(String data, int type) {
        this.data = data;
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public int getType() {
        return type;
    }
}
