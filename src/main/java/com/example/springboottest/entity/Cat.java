package com.example.springboottest.entity;

/**
 * {@inheritDoc}
 * Subclass of the Animal
 * @see com.example.springboottest.entity.Animal
 */
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
