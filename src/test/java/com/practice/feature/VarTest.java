package com.practice.feature;

import org.junit.Test;

import java.util.List;

public class VarTest {

    @Test
    public void test1() {
        var s1 = "Lisa";
        var age = 25;
        for (var i = 0; i < 10; i++) {
            System.out.println("i = " + i);
        }

        List<String> strings = List.of("a", "b", "c");
        for (var str : strings) {
            System.out.println("str = " + str);
        }
    }
}
