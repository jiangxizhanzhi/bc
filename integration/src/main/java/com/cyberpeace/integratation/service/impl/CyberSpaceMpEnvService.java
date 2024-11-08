package com.cyberpeace.integratation.service.impl;

import com.cyberpeace.common.util.SystemPropUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 网络空间中台环境Service
 *
 * @author SN
 * @date 2024/11/08
 *//*
数字空间mp地址
 */
@Slf4j
@Service
public class CyberSpaceMpEnvService {

    /**
     * 中台主机
     *///数字空间mp ip
    private String MP_HOST;

    /**
     * 中台港口
     *///数字空间mp端口
    private String MP_PORT;

    /**
     * 中台网址
     *///数字空间mp url
    private String MP_URL;

    /**
     * 获取中台主机
     *
     * @return {@code String }
     *///获取数字空间mp地址
    public String getMpHost() {
        return MP_URL;
    }

    /**
     * 初始化
     *///初始化
    @PostConstruct
    public void init() {
        MP_HOST = SystemPropUtil.getProp("CYBER_MP_HOST");
        MP_PORT = SystemPropUtil.getProp("CYBER_MP_PORT");
        MP_URL = "http://" + MP_HOST + ":" + MP_PORT;

        log.info("cyber space mp host=[{}], port=[{}]", MP_HOST, MP_PORT);
    }
}
