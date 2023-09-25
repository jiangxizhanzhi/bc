package com.cyberpeace.cr.crbase.controller;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.controller.req.BatchDeleteReq;
import com.cyberpeace.cr.crbase.controller.req.guide.*;
import com.cyberpeace.cr.crbase.controller.rsp.guide.*;
import com.cyberpeace.cr.crbase.service.SituationGuideService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/list")
    @RequiresPermissions(value = {MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_TEAM_TRAIN}, logical = Logical.OR)
    public BaseRsp<ListRsp<SituationGuideListRsp>> list(@Validated ListGuideSchemeReq req) {
        return new BaseRsp<>(situationGuideService.listByRoomId(req));
    }

    @ApiOperation("新增导调方案")
    @PostMapping("")
    @RequiresPermissions(value = {MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_TEAM_TRAIN}, logical = Logical.OR)
    public BaseRsp<String> addGuideScheme(@RequestBody @Validated AddGuideSchemeReq req) {
        String resourceId = situationGuideService.addGuideScheme(req);
        return new BaseRsp<>(resourceId);
    }

    @ApiOperation("查看导调方案")
    @GetMapping("")
    @RequiresPermissions(value = {MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_TEAM_TRAIN}, logical = Logical.OR)
    public BaseRsp<SituationGuideRsp> getGuideSchemeById(@ApiParam(name = "resource_id", value = "导调方案编号",
            required = true) @RequestParam(value = "resource_id") String resourceId) {
        return new BaseRsp<>(situationGuideService.getGuideSchemeDetail(resourceId));
    }

    @ApiOperation("更新导调方案")
    @PatchMapping("")
    @RequiresPermissions(value = {MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_TEAM_TRAIN}, logical = Logical.OR)
    public BaseRsp<UpdateGuideSchemeReq> updateDynamicEffectScheme(@RequestBody @Validated UpdateGuideSchemeReq req) {
        UpdateGuideSchemeReq updateGuideSchemeReq = situationGuideService.updateGuideScheme(req);
        return new BaseRsp<>(updateGuideSchemeReq);
    }

    @ApiOperation("删除导调方案")
    @DeleteMapping("")
    @RequiresPermissions(value = {MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_TEAM_TRAIN}, logical = Logical.OR)
    public BaseRsp<Boolean> deleteDynamicEffectScheme(@RequestBody @Validated BatchDeleteReq req) {
        return new BaseRsp<>(situationGuideService.deleteGuideScheme(req.getIds()));
    }

    @ApiOperation("导调参数类型")
    @GetMapping("/guide_param_type")
    @RequiresPermissions(value = {MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_TEAM_TRAIN}, logical = Logical.OR)
    public BaseRsp<GuideParamTypeRsp> getParamType(@ApiParam(name = "room_id", value = "房间id", required = true) @RequestParam(value = "room_id") String roomId,
                                                   @ApiParam(name = "business_type", value = "训练类型", required = true) @RequestParam(value = "business_type") Integer businessType) {
        return new BaseRsp<>(situationGuideService.getParamType(roomId, businessType));
    }

    @ApiOperation("攻防态势模型")
    @GetMapping("/model")
    @RequiresPermissions(value = {MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_TEAM_TRAIN}, logical = Logical.OR)
    public BaseRsp<SituationModelRsp> listModel() {
        return new BaseRsp<>(situationGuideService.listModel());
    }

    @ApiOperation("执行测试")
    @PostMapping("/guide_test")
    @RequiresPermissions(value = {MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_TEAM_TRAIN}, logical = Logical.OR)
    public BaseRsp<Boolean> guideTest(@RequestBody @Validated AddGuideSchemeReq req) {
        return new BaseRsp<>(situationGuideService.guideTest(req));
    }

    @ApiOperation("执行导调")
    @PostMapping("/implement")
    @RequiresPermissions(value = {MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_TEAM_TRAIN}, logical = Logical.OR)
    public BaseRsp<Boolean> guideImplement(@RequestBody @Validated GuideImplementReq req) {
        return new BaseRsp<>(situationGuideService.guideImplement(req));
    }

    @ApiOperation("任务导调列表")
    @GetMapping("/mission_guide")
    @RequiresPermissions(value = {MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_TEAM_TRAIN}, logical = Logical.OR)
    public BaseRsp<List<GuideRoleStageRsp>> missionGuide(
            @ApiParam(name = "room_id", value = "房间id", required = true) @RequestParam(value = "room_id") String roomId,
            @ApiParam(name = "business_type", value = "训练类型", required = true) @RequestParam(value = "business_type") Integer businessType) {
        return new BaseRsp<>(situationGuideService.missionGuide(roomId, businessType));
    }

    @ApiOperation("攻击类型top5")
    @GetMapping("/attackTop5")
    @RequiresPermissions(value = {MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_TEAM_TRAIN}, logical = Logical.OR)
    public BaseRsp<List<AttackTopFiveRsp>> attackTop5(@ApiParam(name = "room_id", value = "房间id",
            required = true) @RequestParam(value = "room_id") String roomId) {
        return new BaseRsp<>(situationGuideService.attackTop5(roomId));
    }

    @ApiOperation("态势导调类型统计")
    @GetMapping("/guide_statistics")
    @RequiresPermissions(value = {MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_TEAM_TRAIN}, logical = Logical.OR)
    public BaseRsp<GuideStatisticsRsp> guideStatistics(@ApiParam(name = "room_id", value = "房间id",
            required = true) @RequestParam(value = "room_id") String roomId) {
        return new BaseRsp<>(situationGuideService.guideStatistics(roomId));
    }

    @ApiOperation("态势导调时间分布图")
    @GetMapping("/time_distribution")
    @RequiresPermissions(value = {MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_TEAM_TRAIN}, logical = Logical.OR)
    public BaseRsp<GuideTimeDistributionRsp> guideTimeDistribution(@ApiParam(name = "room_id", value = "房间id",
            required = true) @RequestParam(value = "room_id") String roomId) {
        return new BaseRsp<>(situationGuideService.guideTimeDistribution(roomId));
    }

    @ApiOperation("态势导调记录")
    @GetMapping("/guide_record")
    @RequiresPermissions(value = {MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_TEAM_TRAIN}, logical = Logical.OR)
    public BaseRsp<ListRsp<GuideHistoryRsp>> guideRecord(@Validated GuideRecordReq pageReq) {
        return new BaseRsp<>(situationGuideService.guideRecord(pageReq));
    }

}
