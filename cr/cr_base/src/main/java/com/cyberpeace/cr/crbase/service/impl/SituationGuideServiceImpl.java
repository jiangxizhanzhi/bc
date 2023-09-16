package com.cyberpeace.cr.crbase.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.enums.BusinessType;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.controller.req.guide.AddGuideSchemeReq;
import com.cyberpeace.cr.crbase.controller.req.guide.GuideImplementReq;
import com.cyberpeace.cr.crbase.controller.req.guide.ListGuideSchemeReq;
import com.cyberpeace.cr.crbase.controller.req.guide.UpdateGuideSchemeReq;
import com.cyberpeace.cr.crbase.controller.rsp.guide.*;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.dao.entity.SituationGuideHistory;
import com.cyberpeace.cr.crbase.dao.entity.SituationGuideScheme;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskMapper;
import com.cyberpeace.cr.crbase.dao.mapper.MissionMapper;
import com.cyberpeace.cr.crbase.dao.mapper.SituationGuideHisMapper;
import com.cyberpeace.cr.crbase.dao.mapper.SituationGuideSchemeMapper;
import com.cyberpeace.cr.crbase.service.SituationGuideService;
import com.cyberpeace.cr.crbase.service.WsMultiNodeGuide;
import com.cyberpeace.cr.crbase.service.context.guide.SituationGuideMissionBo;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfig;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigMission;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigSchemeStage;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.service.RemoteSceneService;
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
    private CrTaskMapper crTaskMapper;
    @Autowired
    private TopoHandlerService topoHandlerService;
    @Autowired
    private LocalSceneService localSceneService;
    @Autowired
    private SituationHandlerService situationHandlerService;

    @Autowired
    private RemoteSceneService remoteSceneService;

    public Integer MIN_STR_LEN = 1;

    public Integer MAX_NAME_STR_LEN = 255;

    /**
     * 手动导调列表
     */
    @Override
    public ListRsp<SituationGuideScheme> listByRoomId(ListGuideSchemeReq req) {
        LambdaQueryWrapper<SituationGuideScheme> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SituationGuideScheme::getRoomId, req.getRoomId());
        queryWrapper.eq(SituationGuideScheme::getType, SituationGuideScheme.Type.MANUALLY.getCode());
        Page<SituationGuideScheme> page = page(req.toPageParam(), queryWrapper);
        return PageUtil.getListResult(page);
    }

    @Override
    public String addGuideScheme(AddGuideSchemeReq req) {
        validGuideSchemeName(req.getEventName(), req.getRoomId(), null);
        validGuideSchemeParam(req);
        SituationGuideScheme situationGuideScheme = BeanUtils.copy(req, SituationGuideScheme.class);
        situationGuideScheme.setResourceId(IDGeneratorUtil.getID());
        save(situationGuideScheme);
        return situationGuideScheme.getResourceId();
    }

    @Override
    public SituationGuideRes getGuideSchemeDetail(String resourceId) {
        SituationGuideScheme situationGuideScheme = getById(resourceId);
        if (situationGuideScheme == null) {
            log.error("get guide scheme by id ({}) error, guide scheme not found", resourceId);
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
    public UpdateGuideSchemeReq updateGuideScheme(UpdateGuideSchemeReq req) {
        validGuideSchemeName(req.getEventName(), req.getRoomId(), req.getResourceId());
        validGuideSchemeParam(req);
        SituationGuideScheme situationGuideScheme = BeanUtils.copy(req, SituationGuideScheme.class);
        updateById(situationGuideScheme);
        return req;
    }

    @Override
    public Boolean deleteGuideScheme(List<String> ids) {
        if (!CollectionUtils.isEmpty(ids)) {
            return removeByIds(ids);
        }
        return false;
    }

    private void validGuideSchemeName(String name, String roomId, String resourceId) {
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
    public Boolean guideImplement(GuideImplementReq req) {
        SituationGuideScheme guideScheme = getById(req.getResourceId());
        if (Objects.isNull(guideScheme)) {
            return false;
        }
        String sceneId = viewScene(guideScheme.getRoomId());

        //增加历史记录
        SituationGuideHistory his = BeanUtils.copy(guideScheme, SituationGuideHistory.class);
        his.setResourceId(IDGeneratorUtil.getID());
        his.setId(null);
        his.setCreateTime(new Date());
        his.setUpdateTime(new Date());
        his.setSceneId(sceneId);
        situationGuideHisMapper.insert(his);

        return pushDataToRoomByGuide(req.getIsInstance(), his);
    }


    @Override
    public SituationGuideHistory getGuideHisByMissionId(String missionId, String taskId) {
        LambdaQueryWrapper<SituationGuideScheme> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SituationGuideScheme::getMissionId, missionId);
        SituationGuideScheme guideScheme = situationGuideSchemeMapper.selectOne(queryWrapper);
        if (Objects.isNull(guideScheme)) {
            return null;
        }
        SituationGuideHistory guideHis = BeanUtils.copy(guideScheme, SituationGuideHistory.class);
        guideHis.setResourceId(IDGeneratorUtil.getID());
        guideHis.setId(null);
        guideHis.setTaskId(taskId);
        guideHis.setSceneId(viewScene(guideHis.getRoomId()));
        guideHis.setCreateTime(new Date());
        guideHis.setUpdateTime(new Date());
        return guideHis;
    }

    @Override
    public Boolean pushDataToRoomByGuide(Boolean isInstance, SituationGuideHistory his) {
        if (Objects.isNull(his) || StringUtils.isBlank(his.getSceneId())) {
            return false;
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
        return true;
    }

    @Override
    public List<AttackTopFiveRsp> attackTop5(String roomId) {
        ArrayList<AttackTopFiveRsp> result = new ArrayList<>();
        //获取任务导调和态势导调数量
        result.add(new AttackTopFiveRsp(SituationGuideScheme.Type.MISSION.getDesc(),
                situationGuideSchemeMapper.countByType(roomId, SituationGuideScheme.Type.MISSION.getCode())));
        result.add(new AttackTopFiveRsp(SituationGuideScheme.Type.MANUALLY.getDesc(),
                situationGuideSchemeMapper.countByType(roomId, SituationGuideScheme.Type.MANUALLY.getCode())));
        //获取流量采集事件和主机采集事件数量
        LocalScene localScene = localSceneService.getByBusinessInstanceId(roomId);
        if (localScene == null) {
            result.add(new AttackTopFiveRsp(SituationGuideScheme.Type.FLOW.getDesc(), 0));
            result.add(new AttackTopFiveRsp(SituationGuideScheme.Type.HOST.getDesc(), 0));
        } else {
            JSONObject jsonObject = remoteSceneService.getEventTotalCount(localScene.getMpSceneId());
            result.add(new AttackTopFiveRsp(SituationGuideScheme.Type.FLOW.getDesc(), jsonObject.getInteger("flow")));
            result.add(new AttackTopFiveRsp(SituationGuideScheme.Type.HOST.getDesc(), jsonObject.getInteger("host")));
        }
        return result;
    }

    @Override
    public List<GuideRoleStageRsp> missionGuide(String roomId, Integer businessType) {

        //根据roomId和训练类型获取角色任务配置(团队训练/红蓝对抗)
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

        //根据roomId获取训练下发任务,'未下发',任务导调才可编辑 (未下发无task数据)
        List<String> missionIds = crTaskMapper.selectByRoomId(roomId).stream().map(CrTask::getMissionId).collect(Collectors.toList());

        //获取训练房间所有导调任务
        LambdaQueryWrapper<SituationGuideScheme> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SituationGuideScheme::getRoomId, roomId);
        Map<String, SituationGuideScheme> missionGuideMap = StreamApiUtils.toMap(situationGuideSchemeMapper.selectList(queryWrapper), SituationGuideScheme::getMissionId, item -> item);

        //根据roomId获取训练任务mission
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
                        //组装训练任务,编辑状态,态势导调任务id
                        GuideMissionRsp guideMissionRsp = new GuideMissionRsp();
                        Mission mission = missionMap.get(missionTemplate.getResourceId());
                        guideMissionRsp.setMissionTitle(mission.getTitle());
                        guideMissionRsp.setMissionId(mission.getResourceId());
                        //组装编辑状态
                        Boolean isEditable = !missionIds.contains(mission.getResourceId());
                        guideMissionRsp.setIsEditable(isEditable);
                        //组装态势导调任务id
                        SituationGuideScheme guideScheme = missionGuideMap.get(mission.getResourceId());
                        guideMissionRsp.setGuideId(Objects.isNull(guideScheme) ? null : guideScheme.getResourceId());
                        missionList.add(guideMissionRsp);
                    }
                }
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
        ArrayList<GuideStatisticsRsp> result = new ArrayList<>();
        //获取任务导调和态势导调数量
        result.add(new GuideStatisticsRsp(SituationGuideScheme.Type.MISSION.getDesc(),
                situationGuideSchemeMapper.countByType(roomId, SituationGuideScheme.Type.MISSION.getCode())));
        result.add(new GuideStatisticsRsp(SituationGuideScheme.Type.MANUALLY.getDesc(),
                situationGuideSchemeMapper.countByType(roomId, SituationGuideScheme.Type.MANUALLY.getCode())));
        //获取流量采集事件和主机采集事件数量
        LocalScene localScene = localSceneService.getByBusinessInstanceId(roomId);
        if (localScene == null) {
            result.add(new GuideStatisticsRsp(SituationGuideScheme.Type.FLOW.getDesc(), 0));
            result.add(new GuideStatisticsRsp(SituationGuideScheme.Type.HOST.getDesc(), 0));
        } else {
            JSONObject jsonObject = remoteSceneService.getEventTotalCount(localScene.getMpSceneId());
            result.add(new GuideStatisticsRsp(SituationGuideScheme.Type.FLOW.getDesc(), jsonObject.getInteger("flow")));
            result.add(new GuideStatisticsRsp(SituationGuideScheme.Type.HOST.getDesc(), jsonObject.getInteger("host")));
        }
        return result;
    }

    /**
     * 校验导调参数
     */
    private void validGuideSchemeParam(AddGuideSchemeReq req) {
        //任务导调判断是否可新增/编辑
        if (SituationGuideScheme.Type.MISSION.getCode().equals(req.getType()) && Objects.nonNull(req.getMissionId())) {
            List<CrTask> taskList = crTaskMapper.selectByMissionId(req.getMissionId());
            //'未下发',任务导调才可编辑
            if (!CollectionUtils.isEmpty(taskList)) {
                throw new BusinessException(SituationErrorCode.INVALID_GUIDE_SCHEME);
            }
        }

        //判断参数是否为空
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

    public List<String> getElementIds(String sceneId, Boolean isInstance) {

        SceneData sceneData;
        if (isInstance != null && !isInstance) {
            sceneData = topoHandlerService.getSceneData(null, sceneId);
        } else {
            sceneData = topoHandlerService.getSceneData(sceneId, null);
        }
        SituationTopo situationTopo = situationHandlerService.getSituationTopo(sceneData);
        List<ElementInfo> elementInfoList = Lists.newArrayList(situationTopo.getElements().values());
        return elementInfoList.stream().map(ElementInfo::getElementId).collect(Collectors.toList());
    }

    /**
     * 校验导调名称是否合法
     */
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

    public String viewScene(String roomId) {
        LocalScene localScene = localSceneService.getByBusinessInstanceId(roomId);
        if (null == localScene) {
            return null;
        }
        return localScene.getMpSceneId();
    }

}
