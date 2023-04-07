package com.practice.pk2;

import org.junit.Test;

import java.lang.reflect.RecordComponent;

import static org.junit.Assert.*;

public class CustomerTest {
    @Test
    public void test01() {
        Address address = new Address("台北", "忠孝東路四段547號", "110");
        PhoneNumber phoneNumber = new PhoneNumber("110", "2233-4321");
        Customer customer = new Customer("C1001", "Lisa", phoneNumber, address);

        System.out.println("customer = " + customer);

        String number = customer.phoneNumber().number();
        System.out.println("number = " + number);

        String adr = customer.address().address();
        System.out.println("address = " + adr);
    }

    @Test
    public void test02() {
        Address address = new Address("台北", "忠孝東路四段547號", "110");
        PhoneNumber phoneNumber = new PhoneNumber("110", "2233-4321");
        Customer customer = new Customer("C1001", "Lisa", phoneNumber, address);

        RecordComponent[] recordComponents = customer.getClass().getRecordComponents();
        for (RecordComponent recordComponent : recordComponents) {
            System.out.println("recordComponent = " + recordComponent);
        }

        boolean record = customer.getClass().isRecord();
        System.out.println("record = " + record);
    }
}