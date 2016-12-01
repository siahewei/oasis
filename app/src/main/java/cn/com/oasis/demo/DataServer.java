package cn.com.oasis.demo;

import java.util.ArrayList;
import java.util.List;

import cn.com.oasis.base.adapter.demo.Task;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/11/28 下午11:57
 */

public class DataServer {
    public static List<Entity> getList(String suffix, int i, boolean filter) {
        List<Entity> data = new ArrayList<>();
        for (int t = 0; t < i; t++) {
            if (filter) {
                if (t < 4) {
                    data.add(new Entity(suffix + t, 0));
                } else {
                    data.add(new Entity(suffix + t, 1));
                }
            } else {
                data.add(new Entity(suffix + t, t));
            }

        }
        return data;
    }

    public static List<Task> getTask(String suffix, int i, boolean filter) {
        List<Task> data = new ArrayList<>();
        for (int t = 0; t < i; t++) {
            if (filter) {
                if (t <10) {
                    data.add(new Task(suffix + t, 0));
                } else {
                    data.add(new Task(suffix + t, 1));
                }
            } else {
                data.add(new Task(suffix + t, t));
            }

        }
        return data;
    }
}
