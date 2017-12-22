package com.jr.logback;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.slf4j.MDC;

import java.util.UUID;

public class LogBackLogIdConvert extends ClassicConverter {
    @Override
    public String convert(ILoggingEvent iLoggingEvent) {
        if (null == MDC.get("logId")){
            MDC.put("logId", UUID.randomUUID().toString().replace("-",""));
        }
        return MDC.get("logId");
    }
}
