package com.example.springboottest.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerAspect {

    @Pointcut("execution(* com.example.springboottest.controller.*.*(..))")
    public void requestPointcut() {

    }
    //使用@Around注解的方法的返回值，会替换掉原函数的返回值，所以通常用Object，不为void
    //第一个参数必须是ProceedingJoinPoint
    @Around("requestPointcut()")
    public Object calculateTime(ProceedingJoinPoint pjp) {
        var start = System.nanoTime();
        String ret;
        try {
            ret = (String)pjp.proceed();
            System.out.println("execution result: " + ret);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        var end = System.nanoTime();
        System.out.println("execution time:" + (end - start));
        return ret;
    }

    @Before("requestPointcut()")
    public void before(JoinPoint joinPoint) {
        Thread thread = Thread.currentThread();
        System.out.println("current thread: " + thread.getName() + "current obj: " + joinPoint.getThis());
    }
}
