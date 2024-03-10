package com.example.springboottest.entity;

public interface Animal {
    public default void mew() {
        System.out.println("animal mew");
    }
    public void walk();
}
