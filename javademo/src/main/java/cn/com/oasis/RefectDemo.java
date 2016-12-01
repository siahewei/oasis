package cn.com.oasis;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/9/28 下午5:01
 */

public class RefectDemo {
    public static void main(String[] args){
        TestSub testSub = new TestSub();
        Class classz  = testSub.getClass();
        System.out.println(classz.getSuperclass());

        Type type= classz.getGenericSuperclass();
        System.out.println(type);

        ParameterizedType p=(ParameterizedType)type;
        Class c=(Class) p.getActualTypeArguments()[0];
        System.out.println(c);
    }

    public static class TestSub extends Test<List>{

    }

    public static class Test2{

    }
}
