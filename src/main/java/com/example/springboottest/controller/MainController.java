package com.example.springboottest.controller;

import com.example.springboottest.entity.Animal;
import com.example.springboottest.entity.Cat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Proxy;

@RequestMapping("/api")
@Controller
public class MainController {

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello";
    }


    /**
     * 测试动态代理, 代理Cat的mew方法, 在方法前后打印日志
     * 注意：被代理的Cat类的mew方法，必须来源于一个接口，否则获取到动态代理对象之后，其类型为Object
     * 需要通过类型强转，来调用代理后的mew方法，所以必须有一个接口让其能强转
     * @return
     */
    @RequestMapping("/mew")
    @ResponseBody
    public String mew() {
        Cat cat = new Cat();
        Object catProxy = Proxy.newProxyInstance(cat.getClass().getClassLoader(), cat.getClass().getInterfaces(), (proxy, method, args) -> {
            System.out.println("before mew");
            Object ret = method.invoke(cat, args);
            System.out.println("after mew");
            return ret;
        });
        ((Animal) catProxy).mew();
        return "mew";
    }
}
