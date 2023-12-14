package com.cyberpeace.auth.controller;


import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.auth.security.TbUser;
import com.cyberpeace.auth.security.captcha.CheckCode;
import com.cyberpeace.common.model.BaseRsp;
import com.google.code.kaptcha.impl.DefaultKaptcha;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
@Api(value = "登录页", tags = "登录页API")
public class IndexController {

    @Resource
    private DefaultKaptcha defaultKaptcha;


    @ApiOperation("验证码")
    @GetMapping("/captcha")
    public void getVerifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 创建验证码文本
        String capText = defaultKaptcha.createText();
        // 创建验证码图片
        BufferedImage image = defaultKaptcha.createImage(capText);

        // 将验证码文本放进 Session 中
        CheckCode code = new CheckCode(capText, 180);
        request.getSession().setAttribute(KAPTCHA_SESSION_KEY, code);

        // 将验证码图片返回，禁止验证码图片缓存
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ImageIO.write(image, "jpg", response.getOutputStream());
    }


    @ApiOperation("管理员权限接口：测试1")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/demo1")
    public BaseRsp demo1() {

		TbUser currentUser = (TbUser) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
        log.info("currentUser: {}", currentUser);

        return new BaseRsp("hello world");
    }

    @ApiOperation("非管理员权限接口：测试2")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/demo2")
    public BaseRsp demo2() {

		TbUser currentUser = (TbUser) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
        log.info("currentUser: {}", currentUser);

        return new BaseRsp("hello world2");
    }
}
