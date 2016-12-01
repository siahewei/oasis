package cn.com.oasis.demo;

import com.google.gson.annotations.SerializedName;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/10/8 下午5:07
 */

public class User {
    @SerializedName("name")
    String name;
    @SerializedName("addr")
    String addr;
    @SerializedName("content")
    String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", addr='" + addr + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
