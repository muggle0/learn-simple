package com.muggle.learn;

import com.muggle.learn.annotation.TestQuery;

@TestQuery(processor = "xxx")
public interface TestInterface {
    public String test();
}
