package com.transwarp.demo.portal.common;

import com.transwarp.demo.common.Entity.Result;

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
