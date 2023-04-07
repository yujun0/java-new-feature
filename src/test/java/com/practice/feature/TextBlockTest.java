package com.practice.feature;

import org.junit.Test;

public class TextBlockTest {

    @Test
    public void test1() {
        String s1 = """
                hello world
                """;

    }

    @Test
    public void test2() {
        String s1 = """
                hello world
                """;
        String s2 = """
                hello world   
                """;
        boolean flag = s1.equals(s2);
        System.out.println("flag = " + flag);

        boolean f = s1 == s2;
        System.out.println("f = " + f);

        String msg = """
                hello world
                """;
        String str = msg.substring(0, 5);
        System.out.println("str = " + str);
    }

    @Test
    public void test3() {
        String msg = """
                    <html>
                    <body>Text Block</body>
                    </html>
                """;
        System.out.println(msg);

    }

    @Test
    public void test4() {
        String color = """
                red
                blue
                green
                """;
        color = color.indent(15);
        System.out.println(color);
    }

    @Test
    public void test5() {
        String info = """
                Name: %s,
                Phone: %s,
                Age: %d
                """.formatted("Lisa", "0911234221", 25);
        System.out.println(info);
    }

    @Test
    public void test6() {
        String str = """
                Spring Boot 是一個快速開發框架 \
                基於\"Spring\"框架，創建Spring應用 \
                內嵌Web服務器，以jar或war方式運行 \
                """;
        System.out.println(str);
    }

}
