package com.transwarp.demo.portal.controller;

import com.transwarp.demo.common.config.DemoInfo;
import com.transwarp.demo.dto.GetUserInfoReqDto;
import com.transwarp.demo.dto.LoginUserReqDto;
import com.transwarp.demo.dto.RegiserUserInfoReqDto;
import com.transwarp.demo.ext.UserInfoExtSerivce;
import com.transwarp.demo.portal.common.entity.JsonResult;
import com.transwarp.demo.portal.common.util.FormConventUtil;
import com.transwarp.demo.portal.form.LoginForm;
import com.transwarp.demo.portal.form.RegisterForm;
import com.transwarp.demo.result.LoginUserResult;
import com.transwarp.demo.result.RegisterUserInfoResult;
import com.transwarp.demo.result.UserInfoListResult;
import com.transwarp.demo.result.UserInfoResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by huangnx on 2018/8/13.
 */
@RestController
public class UserController {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private DemoInfo            demoInfo;

    @Autowired
    private UserInfoExtSerivce  userInfoExtSerivce;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "Hello World!";
    }

    @RequestMapping(value = "/demoInfo", method = RequestMethod.GET)
    public DemoInfo demoInfo() {
        return demoInfo;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public JsonResult register(@Valid RegisterForm registerForm, BindingResult bindingResult) {
        JsonResult jsonResult = new JsonResult();

        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            for (ObjectError error : list) {
                LOGGER.debug(error.getDefaultMessage());
            }
            jsonResult.fail("paramCheckFail", "请检查表单，填写规范");
        } else {
            RegiserUserInfoReqDto regiserUserInfoReqDto = FormConventUtil.convent(registerForm);
            RegisterUserInfoResult registerUserInfoResult = userInfoExtSerivce.register(regiserUserInfoReqDto);

            jsonResult.copy(registerUserInfoResult);
        }

        return jsonResult;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JsonResult login(@Valid LoginForm loginForm, BindingResult bindingResult) {
        JsonResult jsonResult = new JsonResult();

        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            for (ObjectError error : list) {
                LOGGER.debug(error.getDefaultMessage());
            }
            jsonResult.fail("paramCheckFail", "请检查表单，填写规范");
        } else {
            LoginUserReqDto loginUserReqDto = FormConventUtil.convent(loginForm);
            LoginUserResult loginUserResult = userInfoExtSerivce.login(loginUserReqDto);

            jsonResult.copy(loginUserResult);
        }

        return jsonResult;
    }

    @RequestMapping(value = "/getUserInfo/{userName}", method = RequestMethod.GET)
    public JsonResult getUserInfo(@PathVariable("userName") String userName) {
        JsonResult jsonResult = new JsonResult();

        if (StringUtils.isEmpty(userName)) {
            jsonResult.fail("paramCheckFail", "请求参数错误");
        } else {
            GetUserInfoReqDto getUserInfoReqDto = new GetUserInfoReqDto();
            getUserInfoReqDto.setUserName(userName);
            UserInfoResult userInfoResult = userInfoExtSerivce.getUserInfo(getUserInfoReqDto);

            jsonResult.copy(userInfoResult);
            jsonResult.setData(userInfoResult);
        }

        return jsonResult;
    }

    @RequestMapping(value = "/getUserInfoList", method = RequestMethod.GET)
    public JsonResult getUserInfoList() {
        JsonResult jsonResult = new JsonResult();

        UserInfoListResult userInfoListResult = userInfoExtSerivce.getUserInfoList();

        jsonResult.copy(userInfoListResult);
        jsonResult.setData(userInfoListResult);

        return jsonResult;
    }
}
