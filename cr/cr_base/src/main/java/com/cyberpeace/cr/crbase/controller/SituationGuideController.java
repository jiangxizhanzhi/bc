package com.cyberpeace.cr.crbase.controller;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.controller.req.BatchDeleteReq;
import com.cyberpeace.cr.crbase.controller.req.guide.AddGuideSchemeReq;
import com.cyberpeace.cr.crbase.controller.req.guide.GuideImplementReq;
import com.cyberpeace.cr.crbase.controller.req.guide.ListGuideSchemeReq;
import com.cyberpeace.cr.crbase.controller.req.guide.UpdateGuideSchemeReq;
import com.cyberpeace.cr.crbase.controller.rsp.*;
import com.cyberpeace.cr.crbase.dao.entity.SituationGuideScheme;
import com.cyberpeace.cr.crbase.service.SituationGuideService;
import com.cyberpeace.situation.constant.SituationErrorCode;
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

    @ApiOperation("导调方案列表")
    @PostMapping("/list")
    public BaseRsp<ListRsp<SituationGuideScheme>> list(@RequestBody @Validated ListGuideSchemeReq req) {
        return new BaseRsp<>(situationGuideService.listBySceneId(req));
    }

    @ApiOperation("新增手动导调")
    @PostMapping("")
    public BaseRsp<String> addGuideEffectScheme(@RequestBody @Validated AddGuideSchemeReq req) {
        situationGuideService.validGuideSchemeName(req.getEventName(), req.getSceneId(), null);
        validGuideSchemeParam(req);
        String resourceId = situationGuideService.addGuideScheme(req);
        return new BaseRsp<>(resourceId);
    }

    @ApiOperation("查看导调方案")
    @GetMapping("")
    public BaseRsp<SituationGuideRes> getDynamicEffectScheme(@ApiParam(name = "resource_id", value = "导调方案编号",
            required = true) @RequestParam(value = "resource_id") String resourceId) {
        SituationGuideRes situationGuideRes = situationGuideService.getGuideSchemeById(resourceId);
        return new BaseRsp<>(situationGuideRes);
    }

    @ApiOperation("更新导调方案")
    @PatchMapping("")
    public BaseRsp<UpdateGuideSchemeReq> updateDynamicEffectScheme(@RequestBody @Validated UpdateGuideSchemeReq req) {
        situationGuideService.validGuideSchemeName(req.getEventName(), req.getSceneId(), req.getResourceId());
        validGuideSchemeParam(req);
        UpdateGuideSchemeReq updateGuideSchemeReq = situationGuideService.updateGuideScheme(req);
        return new BaseRsp<>(updateGuideSchemeReq);
    }

    @ApiOperation("删除导调方案")
    @DeleteMapping("")
    public BaseRsp<Boolean> deleteDynamicEffectScheme(@RequestBody @Validated BatchDeleteReq req) {
        situationGuideService.deleteGuideScheme(req.getIds());
        return new BaseRsp<>(true);
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
        situationModelRsp.setBlocking(SituationGuideScheme.BlockingAndBelongingUrlEnum.EXCITATION.getOneUrl());
        SituationBelongDescRsp belong= new SituationBelongDescRsp();
        belong.setName(SituationGuideScheme.BlockingAndBelongingUrlEnum.ANALYZE.getName());
        belong.setRedUrl(SituationGuideScheme.BlockingAndBelongingUrlEnum.ANALYZE.getOneUrl());
        belong.setBlueUrl(SituationGuideScheme.BlockingAndBelongingUrlEnum.ANALYZE.getTwoUrl());
        situationModelRsp.setBelong(belong);
        return new BaseRsp<>(situationModelRsp);
    }

    @ApiOperation("执行导调")
    @GetMapping("/implement")
    public BaseRsp<Boolean> guideImplement(@RequestBody @Validated GuideImplementReq req) {
        situationGuideService.guideImplement(req);
        return new BaseRsp<>(true);
    }

    @ApiOperation("攻击类型top5")
    @GetMapping("/attackTop5")
    public BaseRsp<AttackTopFiveRsp> attackTop5(@ApiParam(name = "scene_id", value = "场景id",
            required = true) @RequestParam(value = "scene_id") String sceneId) {
        return new BaseRsp<>(situationGuideService.attackTop5(sceneId));
    }


    private void validGuideSchemeParam(AddGuideSchemeReq req) {
        SituationGuideScheme.EventType eventType = SituationGuideScheme.EventType.getEventTypeByCode(req.getEventType());
        boolean missionFlag = SituationGuideScheme.Type.MISSION.getCode().equals(req.getType()) && req.getMissionId() == null;
        boolean flag;
        switch (eventType) {
            case ATTACK:
            case DEFENSE:
                flag = req.getCategory() == null || req.getSource() == null || req.getTarget() == null || req.getResult() == null;
                if (flag || missionFlag) {
                    throw new BusinessException(SituationErrorCode.INVALID_GUIDE_SCHEME);
                }
                break;
            case BLOCKING:
                flag = req.getSource() == null || req.getTarget() == null;
                if (flag || missionFlag) {
                    throw new BusinessException(SituationErrorCode.INVALID_GUIDE_SCHEME);
                }
                break;
            case BELONGING:
                flag = req.getSource() == null;
                if (flag || missionFlag) {
                    throw new BusinessException(SituationErrorCode.INVALID_GUIDE_SCHEME);
                }
                break;
            default:
                throw new BusinessException(SituationErrorCode.INVALID_GUIDE_SCHEME);
        }
    }

}
