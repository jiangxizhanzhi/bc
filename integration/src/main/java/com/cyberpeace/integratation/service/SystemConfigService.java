package com.cyberpeace.integratation.service;

/**
 * 系统配置Service
 *
 * @author SN
 * @date 2024/11/14
 */
public interface SystemConfigService {

    /**
     * 获取系统配置
     *
     * @param name 名称
     * @return {@code String }
     */
    String getSystemConfig(String name);

}
