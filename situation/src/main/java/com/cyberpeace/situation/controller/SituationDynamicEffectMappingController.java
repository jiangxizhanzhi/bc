package com.cyberpeace.situation.controller;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.situation.controller.req.BatchDeleteReq;
import com.cyberpeace.situation.controller.req.DynamicEffectMappingPageReq;
import com.cyberpeace.situation.controller.req.DynamicEffectMappingReq;
import com.cyberpeace.situation.controller.req.DynamicEffectMappingUpdateReq;
import com.cyberpeace.situation.dao.entity.DynamicEffectsMapping;
import com.cyberpeace.situation.service.SituationDynamicEffectMappingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * @Author xuxz
 * @Date 26/11/2022 下午3:36
 */
@Api(tags = "组件映射-动效映射")
@RestController
@Slf4j
@RequiresAuthentication
@RequestMapping("/situation/dynamic/effect/mapping")
public class SituationDynamicEffectMappingController {


    @Autowired
    private SituationDynamicEffectMappingService situationDynamicEffectMappingService;

    @ApiOperation("新增动效映射")
    @PostMapping("")
    public BaseRsp<String>
    setDynamicEffectsMapping(@RequestBody @Validated(Create.class) DynamicEffectMappingReq dynamicEffectMappingReq) {

        return new BaseRsp<>(situationDynamicEffectMappingService.setDynamicEffectsMapping(dynamicEffectMappingReq));
    }

    @ApiOperation("查询动效映射")
    @GetMapping("/list")
    public BaseRsp<ListRsp<DynamicEffectsMapping>>
    getDynamicEffectsMapping(DynamicEffectMappingPageReq dynamicEffectMappingFilterReq) {

        return new BaseRsp<>(situationDynamicEffectMappingService.getDynamicEffectsMapping(dynamicEffectMappingFilterReq));
    }

    @ApiOperation("查询动效映射详情")
    @GetMapping("/")
    public BaseRsp<DynamicEffectsMapping>
    getDynamicEffectsMappingDetail(@RequestParam("resourceId") @NotBlank String resourceId) {

        return new BaseRsp<>(situationDynamicEffectMappingService.getDynamicEffectsMappingDetail(resourceId));
    }

    @ApiOperation("删除动效映射")
    @DeleteMapping("")
    public BaseRsp<Integer> deleteDynamicEffectsMapping(@RequestBody @Validated BatchDeleteReq batchDeleteReq) {

        return new BaseRsp<>(situationDynamicEffectMappingService.deleteDynamicEffectsMapping(batchDeleteReq));
    }

    @ApiOperation("修改动效映射")
    @PatchMapping("")
    public BaseRsp<String> patchDynamicEffectsMapping(@Validated(Update.class) DynamicEffectMappingUpdateReq dynamicEffectMappingUpdateReq) {

        return new BaseRsp<>(situationDynamicEffectMappingService.patchDynamicEffectsMapping(dynamicEffectMappingUpdateReq));
    }

}
