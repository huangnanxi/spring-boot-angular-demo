package com.transwarp.demo.portal.controller;

import com.transwarp.demo.dto.RegiserUserInfoReqDto;
import com.transwarp.demo.ext.UserInfoExtSerivce;
import com.transwarp.demo.portal.common.entity.JsonResult;
import com.transwarp.demo.portal.common.util.FormConventUtil;
import com.transwarp.demo.portal.form.LoginForm;
import com.transwarp.demo.portal.form.RegisterForm;
import com.transwarp.demo.result.RegisterUserInfoResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserInfoExtSerivce  userInfoExtSerivce;

    @RequestMapping("/")
    public String home() {
        return "Hello World!";
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
    public String register(LoginForm loginForm) {
        return "Hello World!";
    }

    @RequestMapping(value = "/getUserInfo/{userName}", method = RequestMethod.GET)
    public String register(@PathVariable("userName") String userName) {
        return "Hello World!";
    }

}
