package com.cyberpeace.cr.crbase.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.crbase.controller.req.BatchDeleteReq;
import com.cyberpeace.cr.crbase.controller.req.guide.AddGuideSchemeReq;
import com.cyberpeace.cr.crbase.controller.req.guide.GuideSchemeReq;
import com.cyberpeace.cr.crbase.controller.rsp.SituationGuideRes;
import com.cyberpeace.cr.crbase.service.GuideSchemeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Api(tags = "手动导调")
@RestController
@Slf4j
@RequestMapping("/situation/guide")
public class SituationGuideController {

    @Autowired
    private GuideSchemeService guideSchemeService;

    @ApiOperation("新增手动导调")
    @PostMapping("")
    public BaseRsp<String> addGuideEffectScheme(@RequestBody @Validated AddGuideSchemeReq req) {
        guideSchemeService.validGuideSchemeName(req.getName(), req.getSceneId(), null);
        String resourceId = guideSchemeService.addGuideScheme(req);
        return new BaseRsp<>(resourceId);
    }


    @ApiOperation("查看导调方案")
    @GetMapping("")
    public BaseRsp<SituationGuideRes> getDynamicEffectScheme(@ApiParam(name = "resource_id", value = "动效方案编号",
            required = true) @RequestParam(value = "resource_id") String resourceId) {
        SituationGuideRes situationGuideRes = guideSchemeService.getGuideSchemeById(resourceId);
        return new BaseRsp<>(situationGuideRes);
    }

    @ApiOperation("更新导调方案")
    @PatchMapping("")
    public BaseRsp<GuideSchemeReq> updateDynamicEffectScheme(@RequestBody @Validated GuideSchemeReq req) {
        guideSchemeService.validGuideSchemeName(req.getName(), req.getSceneId(), req.getResourceId());
        GuideSchemeReq guideSchemeReq = guideSchemeService.updateGuideScheme(req);
        return new BaseRsp<>(guideSchemeReq);
    }

    @ApiOperation("删除导调方案")
    @DeleteMapping("")
    public BaseRsp<Boolean> deleteDynamicEffectScheme(@RequestBody @Validated BatchDeleteReq req) {
        guideSchemeService.deleteGuideScheme(req.getIds());
        return new BaseRsp<>(true);
    }

}
