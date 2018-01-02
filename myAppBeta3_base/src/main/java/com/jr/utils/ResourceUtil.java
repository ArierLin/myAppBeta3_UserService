package com.jr.utils;

import java.util.ResourceBundle;

/**
 * @author jql
 * @create 2017-12-29 11:44
 * @desc 读取配置文件：方式一
 **/
public class ResourceUtil {

    private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("jrConfig");
    /**
     * 获取随机码的长度
     *
     * @return 随机码的长度
     */
    public static String getRandCodeLength() {
        return bundle.getString("randCodeLength");
    }

    /**
     * 获取随机码的类型
     *
     * @return 随机码的类型
     */
    public static String getRandCodeType() {
        return bundle.getString("randCodeType");
    }

    /**
     * 获取随机码的干扰线数量
     *
     * @return 干扰线数量
     */
    public static int getRandCodeLineCount(){
        return Integer.parseInt(bundle.getString("randCodeLineCount"));
    }

    /**
     * 获取随机码的干扰线数量
     *
     * @return 干扰线数量
     */
    public static int getRandCodeLineWidth(){
        return Integer.parseInt(bundle.getString("lineWidth"));
    }
}
