package com.practice.pk6;

public sealed interface SomeService permits SomeServiceImpl {
    void doThing();
}
