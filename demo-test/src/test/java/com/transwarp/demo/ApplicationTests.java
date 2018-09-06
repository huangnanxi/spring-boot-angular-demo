package com.transwarp.demo;

import com.alibaba.fastjson.JSON;
import com.transwarp.demo.common.config.DemoInfo;
import com.transwarp.demo.ext.UserInfoExtSerivce;
import com.transwarp.demo.result.UserInfoListResult;
import com.transwarp.demo.result.UserInfoResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by huangnx on 2018/8/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
public class ApplicationTests {

    private MockMvc                       mvc;

    @Autowired
    protected WebApplicationContext       wac;

    @Autowired
    private DemoInfo                      demoInfo;

    @Autowired
    private StringRedisTemplate           stringRedisTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UserInfoExtSerivce            userInfoExtSerivce;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void homeTest() throws Exception {
        String result = mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        System.out.println(result);

        Assert.assertEquals("Hello World!", result);
    }

    @Test
    public void demoInfoTest() throws Exception {
        String result = mvc.perform(MockMvcRequestBuilders.get("/demoInfo").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        DemoInfo resultDemoInfo = JSON.parseObject(result).toJavaObject(DemoInfo.class);

        Assert.assertEquals(demoInfo.getName(), resultDemoInfo.getName());
        Assert.assertEquals(demoInfo.getDesc(), resultDemoInfo.getDesc());
        Assert.assertEquals(demoInfo.getCreators(), resultDemoInfo.getCreators());
    }

    @Test
    public void testRedisStr() throws Exception {
        stringRedisTemplate.opsForValue().set("aaa", "111");
        Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));
    }

    @Test
    public void testRedisObj() throws Exception {

        UserInfoListResult userInfoListResult = userInfoExtSerivce.getUserInfoList();

        for (UserInfoResult userInfoResult : userInfoListResult.getItemList()) {
            redisTemplate.opsForValue().set(userInfoResult.getUserName(), userInfoResult);
        }

        for (UserInfoResult userInfoResult : userInfoListResult.getItemList()) {
            Assert.assertEquals(userInfoResult.getUserName(),
                    ((UserInfoResult) redisTemplate.opsForValue().get(userInfoResult.getUserName())).getUserName());
        }
    }
}
