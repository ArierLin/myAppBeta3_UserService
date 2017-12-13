package com.jr.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 南航字符串工具类
 * @Author: ZhangQingrong
 * @Date : 2017/9/13 16:33
 */
public class MyStringUtils {

    /**
     * 将异常堆栈打印到一行
     * */
    public static String getStackTraceAsString(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    public static String removeLineBreak(String request){
        String replaceResult = StringUtils.replace(request, "\n", "");
        return replaceResult;
    }
}
