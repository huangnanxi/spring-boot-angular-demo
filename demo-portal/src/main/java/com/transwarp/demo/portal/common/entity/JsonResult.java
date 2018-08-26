package com.transwarp.demo.portal.common.entity;

import com.transwarp.demo.common.entity.Result;

/**
 * Created by huangnx on 2018/8/26.
 */
public class JsonResult extends Result {
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
