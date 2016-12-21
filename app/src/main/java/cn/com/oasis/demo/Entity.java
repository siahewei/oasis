package cn.com.oasis.demo;

import java.util.Comparator;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/11/28 下午11:14
 */

public class Entity implements Comparator<Entity>{
    public String data;
    public int type;

    public Entity(String data, int type) {
        this.data = data+ "dadkhakdhakdhkahdkahk";
        this.type = type;
    }

    @Override
    public int compare(Entity o1, Entity o2) {
        if (o1.getType()> o2.getType()) return 1;
        else if (o1.getType() < o2.getType()) return -1;
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entity entity = (Entity) o;

        return data.equals(entity.data);

    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }

    public String getData() {
        return data;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "data='" + data + '\'' +
                ", type=" + type +
                '}';
    }
}
