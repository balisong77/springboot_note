package com.example.springboottest.entity;

/**
 * @author Lunqi Zhao, 1085789239@qq.com
 * @since v1.0
 * A base class used to test JDK dynamic proxy.
 * <p>
 * Has a default method <code>mew()}</code> to demonstrate JDK Proxy {@link java.lang.reflect.Proxy} Class
 * </p>
 *
 */
public interface Animal {
    public default void mew() {
        System.out.println("animal mew");
    }
}
