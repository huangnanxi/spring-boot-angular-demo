package com.transwarp.demo.dmo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by huangnx on 2018/8/23.
 */
@Entity
@Table(name = "T_USER_INFO")
public class UserInfo implements Serializable {

    @Id
    @Column(name = "USER_NAME")
    private String  userName;

    @Column(name = "PSW")
    private String  psw;

    @Column(name = "REAL_NAME")
    private String  realName;

    @Column(name = "AGE")
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
