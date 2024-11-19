package com.cyberpeace.config.custom;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 系统配置
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Component
public class SystemProperty {


    /**
     * 初始化环境
     *
     * @throws
     */
    @PostConstruct
    public void initEnv() {
        // 获取视频总时长时，禁用检查视频大小属性
        System.setProperty("org.bytedeco.javacpp.maxphysicalbytes", "0");
    }

}
