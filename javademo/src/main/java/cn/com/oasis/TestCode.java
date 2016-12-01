package cn.com.oasis;

import java.util.ArrayList;
import java.util.List;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/10/12 下午6:36
 */

public class TestCode {
    public static final int SPAN = 5; // 跨度5做合并

    public static List<Long> getAllListVersion() {
        ArrayList<Long> arrayList = new ArrayList<>();
        arrayList.add(1550324L);
        arrayList.add(1550325L);
        arrayList.add(1550326L);
        arrayList.add(1550329L);
        arrayList.add(1550330L);
        arrayList.add(1550332L);
        arrayList.add(1550335L);
        arrayList.add(1550336L);

        arrayList.add(1550337L);
        arrayList.add(1550338L);
        arrayList.add(1550339L);
        arrayList.add(1550341L);
        arrayList.add(1550343L);
        arrayList.add(1550344L);
        arrayList.add(1550345L);
        arrayList.add(1550346L);

        arrayList.add(1550347L);
        arrayList.add(1550348L);
        arrayList.add(1550349L);
        arrayList.add(1550351L);
        arrayList.add(1550352L);
        arrayList.add(1550353L);
        arrayList.add(1550355L);
        arrayList.add(1550357L);

        arrayList.add(1550362L);
        arrayList.add(1550370L);
        arrayList.add(1550373L);
        arrayList.add(1550374L);

        return arrayList;
    }


    public static boolean preProc() {
        /**
         * [[1, 3], [3, 7], [7, 9]]类似这种我需不需要做一下跨度合并，比如设置跨度为2， 合并为[1,9] ,这样从3次接口到请求一次接口。虽然会请求冗余数据，但是前端的处理了
         */
        List<Long> sortVersionList = getAllListVersion();
        if (sortVersionList == null || sortVersionList.size() <= 1) return false;

        int size = sortVersionList.size();
        Long pre = sortVersionList.get(0);
        List<ArrayList<Long>> tmpList = new ArrayList<ArrayList<Long>>();

        List<Long> longList = new ArrayList<Long>();
        //longList.add(pre);

        //
        List<ArrayList<Long>> vacancyList = new ArrayList<ArrayList<Long>>();
        for (int i = 1; i < size; i++) {
            if (sortVersionList.get(i) - pre > 1) {
                longList.add(pre);
                longList.add(sortVersionList.get(i));
                ArrayList<Long> tmo = new ArrayList<>(2);
                tmo.add(pre);
                tmo.add(sortVersionList.get(i));
            }
            pre = sortVersionList.get(i);
        }

        if (longList.size() <= 1) {
            return false;
        }

        Long start = -1L;


        System.out.println("hewei_fill____s:" + testPrint(sortVersionList));

        System.out.println("hewei_fill____s:" + testPrint(longList));

        pre = longList.get(0);
        int pointSize = longList.size();
        for (int i = 1; i < longList.size(); i++) {
            if (longList.get(i) - pre > SPAN && i != pointSize - 1) {
                ArrayList<Long> leak = new ArrayList<Long>();
                leak.add(pre);
                leak.add(sortVersionList.get(i));
                tmpList.add(leak);
                pre = longList.get(i);
            } else if (i == pointSize - 1) {
                ArrayList<Long> leak = new ArrayList<Long>();
                leak.add(pre);
                leak.add(longList.get(i));
                tmpList.add(leak);
            }
        }

        //this.taskList = tmpList;
        ///this.dolist = sortVersionList;

        for (ArrayList<Long> item : tmpList) {
            System.out.println("hewei_fill____t:" + testPrint(item));
        }

        return true;
    }

    public static String testPrint(List<Long> list) {
        StringBuilder builder = new StringBuilder();
        for (Long item : list) {
            builder.append(String.valueOf(item) + ",");
        }

        return builder.toString();
    }

    public static void main(String[] args) {
        preProc();
    }

}
