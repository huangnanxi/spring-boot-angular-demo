package com.transwarp.demo.portal.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by huangnx on 2018/8/26.
 */
public class RegisterForm {

    @NotNull(message = "用户名不能为空")
    private String  userName;

    @NotNull(message = "密码不能为空")
    @Size(min = 6, message = "密码长度不能少于6位")
    private String  psw;

    @NotNull(message = "真实姓名不能为空")
    private String  realName;

    @NotNull(message = "年龄不能为空")
    @Min(value = 0, message = "年龄应大于0")
    @Max(value = 100, message = "年龄应小于100")
    private Integer age;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
