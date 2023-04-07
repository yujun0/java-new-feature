package com.practice.pk3;

import org.junit.Test;

public class PersonTest {

    @Test
    public void test01() {
        Person person = new Person("Lisa", 20);
        SomeService someService = new SomeService();
        boolean flag = someService.isEligible(person);
        System.out.println("flag = " + flag);
    }
}