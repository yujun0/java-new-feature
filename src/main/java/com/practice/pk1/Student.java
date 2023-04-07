package com.practice.pk1;

import java.util.Optional;

public record Student(Integer id, String name, String email, Integer age) {
    // 實例方法，concat連接字符串
    public String concat() {
        return String.format("姓名是%s, 年齡是%d", this.name, this.age);
    }

    // 靜態方法，把email轉為大寫
    public static String emailToUpperCase(String email) {
        return Optional.ofNullable(email).orElse("no email").toUpperCase();
    }

    // 緊湊
    public Student {
        System.out.println("id = " + id);
        if (id < 1) {
            throw new RuntimeException("id < 1");
        }
    }

    // 定製構造方法
    public Student(Integer id, String name) {
        this(id, name, null, null);
    }
}
