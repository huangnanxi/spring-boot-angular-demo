package com.transwarp.demo.service.service;

import com.transwarp.demo.common.response.UserResponseCode;
import com.transwarp.demo.common.trace.ServiceTrace;
import com.transwarp.demo.dmo.UserInfo;
import com.transwarp.demo.dto.GetUserInfoReqDto;
import com.transwarp.demo.dto.LoginUserReqDto;
import com.transwarp.demo.dto.RegiserUserInfoReqDto;
import com.transwarp.demo.result.LoginUserResult;
import com.transwarp.demo.result.RegisterUserInfoResult;
import com.transwarp.demo.result.UserInfoResult;
import com.transwarp.demo.service.dao.primary.UserInfoDb1Dao;
import com.transwarp.demo.service.dao.secondary.UserInfoDb2Dao;
import com.transwarp.demo.service.inf.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huangnx on 2018/8/26.
 */
@Service
@ServiceTrace
public class UserInfoServiceImpl implements UserInfoService {

    /**
     * 该demo演示springboot配置双数据源
     */
    @Autowired
    private UserInfoDb1Dao userInfoDb1Dao;

    @Autowired
    private UserInfoDb2Dao userInfoDb2Dao;

    /**
     *
     * 方法仅做展示（内部操作两个2数据库，未配事务控制）
     *
     * @param regiserUserInfoReqDto
     * @return
     */
    @Override
    public RegisterUserInfoResult register(RegiserUserInfoReqDto regiserUserInfoReqDto) {
        RegisterUserInfoResult registerUserInfoResult = new RegisterUserInfoResult();

        Boolean insertStatus1 = userInfoDb1Dao.register(regiserUserInfoReqDto) > 0;

        Boolean insertStatu2 = userInfoDb2Dao.register(regiserUserInfoReqDto) > 0;

        if (!insertStatus1 || !insertStatu2) {
            registerUserInfoResult.fail(UserResponseCode.REGISTER_FAIL_CODE, UserResponseCode.REGISTER_FAIL_MSG);
        }

        return registerUserInfoResult;
    }

    @Override
    public LoginUserResult login(LoginUserReqDto loginUserReqDto) {
        LoginUserResult loginUserResult = new LoginUserResult();

        Boolean getStatus1 = userInfoDb1Dao.login(loginUserReqDto) > 0;

        Boolean getStatus2 = userInfoDb2Dao.login(loginUserReqDto) > 0;

        if (!getStatus1 || !getStatus2) {
            loginUserResult.fail(UserResponseCode.LOGIN_FAIL_CODE, UserResponseCode.LOGIN_FAIL_MSG);
        }
        return loginUserResult;
    }

    @Override
    public UserInfoResult getUserInfo(GetUserInfoReqDto getUserInfoReqDto) {
        UserInfoResult userInfoResult = new UserInfoResult();

        UserInfo userInfo1 = userInfoDb1Dao.getUserInfo(getUserInfoReqDto);
        UserInfo userInfo2 = userInfoDb2Dao.getUserInfo(getUserInfoReqDto);
        if (userInfo1 == null || userInfo2 == null) {
            userInfoResult.fail(UserResponseCode.USER_INFO_NOT_EXISTS_CODE, UserResponseCode.USER_INFO_NOT_EXISTS_MSG);
        }

        userInfoResult.setUserName(userInfo1.getUserName());
        userInfoResult.setPsw(userInfo1.getPsw());
        userInfoResult.setRealName(userInfo1.getRealName());
        userInfoResult.setAge(userInfo1.getAge());

        return userInfoResult;
    }
}
