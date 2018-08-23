package com.transwarp.demo.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by huangnx on 2018/8/13.
 */
@Controller
public class UserController {

    @ResponseBody
    @RequestMapping("/")
    public String home() {
        return "Hello World!";
    }

}
