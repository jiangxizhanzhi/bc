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
 * 态势导调Controller
 *
 * @author myy@date 2024/11/12
 */
@Api(tags = "态势导调")
@RestController
@Slf4j
@RequestMapping("/situation/guide")
public class SituationGuideController {

    /**
     * 态势导调Service
     */
    @Autowired
    private SituationGuideService situationGuideService;

    /**
     * 列表
     *
     * @param req 请求体
     * @return {@code BaseRsp<ListRsp<SituationGuideListRsp>> }
     */
    @ApiOperation("手动导调列表")
    @GetMapping("/list")
    @RequiresPermissions(value = {MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_TEAM_TRAIN}, logical = Logical.OR)
    public BaseRsp<ListRsp<SituationGuideListRsp>> list(@Validated ListGuideSchemeReq req) {
        return new BaseRsp<>(situationGuideService.listByRoomId(req));
    }

    /**
     * 新增导调方案
     *
     * @param req 请求体
     * @return {@code BaseRsp<String> }
     */
    @ApiOperation("新增导调方案")
    @PostMapping("")
    @RequiresPermissions(value = {MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_TEAM_TRAIN}, logical = Logical.OR)
    public BaseRsp<String> addGuideScheme(@RequestBody @Validated AddGuideSchemeReq req) {
        String resourceId = situationGuideService.addGuideScheme(req);
        return new BaseRsp<>(resourceId);
    }

    /**
     * 获取导调方案由id
     *
     * @param resourceId 资源id
     * @return {@code BaseRsp<SituationGuideRsp> }
     */
    @ApiOperation("查看导调方案")
    @GetMapping("")
    @RequiresPermissions(value = {MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_TEAM_TRAIN}, logical = Logical.OR)
    public BaseRsp<SituationGuideRsp> getGuideSchemeById(@ApiParam(name = "resource_id", value = "导调方案编号",
            required = true) @RequestParam(value = "resource_id") String resourceId) {
        return new BaseRsp<>(situationGuideService.getGuideSchemeDetail(resourceId));
    }

    /**
     * 更新动态动效方案
     *
     * @param req 请求体
     * @return {@code BaseRsp<UpdateGuideSchemeReq> }
     */
    @ApiOperation("更新导调方案")
    @PatchMapping("")
    @RequiresPermissions(value = {MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_TEAM_TRAIN}, logical = Logical.OR)
    public BaseRsp<UpdateGuideSchemeReq> updateDynamicEffectScheme(@RequestBody @Validated UpdateGuideSchemeReq req) {
        UpdateGuideSchemeReq updateGuideSchemeReq = situationGuideService.updateGuideScheme(req);
        return new BaseRsp<>(updateGuideSchemeReq);
    }

    /**
     * 删除动态动效方案
     *
     * @param req 请求体
     * @return {@code BaseRsp<Boolean> }
     */
    @ApiOperation("删除导调方案")
    @DeleteMapping("")
    @RequiresPermissions(value = {MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_TEAM_TRAIN}, logical = Logical.OR)
    public BaseRsp<Boolean> deleteDynamicEffectScheme(@RequestBody @Validated BatchDeleteReq req) {
        return new BaseRsp<>(situationGuideService.deleteGuideScheme(req.getIds()));
    }

    /**
     * 获取参数类型
     *
     * @param roomId       房间id
     * @param businessType 业务类型
     * @return {@code BaseRsp<GuideParamTypeRsp> }
     */
    @ApiOperation("导调参数类型")
    @GetMapping("/guide_param_type")
    @RequiresPermissions(value = {MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_TEAM_TRAIN}, logical = Logical.OR)
    public BaseRsp<GuideParamTypeRsp> getParamType(@ApiParam(name = "room_id", value = "房间id", required = true) @RequestParam(value = "room_id") String roomId,
                                                   @ApiParam(name = "business_type", value = "训练类型", required = true) @RequestParam(value = "business_type") Integer businessType) {
        return new BaseRsp<>(situationGuideService.getParamType(roomId, businessType));
    }

    /**
     * 列表模型
     *
     * @return {@code BaseRsp<SituationModelRsp> }
     */
    @ApiOperation("攻防态势模型")
    @GetMapping("/model")
    @RequiresPermissions(value = {MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_TEAM_TRAIN}, logical = Logical.OR)
    public BaseRsp<SituationModelRsp> listModel() {
        return new BaseRsp<>(situationGuideService.listModel());
    }

