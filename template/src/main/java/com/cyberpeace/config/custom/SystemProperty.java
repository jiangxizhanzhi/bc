package com.cyberpeace.config.custom;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SystemProperty {


    @PostConstruct
    public void initEnv() {
        // 获取视频总时长时，禁用检查视频大小属性
        System.setProperty("org.bytedeco.javacpp.maxphysicalbytes", "0");
    }

}
