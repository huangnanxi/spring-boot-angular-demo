package com.transwarp.demo.service.ext;

import com.transwarp.demo.common.trace.ServiceTrace;
import com.transwarp.demo.dto.GetUserInfoReqDto;
import com.transwarp.demo.dto.LoginUserReqDto;
import com.transwarp.demo.dto.RegiserUserInfoReqDto;
import com.transwarp.demo.ext.UserInfoExtSerivce;
import com.transwarp.demo.result.LoginUserResult;
import com.transwarp.demo.result.RegisterUserInfoResult;
import com.transwarp.demo.result.UserInfoResult;
import com.transwarp.demo.service.inf.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huangnx on 2018/8/26.
 */
@Service
@ServiceTrace
public class UserInfoExtServiceImpl implements UserInfoExtSerivce {

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public RegisterUserInfoResult register(RegiserUserInfoReqDto regiserUserInfoReqDto) {
        return userInfoService.register(regiserUserInfoReqDto);
    }

    @Override
    public LoginUserResult login(LoginUserReqDto loginUserReqDto) {
        return userInfoService.login(loginUserReqDto);
    }

    @Override
    public UserInfoResult getUserInfo(GetUserInfoReqDto getUserInfoReqDto) {
        return userInfoService.getUserInfo(getUserInfoReqDto);
    }
}
