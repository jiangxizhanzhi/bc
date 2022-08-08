package com.cyberpeace.gateway.controller;

import javax.servlet.http.HttpServletResponse;


import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.gateway.controller.req.HldTokenReq;
import com.cyberpeace.integratation.model.HldToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;

import com.cyberpeace.integratation.service.RemoteUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "hld平台接入相关api")
@RestController
@RequestMapping("/auth/")
public class HldController {

    @Autowired
    private RemoteUserService remoteUserService;

    @ApiOperation("单点登录跳转")
    @PostMapping("/access")
    public void
    access(@RequestBody @Validated HldTokenReq hldTokenReq,
           HttpServletResponse response) throws Exception {

        // 提取远程用户token
        HldToken hldToken = new HldToken();
        hldToken.setUser_id(hldTokenReq.getUserId());
        hldToken.setSession_id(hldTokenReq.getSessionId());
        if (hldToken.getSession_id().equals("null")) {
            hldToken.setSession_id(null);
        }
        setResponse(response, hldToken);

    }

    @ApiOperation("单点登录跳转")
    @GetMapping("/access")
    public void access(
            @ApiParam(name = "session_id", value = "session_id") @RequestParam("session_id") String session_id,
            @ApiParam(name = "user_id", value = "user_id") @RequestParam("user_id") String user_id,
            HttpServletResponse response) throws Exception {
        HldToken hldToken = new HldToken();
        hldToken.setSession_id(session_id);
        hldToken.setUser_id(user_id);
        if (hldToken.getSession_id().equals("null")) {
            hldToken.setSession_id(null);
        }
        setResponse(response, hldToken);

    }

    @ApiOperation("登录跳转jwt")
    @GetMapping("/getAccess")
    public BaseRsp<String> getAccess(
            @ApiParam(name = "session_id", value = "session_id") @RequestParam("session_id") String session_id,
            @ApiParam(name = "user_id", value = "user_id") @RequestParam("user_id") String user_id
    ) {
        HldToken hldToken = new HldToken();
        hldToken.setSession_id(session_id);
        hldToken.setUser_id(user_id);
log.info("1111");
        // 校验token是否为空
        if (hldToken.getUser_id() == null) {
            throw new BusinessException(ErrorCode.USER_INFO_MISSING);
        }
// 校验token是否为空
        if (hldToken.getSession_id().equals("null")) {
            hldToken.setSession_id(null);
        }
        String mpToken = remoteUserService.getMpUserToken(hldToken);
        log.info("2222"+mpToken);
        return new BaseRsp(mpToken);

    }

    /**
     * 设置重定向返回体
     */
    private void setResponse(HttpServletResponse response, HldToken tokenContext) {
        log.info("设置重定向返回体start");
        // 校验token是否为空
        if (tokenContext.getUser_id() == null) {
            throw new BusinessException(ErrorCode.USER_INFO_MISSING);
        }

        String mpToken = remoteUserService.getMpUserToken(tokenContext);
        log.info("relocation___jwt"+mpToken);

        // 构造返回体
        response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Location", "/page/mg/");
        response.setHeader("Set-Cookie",
                "cr_jwttoken=" + mpToken + "; SameSite=Lax; Path=/");

    }

}
