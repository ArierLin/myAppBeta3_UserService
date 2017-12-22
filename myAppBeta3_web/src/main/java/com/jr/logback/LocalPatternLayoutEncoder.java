package com.jr.logback;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;

public class LocalPatternLayoutEncoder extends PatternLayoutEncoder {
    static {
        //本机ip地址
        PatternLayout.defaultConverterMap.put("ip", LogBackIpConvert.class.getName());
        //日志id
        PatternLayout.defaultConverterMap.put("logId", LogBackLogIdConvert.class.getName());
        //接口
        PatternLayout.defaultConverterMap.put("intf", LogBackInterfaceConvert.class.getName());
    }
}