    /**
     * 导调测试
     *
     * @param req 请求体
     * @return {@code BaseRsp<Boolean> }
     */
    @ApiOperation("执行测试")
    @PostMapping("/guide_test")
    @RequiresPermissions(value = {MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_TEAM_TRAIN}, logical = Logical.OR)
    public BaseRsp<Boolean> guideTest(@RequestBody @Validated AddGuideSchemeReq req) {
        return new BaseRsp<>(situationGuideService.guideTest(req));
    }

    /**
     * 导调实现
     *
     * @param req 请求体
     * @return {@code BaseRsp<Boolean> }
     */
    @ApiOperation("执行导调")
    @PostMapping("/implement")
    @RequiresPermissions(value = {MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_TEAM_TRAIN}, logical = Logical.OR)
    public BaseRsp<Boolean> guideImplement(@RequestBody @Validated GuideImplementReq req) {
        return new BaseRsp<>(situationGuideService.guideImplement(req));
    }

    /**
     * 任务导调
     *
     * @param roomId       房间id
     * @param businessType 业务类型
     * @return {@code BaseRsp<List<GuideRoleStageRsp>> }
     */
    @ApiOperation("任务导调列表")
    @GetMapping("/mission_guide")
    @RequiresPermissions(value = {MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_TEAM_TRAIN}, logical = Logical.OR)
    public BaseRsp<List<GuideRoleStageRsp>> missionGuide(
            @ApiParam(name = "room_id", value = "房间id", required = true) @RequestParam(value = "room_id") String roomId,
            @ApiParam(name = "business_type", value = "训练类型", required = true) @RequestParam(value = "business_type") Integer businessType) {
        return new BaseRsp<>(situationGuideService.missionGuide(roomId, businessType));
    }

    /**
     * 攻击 top5
     *
     * @param roomId 房间id
     * @return {@code BaseRsp<List<AttackTopFiveRsp>> }
     */
    @ApiOperation("攻击类型top5")
    @GetMapping("/attackTop5")
    @RequiresPermissions(value = {MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_TEAM_TRAIN}, logical = Logical.OR)
    public BaseRsp<List<AttackTopFiveRsp>> attackTop5(@ApiParam(name = "room_id", value = "房间id",
            required = true) @RequestParam(value = "room_id") String roomId) {
        return new BaseRsp<>(situationGuideService.attackTop5(roomId));
    }

    /**
     * 导调统计学
     *
     * @param roomId 房间id
     * @return {@code BaseRsp<GuideStatisticsRsp> }
     */
    @ApiOperation("态势导调类型统计")
    @GetMapping("/guide_statistics")
    @RequiresPermissions(value = {MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_TEAM_TRAIN}, logical = Logical.OR)
    public BaseRsp<GuideStatisticsRsp> guideStatistics(@ApiParam(name = "room_id", value = "房间id",
            required = true) @RequestParam(value = "room_id") String roomId) {
        return new BaseRsp<>(situationGuideService.guideStatistics(roomId));
    }

    /**
     * 导调时间分配
     *
     * @param roomId 房间id
     * @return {@code BaseRsp<GuideTimeDistributionRsp> }
     */
    @ApiOperation("态势导调时间分布图")
    @GetMapping("/time_distribution")
    @RequiresPermissions(value = {MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_TEAM_TRAIN}, logical = Logical.OR)
    public BaseRsp<GuideTimeDistributionRsp> guideTimeDistribution(@ApiParam(name = "room_id", value = "房间id",
            required = true) @RequestParam(value = "room_id") String roomId) {
        return new BaseRsp<>(situationGuideService.guideTimeDistribution(roomId));
    }

    /**
     * 导调记录
     *
     * @param pageReq 分页请求体
     * @return {@code BaseRsp<ListRsp<GuideHistoryRsp>> }
     */
    @ApiOperation("态势导调记录")
    @GetMapping("/guide_record")
    @RequiresPermissions(value = {MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_TEAM_TRAIN}, logical = Logical.OR)
    public BaseRsp<ListRsp<GuideHistoryRsp>> guideRecord(@Validated GuideRecordReq pageReq) {
        return new BaseRsp<>(situationGuideService.guideRecord(pageReq));
    }

}
