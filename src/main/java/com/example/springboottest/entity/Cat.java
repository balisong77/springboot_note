package com.example.springboottest.entity;

public class Cat implements Animal{
    public void mew() {
        System.out.println("mew");
    }

    public void walk() {
        System.out.println("cat walk");
    }

    public void eat() {
        System.out.println("cat eat");
    }
}
