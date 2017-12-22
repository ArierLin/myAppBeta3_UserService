package com.jr.logback;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.jr.utils.IPUtil;

public class LogBackIpConvert extends ClassicConverter {
    @Override
    public String convert(ILoggingEvent iLoggingEvent) {
        return IPUtil.LOCAL_IP;
    }
}
