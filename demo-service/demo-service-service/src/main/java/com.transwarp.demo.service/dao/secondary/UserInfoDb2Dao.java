package com.transwarp.demo.service.dao.secondary;

import com.transwarp.demo.dmo.UserInfo;
import com.transwarp.demo.dto.GetUserInfoReqDto;
import com.transwarp.demo.dto.LoginUserReqDto;
import com.transwarp.demo.dto.RegiserUserInfoReqDto;
import org.apache.ibatis.annotations.*;

/**
 * Created by huangnx on 2018/8/23.
 */
@Mapper
public interface UserInfoDb2Dao {

    /**
     * 注册用户
     * @param regiserUserInfoReqDto
     * @return
     */
    @Insert("INSERT INTO T_USER_INFO(USER_NAME,PSW,REAL_NAME,AGE) VALUES(#{userName},#{psw},#{realName},#{age})")
    public Integer register(RegiserUserInfoReqDto regiserUserInfoReqDto);

    /**
     * 登录
     * @param loginUserReqDto
     * @return
     */
    @Select("SELECT COUNT(USER_NAME) FROM T_USER_INFO WHERE USER_NAME = #{userName} AND PSW = #{psw}")
    public Integer login(LoginUserReqDto loginUserReqDto);

    /**
     * 获取用户信息
     * @param getUserInfoReqDto
     * @return
     */
    @Select("SELECT * FROM T_USER_INFO WHERE USER_NAME = #{userName}")
    @Results({ @Result(column = "USER_NAME", property = "userName"), @Result(column = "PSW", property = "psw"),
            @Result(column = "REAL_NAME", property = "realName"), @Result(column = "AGE", property = "age") })
    public UserInfo getUserInfo(GetUserInfoReqDto getUserInfoReqDto);
}
