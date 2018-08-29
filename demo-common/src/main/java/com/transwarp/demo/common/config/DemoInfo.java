package com.transwarp.demo.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by huangnx on 2018/8/13.
 */
@Component
@ConfigurationProperties(prefix = "demoinfo")
public class DemoInfo {

    private String       name;

    private String       desc;

    private List<String> creators;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<String> getCreators() {
        return creators;
    }

    public void setCreators(List<String> creators) {
        this.creators = creators;
    }

}
