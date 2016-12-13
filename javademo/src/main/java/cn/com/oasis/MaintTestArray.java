package cn.com.oasis;

import java.util.ArrayList;
import java.util.List;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/10/15 下午12:06
 */

public class MaintTestArray {
    public static void main(String[] args) {
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            datas.add(i + "");
        }

        int totalSize = datas.size();
        int LIMIT_COLUM = 17;
        for (int i = 0; i <totalSize / LIMIT_COLUM; i++) {
            List<String> subList = new ArrayList<String>(datas.subList(i * LIMIT_COLUM, (i + 1) * LIMIT_COLUM));
            System.out.println("----------b-------------" + i);
            printList(subList);
            System.out.println("-----------e------------" + i);
        }
        if (totalSize % LIMIT_COLUM != 0) {
            System.out.println("----------b-------------" + (totalSize / LIMIT_COLUM + 1));
            List<String> subList = new ArrayList<String>(datas.subList(totalSize / LIMIT_COLUM * LIMIT_COLUM, totalSize));
            printList(subList);
            System.out.println("----------e-------------" + (totalSize / LIMIT_COLUM + 1));
        }





    }

    static void printList(List<String> list) {
        for (String string : list) {
            System.out.println(string);
        }
    }
}
