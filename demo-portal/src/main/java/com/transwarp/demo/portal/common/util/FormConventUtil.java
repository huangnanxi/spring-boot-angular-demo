package com.transwarp.demo.portal.common.util;

import com.transwarp.demo.dto.LoginUserReqDto;
import com.transwarp.demo.dto.RegiserUserInfoReqDto;
import com.transwarp.demo.portal.form.LoginForm;
import com.transwarp.demo.portal.form.RegisterForm;

/**
 * Created by huangnx on 2018/8/26.
 */
public class FormConventUtil {

    public static RegiserUserInfoReqDto convent(RegisterForm registerForm) {
        RegiserUserInfoReqDto regiserUserInfoReqDto = new RegiserUserInfoReqDto();
        regiserUserInfoReqDto.setUserName(registerForm.getUserName());
        regiserUserInfoReqDto.setPsw(registerForm.getPsw());
        regiserUserInfoReqDto.setRealName(registerForm.getRealName());
        regiserUserInfoReqDto.setAge(registerForm.getAge());

        return regiserUserInfoReqDto;
    }

    public static LoginUserReqDto convent(LoginForm registerForm) {
        LoginUserReqDto loginUserReqDto = new LoginUserReqDto();
        loginUserReqDto.setUserName(registerForm.getUserName());
        loginUserReqDto.setPsw(registerForm.getPsw());

        return loginUserReqDto;
    }
}
