package com.cyberpeace.integratation.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.integratation.service.YXLogService;
import com.cyberpeace.integratation.service.YXUserService;
import com.cyberpeace.integratation.service.bo.YxRvbUserColl;
import com.cyberpeace.integratation.service.bo.mp.YXLogRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 这段代码是一个Spring服务类 `YXLogServiceImpl`，实现了 `YXLogService` 接口。该类的主要功能是通过调用外部API获取用户信息，并将这些信息封装到 `YxRvbUserColl` 对象中返回。
 * ### 主要组成部分： 
 * 1. **依赖注入**： 
 * - 使用了阿里巴巴的 `fastjson` 库来处理JSON数据。 
 * - 使用了MyBatis-Plus提供的工具类 `StringUtils` 进行字符串操作。 
 * - 使用了 `SystemPropUtil` 和 `ConfigUtil` 工具类来读取系统属性和配置文件中的属性。 
 * 2. **字段定义**： 
 * - `YX_HOST`：用于存储从系统属性或配置文件中读取的主机地址。 
 * - `RVB_URL`：固定API路径，默认值为 `/api/v1/openapi/get_match_user`。 
 * 3. **初始化方法**： 
 * - `@PostConstruct` 注解的方法 `init()` 会在bean初始化完成后自动执行，用于初始化 `YX_HOST` 和 `RVB_URL`。 
 * 4. **业务逻辑方法**： 
 * - `getYxRvbUsers(Integer rvbId)` 方法根据传入的 `rvbId` 参数构造请求URL，并调用HTTP客户端发送POST请求，最终返回 `YxRvbUserColl` 对象。 
 * ### 关键点总结： 
 * - **初始化**：在bean初始化后调用 `init()` 方法设置必要的配置信息。 
 * - **业务逻辑**：通过调用外部API获取用户信息，并将结果转换为指定的对象类型返回。 
 * - **错误处理**：如果 `YX_HOST` 为空，则直接返回 `null`，没有进行更详细的错误处理。 
 * ### 优点： 
 * - 使用了注解驱动的方式，如 `@Service` 和 `@Slf4j`，使得代码简洁且易于理解。 
 * - 利用了Spring框架的生命周期方法 `@PostConstruct` 来确保配置项的正确加载。 
 * ### 可能的改进点： 
 * - **异常处理**：可以增加对网络请求失败、解析失败等异常情况的处理，以增强系统的健壮性。 
 * - **日志记录**：在关键步骤增加更多的日志记录，以便于问题排查和监控。 
 * - **配置管理**：考虑使用更灵活的配置管理方式（如配置中心），以方便配置的动态调整和维护。 
**/
@Service
@Slf4j
public class YXLogServiceImpl implements YXLogService {

    private static final int SYSTEM_ID_ZSKJ = 3;
    private String YX_HOST;

    private String UPLOAD_LOG_URL = "/api/v1/openapi/record_log";

    @PostConstruct
    public void init() {
        YX_HOST = SystemPropUtil.getProp("YX_HOST");
        log.info("yx host=[{}]", YX_HOST);
    }

    @Override
    public String uploadLog(YXLogRequest result) {
        if (StringUtils.isBlank(YX_HOST)){
            return null;
        }
        String url = YX_HOST + UPLOAD_LOG_URL;
        JSONObject body = new JSONObject();
        body.put("type", result.getType());
        body.put("ip", result.getIp());
        body.put("time", result.getTime());
        body.put("content", result.getContent());
        body.put("action", result.getAction());
        body.put("result", result.getResult());
        body.put("username", result.getUsername());
        body.put("role_id", result.getRoleId());
        body.put("system_id", SYSTEM_ID_ZSKJ);
        return HttpClientUtil.post(url, body);
    }
}

