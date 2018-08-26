package com.transwarp.demo.common.entity;

/**
 * Created by huangnx on 2018/8/26.
 */
public class Result extends Entity {

    private Boolean success = true;

    private String  errorCode;

    private String  errorMsg;

    public void copy(Result result) {
        this.setSuccess(result.getSuccess());
        this.setErrorCode(result.getErrorCode());
        this.setErrorMsg(result.getErrorMsg());
    }

    public void fail(String errorCode, String errorMsg) {
        this.setSuccess(false);
        this.setErrorCode(errorCode);
        this.setErrorMsg(errorMsg);
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
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
}
