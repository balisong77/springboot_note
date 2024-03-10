package com.example.springboottest.controller;

import com.example.springboottest.entity.Animal;
import com.example.springboottest.entity.Cat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@RequestMapping("/api")
@Controller
public class MainController {

    @Value("${name}")
    private String name;

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello";
    }


    /**
     * 测试动态代理, 代理Cat的mew方法, 在方法前后打印日志
     * 注意：被代理的Cat类的mew方法，必须来源于一个接口，否则获取到动态代理对象之后，其类型为Object
     * 需要通过类型强转，来调用代理后的mew方法，所以必须有一个接口让其能强转
     * 动态代理会为被代理的对象的每一个方法都添加代理逻辑
     * JDK动态代理只能代理实现了接口的类，拦截接口中的方法
     * 而CGLIB可以拦截所有的方法
     * @return
     */
    @RequestMapping("/mew")
    @ResponseBody
    public String mew() {
        Cat cat = new Cat();
        Object catProxy = Proxy.newProxyInstance(cat.getClass().getClassLoader(), cat.getClass().getInterfaces(), (proxy, method, args) -> {
            System.out.println("before dynamic proxy");
            Object ret = method.invoke(cat, args);
            System.out.println("after dynamic proxy");
            return ret;
        });
        ((Animal) catProxy).mew();
        ((Animal) catProxy).walk();
        return "mew";
    }

    /**
     * 测试CGLIB动态代理
     * CGLIB不支持final类和方法：由于CGlib是通过extends(创建一个子类)来实现代理的，所以无法代理final类和方法。
     * https://www.liaoxuefeng.com/wiki/1252599548343744/1339039378571298
     * @return
     */
    public class TargetInterceptor implements MethodInterceptor {
        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            System.out.println("CGLIB 调用前");
            Object result = proxy.invokeSuper(obj, args);
            System.out.println("CGLIB 调用后");
            return result;
        }
    }
    @RequestMapping("/eat")
    @ResponseBody
    public String eat() {
        Cat cat = new Cat();
        Enhancer enhancer = new Enhancer();
        enhancer.setClassLoader(cat.getClass().getClassLoader());
        enhancer.setSuperclass(Cat.class);
        enhancer.setCallback(new TargetInterceptor());
        Cat catProxy = (Cat) enhancer.create();
        catProxy.eat();
        return "eat";
    }

    @RequestMapping("/getValue")
    @ResponseBody
    public String getValue() {
        return this.name;
    }
}
