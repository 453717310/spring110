package cn.bdqn.proxy;

import cn.bdqn.dao.Animal;
import cn.bdqn.dao.Dog;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by dll on 2017/10/9.
 */
public class MyProxy implements InvocationHandler {
    private Object target;

    /**
     * 给我一个委托类，我返回一个对象
     * @param target
     * @return
     */
    public Object createProxy(Object target){
        this.target=target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
    }

    /**
     * 实现系统级和主业务交互
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("主人在召唤！");//主业务
        Object result = method.invoke(target, args);
        System.out.println("主人在离开！");//主业务
        return result;
    }

    public static void main(String[] args) {
        MyProxy myProxy=new MyProxy();
        Animal dog = (Animal) myProxy.createProxy(new Dog());
        dog.eat();
        System.out.println("***********************");
        dog.sleep();
    }
}
