package com.cyberpeace.cr.crbase.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.enums.BusinessType;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.controller.req.guide.AddGuideSchemeReq;
import com.cyberpeace.cr.crbase.controller.req.guide.GuideImplementReq;
import com.cyberpeace.cr.crbase.controller.req.guide.ListGuideSchemeReq;
import com.cyberpeace.cr.crbase.controller.req.guide.UpdateGuideSchemeReq;
import com.cyberpeace.cr.crbase.controller.rsp.guide.*;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.dao.entity.SituationGuideHistory;
import com.cyberpeace.cr.crbase.dao.entity.SituationGuideScheme;
import com.cyberpeace.cr.crbase.dao.mapper.MissionMapper;
import com.cyberpeace.cr.crbase.dao.mapper.SituationGuideHisMapper;
import com.cyberpeace.cr.crbase.dao.mapper.SituationGuideSchemeMapper;
import com.cyberpeace.cr.crbase.service.SituationGuideService;
import com.cyberpeace.cr.crbase.service.WsMultiNodeGuide;
import com.cyberpeace.cr.crbase.service.context.guide.SituationGuideMissionBo;
import com.cyberpeace.cr.crbase.service.context.guide.SituationRoomBo;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfig;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigMission;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigSchemeStage;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.constant.ws.MessageType;
import com.cyberpeace.situation.model.ElementInfo;
import com.cyberpeace.situation.model.SceneData;
import com.cyberpeace.situation.model.SituationTopo;
import com.cyberpeace.situation.model.ws.WsMessage;
import com.cyberpeace.situation.service.SituationHandlerService;
import com.cyberpeace.situation.service.TopoHandlerService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SituationGuideServiceImpl extends ServiceImpl<SituationGuideSchemeMapper, SituationGuideScheme> implements SituationGuideService {

    @Autowired
    private SituationGuideHisMapper situationGuideHisMapper;

    @Autowired
    private SituationGuideSchemeMapper situationGuideSchemeMapper;

    @Autowired
    private MissionMapper missionMapper;

    @Autowired
    private TopoHandlerService topoHandlerService;

    @Autowired
    private LocalSceneService localSceneService;

    @Autowired
    private SituationHandlerService situationHandlerService;

    public Integer MIN_STR_LEN = 1;

    public Integer MAX_NAME_STR_LEN = 255;

    @Override
    public ListRsp<SituationGuideScheme> listByRoomId(ListGuideSchemeReq req) {
        LambdaQueryWrapper<SituationGuideScheme> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SituationGuideScheme::getRoomId, req.getRoomId());
        Page<SituationGuideScheme> page = page(req.toPageParam(), queryWrapper);
        return PageUtil.getListResult(page);
    }

    @Override
    public String addGuideScheme(AddGuideSchemeReq addGuideSchemeReq) {
        SituationGuideScheme situationGuideScheme = BeanUtils.copy(addGuideSchemeReq, SituationGuideScheme.class);
        situationGuideScheme.setResourceId(IDGeneratorUtil.getID());
        save(situationGuideScheme);
        return situationGuideScheme.getResourceId();
    }

    @Override
    public SituationGuideRes getGuideSchemeById(String resourceId) {
        SituationGuideScheme situationGuideScheme = getById(resourceId);
        if (situationGuideScheme == null) {
            log.error("get dynamic scheme by id ({}) error, dynamic scheme not found", resourceId);
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        SituationGuideRes resp = BeanUtils.copy(situationGuideScheme, SituationGuideRes.class);
        SituationGuideScheme.EventType eventType = SituationGuideScheme.EventType.getEventTypeByCode(resp.getEventType());

        switch (eventType) {
            case ATTACK:
                SituationGuideScheme.AttackUrlEnum attackUrlEnum = SituationGuideScheme.AttackUrlEnum.getEnumByCode(resp.getCategory());
                resp.setSuccessUrl(attackUrlEnum.getSuccessUrl());
                resp.setFailUrl(attackUrlEnum.getFailUrl());
                break;
            case DEFENSE:
                SituationGuideScheme.DefenseUrlEnum defenseUrlEnum = SituationGuideScheme.DefenseUrlEnum.getEnumByCode(resp.getCategory());
                resp.setSuccessUrl(defenseUrlEnum.getSuccessUrl());
                resp.setFailUrl(defenseUrlEnum.getFailUrl());
            case BLOCKING:
                String oneUrl = SituationGuideScheme.BlockingAndBelongingUrlEnum.BLOCKING.getOneUrl();
                resp.setSuccessUrl(oneUrl);
                break;
            case BELONGING:
                SituationGuideScheme.BlockingAndBelongingUrlEnum belongingUrlEnum = SituationGuideScheme.BlockingAndBelongingUrlEnum.BELONGING;
                resp.setRedUrl(belongingUrlEnum.getOneUrl());
                resp.setBlueUrl(belongingUrlEnum.getTwoUrl());
                break;
            default:
        }
        return resp;
    }

    @Override
    public UpdateGuideSchemeReq updateGuideScheme(UpdateGuideSchemeReq updateGuideSchemeReq) {
        SituationGuideScheme situationGuideScheme = BeanUtils.copy(updateGuideSchemeReq, SituationGuideScheme.class);
        updateById(situationGuideScheme);
        return updateGuideSchemeReq;
    }

    @Override
    public void deleteGuideScheme(List<String> ids) {
        if (!CollectionUtils.isEmpty(ids)) {
            removeByIds(ids);
        }
    }

    /**
     * 校验导调方案是否重名
     */
    @Override
    public void validGuideSchemeName(String name, String roomId, String resourceId) {
        validateNameLen(name, SituationErrorCode.INVALID_GUIDE_SCHEME_NAME);
        LambdaQueryWrapper<SituationGuideScheme> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SituationGuideScheme::getEventName, name);
        queryWrapper.eq(SituationGuideScheme::getRoomId, roomId);
        if (StringUtils.isNotBlank(resourceId)) {
            queryWrapper.ne(SituationGuideScheme::getResourceId, resourceId);
        }
        List<SituationGuideScheme> situationGuideSchemes = list(queryWrapper);
        if (!CollectionUtils.isEmpty(situationGuideSchemes)) {
            log.error("dynamic scheme name is duplicated, name:{}", name);
            throw new BusinessException(SituationErrorCode.DUPLICATED_GUIDE_SCHEME_NAME);
        }
    }

    @Override
    public void guideImplement(GuideImplementReq req) {
        SituationGuideScheme guideScheme = getById(req.getResourceId());

        // todo 根据roomId获取sceneId,只查不处理脏数据
        String sceneId = viewScene(guideScheme.getRoomId(), guideScheme.getBusinessType());

        //增加历史记录
        SituationGuideHistory his = BeanUtils.copy(guideScheme, SituationGuideHistory.class);
        his.setResourceId(IDGeneratorUtil.getID());
        his.setCreateTime(new Date());
        his.setUpdateTime(new Date());
        his.setSceneId(sceneId);
        situationGuideHisMapper.insert(his);

        pushDataToRoomByGuide(req.getIsInstance(), his);
    }


    @Override
    public SituationGuideHistory getGuideHisByMissionId(String missionId, String taskId) {
        LambdaQueryWrapper<SituationGuideScheme> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SituationGuideScheme::getMissionId, missionId);
        List<SituationGuideScheme> situationGuideSchemes = situationGuideSchemeMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(situationGuideSchemes)) {
            return null;
        }

        SituationGuideHistory guideHis = BeanUtils.copy(situationGuideSchemes.get(0), SituationGuideHistory.class);
        // todo 根据roomId获取sceneId, 只查不处理脏数据
        String sceneId = viewScene(guideHis.getRoomId(), guideHis.getBusinessType());
        guideHis.setSceneId(sceneId);
        guideHis.setResourceId(IDGeneratorUtil.getID());
        guideHis.setTaskId(taskId);
        guideHis.setCreateTime(new Date());
        guideHis.setUpdateTime(new Date());
        return guideHis;
    }

    @Override
    public void pushDataToRoomByGuide(Boolean isInstance, SituationGuideHistory his) {
        if (Objects.isNull(his) || StringUtils.isBlank(his.getSceneId())) {
            return;
        }
        String sceneId = his.getSceneId();
        List<String> elementIds = getElementIds(sceneId, isInstance);
        WsMultiNodeGuide wsMultiNodeGuide = new WsMultiNodeGuide();
        wsMultiNodeGuide.setSceneId(his.getSceneId());
        wsMultiNodeGuide.setDuration(3);
        wsMultiNodeGuide.setName(his.getEventName());
        wsMultiNodeGuide.setCategory(his.getCategory());
        wsMultiNodeGuide.setEventType(his.getEventType());
        wsMultiNodeGuide.setSourceId(his.getSource());
        wsMultiNodeGuide.setTargetId(his.getTarget());
        wsMultiNodeGuide.setResult(his.getResult());
        wsMultiNodeGuide.setRole(his.getRole());

        WsMessage<WsMultiNodeGuide> wsMessage;
        if (elementIds.contains(his.getTarget())) {
            wsMessage = new WsMessage<>(MessageType.AttackEventEE.getDesc(), wsMultiNodeGuide);
        } else {
            wsMessage = new WsMessage<>(MessageType.AttackEventEA.getDesc(), wsMultiNodeGuide);
        }
        situationHandlerService.pushDataToRoom(wsMessage, wsMultiNodeGuide.getSceneId());
    }

    @Override
    public List<AttackTopFiveRsp> attackTop5(String roomId) {
        //todo 临时数据,便于前端画图
        ArrayList<AttackTopFiveRsp> result = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            AttackTopFiveRsp attackTopFiveRsp = new AttackTopFiveRsp();
            attackTopFiveRsp.setCategory(i);
            attackTopFiveRsp.setCount(i);
            result.add(attackTopFiveRsp);
        }
        return result;
    }

    @Override
    public List<GuideRoleStageRsp> missionGuide(String roomId, Integer businessType) {
        SituationGuideMissionBo situationGuideMissionBo = situationGuideSchemeMapper.selectRoleConfigByBusinessType(roomId, businessType);
        if (Objects.isNull(situationGuideMissionBo) || StringUtils.isBlank(situationGuideMissionBo.getRoleConfig())) {
            return null;
        }
        List<CrRoleConfig> crRoleConfigs = new ArrayList<>();
        if (BusinessType.TRAINING_TEAM.getCode().equals(businessType)) {
            crRoleConfigs = JsonUtil.toJsonObjectArray(situationGuideMissionBo.getRoleConfig(), CrRoleConfig.class);
        } else {
            JSONObject roleObject = JsonUtil.toJsonObject(situationGuideMissionBo.getRoleConfig(), JSONObject.class);
            List<CrRoleConfig> redRoleResp = JsonUtil.toJsonObjectArray(roleObject.get("red_roles").toString(), CrRoleConfig.class);
            List<CrRoleConfig> blueRoleResp = JsonUtil.toJsonObjectArray(roleObject.get("blue_roles").toString(), CrRoleConfig.class);
            crRoleConfigs.addAll(redRoleResp);
            crRoleConfigs.addAll(blueRoleResp);
        }

        Map<String, Mission> missionMap = StreamApiUtils.toMap(missionMapper.selectByRoomId(roomId), Mission::getMissionTemplateId, item -> item);
        List<GuideRoleStageRsp> result = new ArrayList<>();
        for (CrRoleConfig crRoleConfig : crRoleConfigs) {
            GuideRoleStageRsp guideRoleStageRsp = new GuideRoleStageRsp();
            guideRoleStageRsp.setRoleName(crRoleConfig.getName());
            List<GuideMissionStageRsp> stageList = new ArrayList<>();
            for (CrRoleConfigSchemeStage schemeDatum : crRoleConfig.getMissionsScheme().getSchemeData()) {
                GuideMissionStageRsp missionStageRsp = new GuideMissionStageRsp();
                missionStageRsp.setStageName(schemeDatum.getStage());
                List<GuideMissionRsp> missionList = new ArrayList<>();
                for (CrRoleConfigMission missionTemplate : schemeDatum.getMissions()) {
                    if (missionMap.containsKey(missionTemplate.getResourceId())) {
                        GuideMissionRsp guideMissionRsp = new GuideMissionRsp();
                        Mission mission = missionMap.get(missionTemplate.getResourceId());
                        guideMissionRsp.setMissionTitle(mission.getTitle());
                        guideMissionRsp.setMissionId(mission.getResourceId());
                        missionList.add(guideMissionRsp);
                    }
                }
                //todo 模版的阶段任务修改是否展示
//                if (CollectionUtils.isEmpty(missionList)){
//                    continue;
//                }
                missionStageRsp.setMissionList(missionList);
                stageList.add(missionStageRsp);
            }
            guideRoleStageRsp.setStageList(stageList);
            result.add(guideRoleStageRsp);
        }
        return result;
    }

    @Override
    public List<GuideStatisticsRsp> guideStatistics(String roomId) {

        //todo 临时数据,便于前端画图
        ArrayList<GuideStatisticsRsp> result = new ArrayList<>();
        GuideStatisticsRsp statisticsRsp = new GuideStatisticsRsp();
        statisticsRsp.setGuideName("任务导调");
        statisticsRsp.setGuideType(1);
        statisticsRsp.setCount(4);
        statisticsRsp.setPercent(40.0);
        result.add(statisticsRsp);

        GuideStatisticsRsp statisticsRsp2 = new GuideStatisticsRsp();
        statisticsRsp2.setGuideName("手动导调");
        statisticsRsp2.setGuideType(2);
        statisticsRsp2.setCount(6);
        statisticsRsp2.setPercent(60.0);
        result.add(statisticsRsp2);
        return result;
    }

    public List<String> getElementIds(String sceneId, Boolean isInstance) {

        SceneData sceneData;
        if (isInstance != null && !isInstance) {
            sceneData = topoHandlerService.getSceneData(null, sceneId);
        } else {
            sceneData = topoHandlerService.getSceneData(sceneId, null);
        }
        SituationTopo situationTopo = situationHandlerService.getSituationTopo(sceneData);
        List<ElementInfo> elementInfoList = Lists
                .newArrayList(situationTopo.getElements().values());
        return elementInfoList.stream().map(ElementInfo::getElementId).collect(Collectors.toList());
    }

    public void validateLength(String target, Integer minLen, Integer maxLen, String errorCode) {
        if (StringUtils.isBlank(target)) {
            log.error("length of name is null, minLen is {}, maxLen is {}", minLen, maxLen);
            throw new BusinessException(errorCode, minLen, maxLen);
        }
        try {
            String targetStr = new String(target.getBytes("GBK"), StandardCharsets.ISO_8859_1);
            int len = targetStr.length();
            if (len < minLen || len > maxLen) {
                log.error("length of name is {}, minLen is {}, maxLen is {}", len, minLen, maxLen);
                throw new BusinessException(errorCode, minLen, maxLen);
            }
        } catch (UnsupportedEncodingException e) {
            log.error("target.getBytes error");
        }
    }

    public void validateNameLen(String target, String errorCode) {
        validateLength(target, MIN_STR_LEN, MAX_NAME_STR_LEN, errorCode);
    }


    public String viewScene(String roomId, Integer businessType) {
        String sceneId = null;
        SituationRoomBo situationRoomBo = situationGuideSchemeMapper.selectRoomType(roomId, businessType);
        JSONObject sceneData = getMpSceneData(situationRoomBo);
        if (sceneData != null) {
            sceneId = sceneData.getString(CrCommonConstants.MP_SCENE_ID_KEY);
        }
        return sceneId;
    }

    private JSONObject getMpSceneData(SituationRoomBo room) {

        if (Objects.isNull(room) || StringUtils.isBlank(room.getLocalSceneId())) {
            return null;
        }
        LocalScene localScene = localSceneService.getById(room.getLocalSceneId());
        if (null == localScene) {
            return null;
        }
        String mpSceneId = localScene.getMpSceneId();
        if (StringUtils.isBlank(mpSceneId)) {
            return null;
        }
        try {
            return localSceneService.getSceneData(mpSceneId, UserInfoContext.getUserContext().getUserId());
        } catch (Exception e) {
            log.error("situation guide getMpSceneData error");
        }
        return null;
    }
}
