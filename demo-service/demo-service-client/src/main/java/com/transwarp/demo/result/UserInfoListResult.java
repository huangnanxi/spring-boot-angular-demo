package com.transwarp.demo.result;

import com.transwarp.demo.common.entity.Result;

import java.util.List;

/**
 * Created by huangnx on 2018/8/26.
 */
public class UserInfoListResult extends Result {

    private List<UserInfoResult> itemList;

    public List<UserInfoResult> getItemList() {
        return itemList;
    }

    public void setItemList(List<UserInfoResult> itemList) {
        this.itemList = itemList;
    }
}
