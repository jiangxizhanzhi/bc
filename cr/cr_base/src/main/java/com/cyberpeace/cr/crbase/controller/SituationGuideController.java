package com.cyberpeace.cr.crbase.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.controller.req.BatchDeleteReq;
import com.cyberpeace.cr.crbase.controller.req.guide.AddGuideSchemeReq;
import com.cyberpeace.cr.crbase.controller.req.guide.GuideImplementReq;
import com.cyberpeace.cr.crbase.controller.req.guide.ListGuideSchemeReq;
import com.cyberpeace.cr.crbase.controller.req.guide.UpdateGuideSchemeReq;
import com.cyberpeace.cr.crbase.controller.rsp.guide.*;
import com.cyberpeace.cr.crbase.dao.entity.SituationGuideScheme;
import com.cyberpeace.cr.crbase.service.SituationGuideService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * @author myy
 */
@Api(tags = "态势导调")
@RestController
@Slf4j
@RequestMapping("/situation/guide")
public class SituationGuideController {

    @Autowired
    private SituationGuideService situationGuideService;

    @ApiOperation("手动导调列表")
    @PostMapping("/list")
    public BaseRsp<ListRsp<SituationGuideScheme>> list(@RequestBody @Validated ListGuideSchemeReq req) {
        return new BaseRsp<>(situationGuideService.listByRoomId(req));
    }

    @ApiOperation("新增手动导调")
    @PostMapping("")
    public BaseRsp<String> addGuideScheme(@RequestBody @Validated AddGuideSchemeReq req) {
        String resourceId = situationGuideService.addGuideScheme(req);
        return new BaseRsp<>(resourceId);
    }

    @ApiOperation("查看手动导调方案")
    @GetMapping("")
    public BaseRsp<SituationGuideRes> getGuideSchemeById(@ApiParam(name = "resource_id", value = "导调方案编号",
            required = true) @RequestParam(value = "resource_id") String resourceId) {
        return new BaseRsp<>(situationGuideService.getGuideSchemeDetail(resourceId));
    }

    @ApiOperation("更新导调方案")
    @PatchMapping("")
    public BaseRsp<UpdateGuideSchemeReq> updateDynamicEffectScheme(@RequestBody @Validated UpdateGuideSchemeReq req) {
        UpdateGuideSchemeReq updateGuideSchemeReq = situationGuideService.updateGuideScheme(req);
        return new BaseRsp<>(updateGuideSchemeReq);
    }

    @ApiOperation("删除导调方案")
    @DeleteMapping("")
    public BaseRsp<Boolean> deleteDynamicEffectScheme(@RequestBody @Validated BatchDeleteReq req) {
        return new BaseRsp<>(situationGuideService.deleteGuideScheme(req.getIds()));
    }

    @ApiOperation("导调参数类型")
    @GetMapping("/guide_param_type")
    public BaseRsp<GuideParamTypeRsp> getParamType(@ApiParam(name = "room_id", value = "房间id", required = true) @RequestParam(value = "room_id") String roomId) {
        return new BaseRsp<>(situationGuideService.getParamType(roomId));
    }

    @ApiOperation("攻防态势模型")
    @GetMapping("/model")
    public BaseRsp<SituationModelRsp> listModel() {
        SituationModelRsp situationModelRsp = new SituationModelRsp();
        List<SituationOffensiveDescRsp> attackList = new ArrayList<>();
        List<SituationOffensiveDescRsp> defenseList = new ArrayList<>();

        for (SituationGuideScheme.AttackUrlEnum modelEnum : SituationGuideScheme.AttackUrlEnum.values()) {
            Integer code = modelEnum.getCode();
            SituationOffensiveDescRsp model = new SituationOffensiveDescRsp();
            model.setCode(code);
            model.setName(modelEnum.getName());
            model.setDesc(modelEnum.getDesc());
            model.setSuccessUrl(modelEnum.getSuccessUrl());
            model.setFailUrl(modelEnum.getFailUrl());
            attackList.add(model);
        }

        for (SituationGuideScheme.DefenseUrlEnum modelEnum : SituationGuideScheme.DefenseUrlEnum.values()) {
            Integer code = modelEnum.getCode();
            SituationOffensiveDescRsp model = new SituationOffensiveDescRsp();
            model.setCode(code);
            model.setName(modelEnum.getName());
            model.setDesc(modelEnum.getDesc());
            model.setSuccessUrl(modelEnum.getSuccessUrl());
            model.setFailUrl(modelEnum.getFailUrl());
            defenseList.add(model);
        }
        situationModelRsp.setAttackList(attackList);
        situationModelRsp.setDefenseList(defenseList);
        situationModelRsp.setBlocking(SituationGuideScheme.BlockingAndBelongingUrlEnum.BLOCKING.getOneUrl());
        SituationBelongDescRsp belong = new SituationBelongDescRsp();
        belong.setName(SituationGuideScheme.BlockingAndBelongingUrlEnum.BELONGING.getName());
        belong.setRedUrl(SituationGuideScheme.BlockingAndBelongingUrlEnum.BELONGING.getOneUrl());
        belong.setBlueUrl(SituationGuideScheme.BlockingAndBelongingUrlEnum.BELONGING.getTwoUrl());
        situationModelRsp.setBelong(belong);
        return new BaseRsp<>(situationModelRsp);
    }

    @ApiOperation("执行导调")
    @PostMapping("/implement")
    public BaseRsp<Boolean> guideImplement(@RequestBody @Validated GuideImplementReq req) {
        return new BaseRsp<>(situationGuideService.guideImplement(req));
    }

    @ApiOperation("任务导调列表")
    @GetMapping("/mission_guide")
    public BaseRsp<List<GuideRoleStageRsp>> missionGuide(
            @ApiParam(name = "room_id", value = "房间id", required = true) @RequestParam(value = "room_id") String roomId,
            @ApiParam(name = "business_type", value = "训练类型", required = true) @RequestParam(value = "business_type") Integer businessType) {
        return new BaseRsp<>(situationGuideService.missionGuide(roomId, businessType));
    }

    @ApiOperation("攻击类型top5")
    @GetMapping("/attackTop5")
    public BaseRsp<List<AttackTopFiveRsp>> attackTop5(@ApiParam(name = "room_id", value = "房间id",
            required = true) @RequestParam(value = "room_id") String roomId) {
        return new BaseRsp<>(situationGuideService.attackTop5(roomId));
    }

    @ApiOperation("态势导调类型统计")
    @GetMapping("/guide_statistics")
    public BaseRsp<List<GuideStatisticsRsp>> guideStatistics(@ApiParam(name = "room_id", value = "房间id",
            required = true) @RequestParam(value = "room_id") String roomId) {
        return new BaseRsp<>(situationGuideService.guideStatistics(roomId));
    }

    @ApiOperation("态势导调时间部分图")
    @GetMapping("/time_distribution")
    public BaseRsp<GuideTimeDistributionRsp> guideTimeDistribution(@ApiParam(name = "room_id", value = "房间id",
            required = true) @RequestParam(value = "room_id") String roomId) {
        return new BaseRsp<>(situationGuideService.guideTimeDistribution(roomId));
    }


}
