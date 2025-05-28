/**
 * 这段代码定义了一个名为 `CyberSpaceMpEnvService` 的 Spring 服务类，用于管理网络空间中台环境的相关配置。以下是详细的分析： 
 * ### 类与注解 
 * - **`@Slf4j`**：这是一个 Lombok 注解，用于自动生成日志记录器。 
 * - **`@Service`**：这是一个 Spring 注解，将该类声明为一个 Spring 服务组件，使其可以在 Spring 容器中被自动发现和管理。 
 * ### 成员变量 
 * - **`MP_HOST`**：存储中台主机的 IP 地址。 
 * - **`MP_PORT`**：存储中台主机的端口号。 
 * - **`MP_URL`**：存储组合后的中台 URL（格式为 `http://<IP>:<PORT>`）。 
 * ### 方法 
 * - **`getMpHost()`**：返回中台的 URL。 
 * - 注意这里返回的是 `MP_URL` 而不是 `MP_HOST`，这可能是代码中的一个小错误。 
 * - **`init()`**：在 Bean 初始化完成后执行的方法。 
 * - 使用 `@PostConstruct` 注解，确保这个方法在构造函数之后、其他依赖注入之前调用。 
 * - 从系统属性中读取中台主机和端口，并组合成完整的 URL。 
 * - 记录初始化时的主机和端口信息到日志。 
 * ### 总结 
 * - **功能**：该服务类主要用于管理和初始化网络空间中台环境的配置信息。 
 * - **潜在问题**： 
 * - `getMpHost()` 方法返回的是 `MP_URL` 而不是 `MP_HOST`，这可能是一个错误。 
 * - `MP_URL` 应该是根据 `MP_HOST` 和 `MP_PORT` 动态生成的，而不是直接赋值。 
 * ### 改进建议 
 * - 修改 `getMpHost()` 方法，使其返回 `MP_HOST` 而不是 `MP_URL`。 
 * - 在 `init()` 方法中，应该先检查 `MP_HOST` 和 `MP_PORT` 是否为空或无效，以避免组合出不正确的 URL。 
 * ```java 
 * public String getMpHost() { 
 * return MP_HOST; // 修改返回值为 MP_HOST 
 * } 
 * ``` 
 * 通过这些改进，代码的功能将更加清晰和正确。 
**/
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
