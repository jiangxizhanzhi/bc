package com.cyberpeace.situation.controller;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.situation.constant.ws.AttackType;
import com.cyberpeace.situation.controller.req.*;
import com.cyberpeace.situation.controller.res.DynamicSchemeNameRes;
import com.cyberpeace.situation.controller.res.DynamicSchemeRes;
import com.cyberpeace.situation.controller.res.OptionsRes;
import com.cyberpeace.situation.service.AttackMessageService;
import com.cyberpeace.situation.service.DynamicSchemeService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;


@Api(tags = "态势编排")
@RestController
@RequestMapping("/situation/control")
public class SituationControlController {

    @Autowired
    private DynamicSchemeService dynamicSchemeService;

    @Autowired
    private AttackMessageService attackMessageService;

    @ApiOperation("保存动效方案")
    @PostMapping("/scheme")
    public BaseRsp<String> addDynamicEffectScheme(@RequestBody @Validated DynamicSchemeReq dynamicSchemeReq){
        dynamicSchemeService.addDynamicScheme(dynamicSchemeReq);
        return new BaseRsp<>();
    }

    @ApiOperation("查看动效方案")
    @GetMapping("/scheme")
    public BaseRsp<DynamicSchemeRes> getDynamicEffectScheme(@ApiParam("resourceId")String dynamicSchemeId){
        DynamicSchemeRes dynamicSchemeRes = dynamicSchemeService.getDynamicSchemeById(dynamicSchemeId);
        return new BaseRsp<>(dynamicSchemeRes);
    }

    @ApiOperation("更新动效方案")
    @PatchMapping("/scheme")
    public BaseRsp<String> updateDynamicEffectScheme(@RequestBody @Validated UpdateDynamicSchemeReq dynamicSchemeReq){
        dynamicSchemeService.updateDynamicScheme(dynamicSchemeReq);
        return new BaseRsp<>();
    }

    @ApiOperation("删除动效方案")
    @DeleteMapping("/scheme")
    public BaseRsp<?> deleteDynamicEffectScheme(@ApiParam("resourceId")String dynamicSchemeId){
        dynamicSchemeService.deleteDynamicScheme(dynamicSchemeId);
        return new BaseRsp<>();
    }

    @ApiOperation("动效方案名称")
    @GetMapping("/schemeNames")
    public BaseRsp<List<DynamicSchemeNameRes>> getAllDynamicName(){
        List<DynamicSchemeNameRes> names = dynamicSchemeService.getAllDynamicName();
        return new BaseRsp<>(names);
    }

    @ApiOperation("保存单条动效")
    @PostMapping("/attackMessage")
    public BaseRsp<String> addAttackMessage(@RequestBody @Validated AttackMessageReq attackMessageReq){
        String resourceId = attackMessageService.addAttackMessage(attackMessageReq);
        return new BaseRsp<>(resourceId);
    }

    @ApiOperation("更新单条动效")
    @PatchMapping("/attackMessage")
    public BaseRsp<?> updateAttackMessage(@RequestBody @Validated UpdateAttackMessageReq updateAttackMessageReq){
        attackMessageService.updateAttackMessage(updateAttackMessageReq);
        return new BaseRsp<>();
    }




}
