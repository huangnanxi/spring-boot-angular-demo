package com.transwarp.demo.dto;

import com.transwarp.demo.common.entity.Request;

/**
 * Created by huangnx on 2018/8/26.
 */
public class GetUserInfoReqDto extends Request {

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
