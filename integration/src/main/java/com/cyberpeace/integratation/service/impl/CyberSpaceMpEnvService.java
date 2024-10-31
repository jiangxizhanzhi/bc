package com.cyberpeace.integratation.service.impl;

import com.cyberpeace.common.util.SystemPropUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/*
数字空间mp地址
 */
@Slf4j
@Service
public class CyberSpaceMpEnvService {

    //数字空间mp ip
    private String MP_HOST;

    //数字空间mp端口
    private String MP_PORT;

    //数字空间mp url
    private String MP_URL;

    //获取数字空间mp地址
    public String getMpHost() {
        return MP_URL;
    }

    //初始化
    @PostConstruct
    public void init() {
        MP_HOST = SystemPropUtil.getProp("CYBER_MP_HOST");
        MP_PORT = SystemPropUtil.getProp("CYBER_MP_PORT");
        MP_URL = "http://" + MP_HOST + ":" + MP_PORT;

        log.info("cyber space mp host=[{}], port=[{}]", MP_HOST, MP_PORT);
    }
}
