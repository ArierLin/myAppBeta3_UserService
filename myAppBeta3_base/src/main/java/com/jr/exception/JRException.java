package com.jr.exception;

import com.jr.ResponseCode;

/**
 *系统异常响应码
 */
public class JRException extends RuntimeException {

    /**
     * 响应码
     */
    private int code;
    /**
     * 相应描述
     */
    private String mes;

    public JRException(int code, String mes) {
        this.code = code;
        this.mes = mes;
    }

    public JRException(ResponseCode responseCode) {
        this.code = responseCode.getCode();
        this.mes = responseCode.getDes();
    }

    public JRException(String message, int code, String mes) {
        super(message);
        this.code = code;
        this.mes = mes;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    @Override
    public String toString() {
        return "NhException{" +
                "code=" + code +
                ", mes='" + mes + '\'' +
                '}';
    }
}
