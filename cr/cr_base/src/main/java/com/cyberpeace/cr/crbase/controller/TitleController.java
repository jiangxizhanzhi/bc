package com.cyberpeace.cr.crbase.controller;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.HtmlTemplateEnum;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.HtmlToPdfUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.req.crtitle.CrUserTitleReq;
import com.cyberpeace.cr.crbase.dao.entity.CrTitle;
import com.cyberpeace.cr.crbase.dao.entity.CrTitleLevel;
import com.cyberpeace.cr.crbase.service.CrTitleService;
import com.cyberpeace.cr.crbase.service.impl.bo.*;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Api(tags = "前台-荣誉称号")
@Slf4j
@RequestMapping("/cr/title")
@RestController
@RequiresAuthentication
public class TitleController {
    @Autowired
    private CrTitleService crTitleService;

    Cache<String, String> cache = CacheBuilder.newBuilder().expireAfterAccess(4, TimeUnit.SECONDS)
            .build();

    @GetMapping("/level/list")
    @ApiOperation("称号等级获取")
    public BaseRsp<List<CrTitleLevel>> levelList(@RequestParam(value = "title", required = false) String title) {
        return new BaseRsp<>(crTitleService.levelFullList(title));
    }

    @GetMapping("/list")
    @ApiOperation("职业名称全量查询")
    public BaseRsp<List<CrTitle>> titleList(@RequestParam(value = "level_id", required = false) String levelId) {
        return new BaseRsp<>(crTitleService.titleFullList(levelId));
    }

    @GetMapping("/user")
    @ApiOperation("当前用户已获得称号")
    public BaseRsp<ListRsp<CrTitleBo>> userTitle(CrUserTitleReq queryParams) {
        return new BaseRsp<>(PageUtil.getListResult(
                crTitleService.userTitle(queryParams.toPageParam(), queryParams.getLevelId(), queryParams.getTitle())
        ));
    }

    @GetMapping("/standard")
    @ApiOperation("评级标准查询")
    public BaseRsp<ListRsp<CrTitleStandardBo>> standardSearch(CrUserTitleReq queryParams) {
        return new BaseRsp<>(PageUtil.getListResult(
                crTitleService.titleStandard(queryParams.toPageParam(), queryParams.getLevelId(), queryParams.getTitle())
        ));
    }

    @GetMapping("/detail")
    @ApiOperation("称号基础信息")
    public BaseRsp<CrTitleDetailBo> titleDetail(CrUserTitleReq queryParams) {
        return new BaseRsp<>(crTitleService.titleDetail(queryParams.getLevelId(), queryParams.getTitle()));
    }

    @GetMapping("/skill-radar")
    @ApiOperation("技能树分值")
    public BaseRsp<CrTitleSkillRadarBo> skillRadar() {
        return new BaseRsp<>(crTitleService.userSkillRadar());
    }

    @GetMapping("/cert")
    @ApiModelProperty("证书下载")
    public void dumpCert(HttpServletResponse httpServletResponse, @RequestParam("title_id") String titleId) {
        CrTitleCertBo crTitleCertBo = crTitleService.getTitleCertInfo(titleId);
        if (crTitleCertBo == null) {
            throw new BusinessException(CrBusinessErrorCode.TITLE_NOT_OWNED);
        }
        Path htmlPath = Paths.get(
                AttachmentPath.TITLE_CERT_TEMPLATE.getDesc(),
                crTitleCertBo.getTitleId(),
                crTitleCertBo.getUserId(),
                String.format("%s%s.html", crTitleCertBo.getLevel(), crTitleCertBo.getTitle())
        );
        String htmlPathString = htmlPath.toString();
        String pdfPathString = htmlPathString.substring(0, htmlPathString.lastIndexOf("html")) + "pdf";

        // 证书已生成, 返回
        if (new File(pdfPathString).exists()) {
            FileUtil.setResponse(new File(pdfPathString), false, httpServletResponse);
            return;
        }

        // 证书生成防抖
        String key = "TITLE-CERT_" + CommonContext.getIp();
        if (cache.getIfPresent(key) != null) {
            throw new BusinessException(ErrorCode.OPERATION_TOO_FREQUENT);
        }
        cache.put(key, "whatever");

        // 检查/创建上层目录
        File directory = htmlPath.getParent().toFile();
        if (!directory.exists()) {
            boolean isCreated = directory.mkdirs();
            if (!isCreated) {
                throw new BusinessException(ErrorCode.FAILED_TO_MAKE_DIR, directory.getPath());
            }
        }

        try {
            // 生成文件
            HtmlToPdfUtil.getHtmlTemplateInstance(crTitleCertBo, HtmlTemplateEnum.TITLE, htmlPathString);
            HtmlToPdfUtil.convert(htmlPathString, pdfPathString);
            FileUtil.setResponse(new File(pdfPathString), false, httpServletResponse);
        } catch (Exception e) {
            log.error("failed to export pdf: {}", htmlPathString);
            throw e;
        } finally {
            // 删除生成的html文件
            File originHtml = new File(htmlPathString);
            if (!FileUtil.deleteDir(originHtml)) {
                log.error("failed to origin html: {}", originHtml);
            }
        }
    }
}
