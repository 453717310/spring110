package cn.bdqn.dao;

/**
 * Created by dll on 2017/9/29.
 */

public class Dog implements Animal {

    public Dog(){
        System.out.println("小狗的构造被执行");
    }
    /**
     * 吃的方法
     */
    @Override
    public void eat() {
        System.out.println("小狗在吃饭");
    }

    /**
     * 睡觉的方法
     */
    @Override
    public void sleep() {
        System.out.println("小狗在睡觉");
    }
}
