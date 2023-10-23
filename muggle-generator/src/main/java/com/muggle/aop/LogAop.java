package com.muggle.aop;

public @interface LogAop {
    String handler()default "commonLogHandler";

}
