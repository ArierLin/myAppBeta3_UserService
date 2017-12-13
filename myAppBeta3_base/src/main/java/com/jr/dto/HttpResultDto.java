package com.jr.dto;

/**
 * http请求返回结果
 * @Author: ZhangQingrong
 * @Date : 2017/8/30 15:15
 */
public class HttpResultDto<T> {

    /**
     * 状态码
     */
    private Boolean isSuccess;
    /**
     * 错误码
     * */
    private String errorCode;
    /**
     * 错误miaos
     * */
    private String errorMsg;

    /**
     * 返回数据
     * */
    private T result;

    public void setError(String errorCode, String errorMsg){
        this.isSuccess = false;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public void setResult(T result){
        this.isSuccess = true;
        this.result = result;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "HttpResultDto{" +
                "isSuccess=" + isSuccess +
                ", errorCode='" + errorCode + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", result=" + result +
                '}';
    }
}
