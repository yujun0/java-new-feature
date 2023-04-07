package com.practice.pk1;

import com.practice.BasicTest;
import org.junit.Test;

public class StudentTest extends BasicTest {

    @Test
    public void test01() {
        // 創建Record object
        Student lisa = new Student(1, "lisa", "lisa@gmail.com", 25);
        System.out.println("lisa = " + lisa);

        // public方法器，獲取屬性值，readonly，沒有get、set方法
        Integer id = lisa.id();
        String name = lisa.name();

        System.out.println("id = " + id);
        System.out.println("name = " + name);

        Student susan = new Student(2, "Susan", "susan@gmail.com", 22);
        System.out.println("equals: " + lisa.equals(susan));
    }

    @Test
    public void test02() {
        Student lisa = new Student(1, "lisa", "lisa@gmail.com", 25);
        String str = lisa.concat();
        System.out.println("str = " + str);
    }

    @Test
    public void test03() {
        // 使用類.靜態方法
        String email = Student.emailToUpperCase("lisa@gmail.com");
        System.out.println("email = " + email);
    }

    @Test
    public void test04() {
        Student student = new Student(2, "lisa");
        System.out.println("student = " + student);
    }

    @Test
    public void test05() {
        ProductRecord productRecord = new ProductRecord(1001, "手機", 200);
        productRecord.print();
    }

    @Test
    public void test06() {
        // 定義 Local Record
        record SaleRecord(String saleId, String productName, Integer money){}
        // 創建object
        SaleRecord saleRecord = new SaleRecord("S001", "顯示器", 5000);

        System.out.println("saleRecord = " + saleRecord);
    }
}