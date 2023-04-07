package com.practice.feature;

import com.practice.pk6.SomeService;
import com.practice.pk6.SomeServiceImpl;
import org.junit.Test;

public class SealedTest {

    @Test
    public void test01() {
        SomeService service = new SomeServiceImpl();
        service.doThing();
    }
}
