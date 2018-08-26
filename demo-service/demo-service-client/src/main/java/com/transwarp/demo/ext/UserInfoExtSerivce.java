package com.transwarp.demo.ext;

import com.transwarp.demo.dto.GetUserInfoReqDto;
import com.transwarp.demo.dto.LoginUserReqDto;
import com.transwarp.demo.dto.RegiserUserInfoReqDto;
import com.transwarp.demo.result.LoginUserResult;
import com.transwarp.demo.result.RegisterUserInfoResult;
import com.transwarp.demo.result.UserInfoResult;

/**
 * Created by huangnx on 2018/8/26.
 */
public interface UserInfoExtSerivce {

    /**
     * 注册用户
     * @param regiserUserInfoReqDto
     * @return
     */
    public RegisterUserInfoResult register(RegiserUserInfoReqDto regiserUserInfoReqDto);

    /**
     * 登录
     * @param loginUserReqDto
     * @return
     */
    public LoginUserResult login(LoginUserReqDto loginUserReqDto);

    /**
     * 获取用户信息
     * @param getUserInfoReqDto
     * @return
     */
    public UserInfoResult getUserInfo(GetUserInfoReqDto getUserInfoReqDto);
}
