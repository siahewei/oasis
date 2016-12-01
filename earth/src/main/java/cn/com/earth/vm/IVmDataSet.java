package cn.com.earth.vm;

import java.util.Collection;
import java.util.List;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/11/29 下午11:49
 */

public interface IVmDataSet<T> {
    void clear();

    void add(T t);

    void add(int location, T t);

    void addAll(Collection<T> ts);

    void addAll(int location, List<T> ts);

    void remove(int location);

    void remove(T t);

    Collection<T> getDataSet();

    int getDataSize();

}