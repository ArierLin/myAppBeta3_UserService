package com.jr.logback;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.slf4j.MDC;

public class LogBackInterfaceConvert extends ClassicConverter {
    @Override
    public String convert(ILoggingEvent iLoggingEvent) {
        String intf = MDC.get("intf");
        StackTraceElement[] cda = iLoggingEvent.getCallerData();
        if (null == intf && null != cda && cda.length >0){
            intf = cda[0].getClass().getSimpleName() + "." + cda[0].getMethodName();
        }
        return intf;
    }
}
