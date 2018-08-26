package com.transwarp.demo.dto;

import com.transwarp.demo.common.entity.Request;

/**
 * Created by huangnx on 2018/8/26.
 */
public class LoginUserReqDto extends Request {
    private String userName;

    private String psw;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }
}
