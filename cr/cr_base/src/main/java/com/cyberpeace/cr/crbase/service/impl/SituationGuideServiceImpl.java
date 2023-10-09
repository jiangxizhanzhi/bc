package com.cyberpeace.cr.crbase.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.enums.BusinessType;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.req.guide.*;
import com.cyberpeace.cr.crbase.controller.rsp.constants.MissionCategoryRsp;
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
import com.cyberpeace.cr.crbase.service.context.guide.*;
import com.cyberpeace.cr.crbase.service.context.mp.AttackTypeCount;
import com.cyberpeace.cr.crbase.service.context.mp.SceneTrend;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfig;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigMission;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigSchemeStage;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.model.scene.AttackScreenContext;
import com.cyberpeace.integratation.model.scene.SceneEventContext;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.constant.ws.MessageType;
import com.cyberpeace.situation.model.ElementInfo;
import com.cyberpeace.situation.model.LineStyle;
import com.cyberpeace.situation.model.SceneData;
import com.cyberpeace.situation.model.SituationTopo;
import com.cyberpeace.situation.model.ws.WsMessage;
import com.cyberpeace.situation.service.SituationHandlerService;
import com.cyberpeace.situation.service.TopoHandlerService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
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

    private static final int MIN_LEN = 1;

    private static final int MAX_NAME_LEN = 255;

    /**
     * 手动导调列表
     */
    @Override
    public ListRsp<SituationGuideListRsp> listByRoomId(ListGuideSchemeReq req) {
        LambdaQueryWrapper<SituationGuideScheme> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SituationGuideScheme::getRoomId, req.getRoomId());
        queryWrapper.eq(SituationGuideScheme::getType, SituationGuideScheme.Type.MANUALLY.getCode());
        Page<SituationGuideScheme> page = page(req.toPageParam(), queryWrapper);
        page.convert(item -> {
            SituationGuideListRsp situationGuideListRsp = new SituationGuideListRsp();
            situationGuideListRsp.setResourceId(item.getResourceId());
            situationGuideListRsp.setRoomId(item.getRoomId());
            situationGuideListRsp.setEventName(item.getEventName());
            situationGuideListRsp.setEventType(item.getEventType());
            situationGuideListRsp.setCategory(item.getCategory());
            situationGuideListRsp.setSource(item.getSource());
            situationGuideListRsp.setTarget(item.getTarget());
            situationGuideListRsp.setBusinessType(item.getBusinessType());
            return situationGuideListRsp;
        });
        ListRsp<SituationGuideListRsp> result = PageUtil.getListResult(page);
        if (CollectionUtils.isEmpty(result.getList())) {
            return result;
        }
        Integer businessType = result.getList().get(0).getBusinessType();
        SituationTopo situationTopo = getSituationTopoByRoomId(req.getRoomId(), businessType);

        for (SituationGuideListRsp situationGuideListRsp : result.getList()) {
            Map<String, ElementInfo> elementMap = situationTopo.getElements();
            ElementInfo sourceElement = elementMap.get(situationGuideListRsp.getSource());
            ElementInfo targetElement = elementMap.get(situationGuideListRsp.getTarget());
            situationGuideListRsp.setSourceName(Objects.isNull(sourceElement) ? situationGuideListRsp.getSource() : sourceElement.getName());
            situationGuideListRsp.setTargetName(Objects.isNull(targetElement) ? situationGuideListRsp.getTarget() : targetElement.getName());
            if (SituationGuideScheme.EventType.ATTACK.getCode().equals(situationGuideListRsp.getEventType())) {
                SituationGuideScheme.AttackUrlEnum enumByCode = SituationGuideScheme.AttackUrlEnum.getEnumByCode(situationGuideListRsp.getCategory());
                situationGuideListRsp.setCategoryName(enumByCode.getMsg());
            } else if (SituationGuideScheme.EventType.DEFENSE.getCode().equals(situationGuideListRsp.getEventType())) {
                SituationGuideScheme.DefenseUrlEnum enumByCode = SituationGuideScheme.DefenseUrlEnum.getEnumByCode(situationGuideListRsp.getCategory());
                situationGuideListRsp.setCategoryName(enumByCode.getMsg());
            }
        }
        return result;
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
    public SituationGuideRsp getGuideSchemeDetail(String resourceId) {
        SituationGuideScheme situationGuideScheme = getById(resourceId);
        if (situationGuideScheme == null) {
            log.error("get guide scheme by id ({}) error, guide scheme not found", resourceId);
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        SituationGuideRsp resp = BeanUtils.copy(situationGuideScheme, SituationGuideRsp.class);
        SituationGuideScheme.Type type = SituationGuideScheme.Type.getTypeByCode(resp.getType());
        resp.setTypeName(type.getMsg());

        SituationGuideScheme.EventType eventType = SituationGuideScheme.EventType.getEventTypeByCode(resp.getEventType());
        resp.setEventTypeName(eventType.getMsg());

        SituationTopo situationTopo = getSituationTopoByRoomId(situationGuideScheme.getRoomId(), situationGuideScheme.getBusinessType());
        Map<String, ElementInfo> elementMap = situationTopo.getElements();

        ElementInfo sourceElement = elementMap.get(resp.getSource());
        ElementInfo targetElement = elementMap.get(resp.getTarget());
        resp.setSourceName(Objects.isNull(sourceElement) ? resp.getSource() : sourceElement.getName());
        resp.setTargetName(Objects.isNull(targetElement) ? resp.getTarget() : targetElement.getName());

        SituationGuideScheme.ResultType resultType = SituationGuideScheme.ResultType.getResultTypeByCode(resp.getResult());
        resp.setResultName(Objects.isNull(resultType) ? null : resultType.getMsg());

        SituationGuideScheme.RoleType roleType = SituationGuideScheme.RoleType.getRoleTypeByCode(resp.getRole());
        resp.setRoleName(Objects.isNull(roleType) ? null : roleType.getMsg());

        SituationGuideScheme.ConditionType conditionType = SituationGuideScheme.ConditionType.getEnumByCode(resp.getTriggerCondition());
        resp.setTriggerName(Objects.isNull(conditionType) ? null : conditionType.getMsg());

        switch (eventType) {
            case ATTACK:
                SituationGuideScheme.AttackUrlEnum attackUrlEnum = SituationGuideScheme.AttackUrlEnum.getEnumByCode(resp.getCategory());
                resp.setCategoryName(attackUrlEnum.getMsg());
                resp.setSuccessUrl(attackUrlEnum.getSuccessUrl());
                resp.setFailUrl(attackUrlEnum.getFailUrl());
                break;
            case DEFENSE:
                SituationGuideScheme.DefenseUrlEnum defenseUrlEnum = SituationGuideScheme.DefenseUrlEnum.getEnumByCode(resp.getCategory());
                resp.setCategoryName(defenseUrlEnum.getMsg());
                resp.setSuccessUrl(defenseUrlEnum.getSuccessUrl());
                resp.setFailUrl(defenseUrlEnum.getFailUrl());
                break;
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
            removeByIds(ids);
            LambdaQueryWrapper<SituationGuideHistory> deleteWrapper = new LambdaQueryWrapper<>();
            deleteWrapper.in(SituationGuideHistory::getGuideId, ids);
            situationGuideHisMapper.delete(deleteWrapper);
            return true;
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
        //校验训练是否开启
        checkRoomOpening(guideScheme);
        String sceneId = viewScene(guideScheme.getRoomId());

        //增加历史记录
        SituationGuideHistory his = BeanUtils.copy(guideScheme, SituationGuideHistory.class);
        his.setResourceId(IDGeneratorUtil.getID());
        his.setGuideId(guideScheme.getResourceId());
        his.setId(null);
        his.setCreateTime(new Date());
        his.setUpdateTime(new Date());
        his.setSceneId(sceneId);
        situationGuideHisMapper.insert(his);

        return pushDataToRoomByGuide(Boolean.TRUE, his);
    }

    @Override
    public Boolean guideTest(AddGuideSchemeReq req) {
        String sceneId = viewScene(req.getRoomId());
        SituationGuideHistory his = BeanUtils.copy(req, SituationGuideHistory.class);
        his.setSceneId(sceneId);
        return pushDataToRoomByGuide(Boolean.TRUE, his);
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
        guideHis.setGuideId(guideScheme.getResourceId());
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
        LineStyle lineStyle = new LineStyle();
        lineStyle.setLineType("straight");
        lineStyle.setColor("#FF0000");
        lineStyle.setWidth(3);
        wsMultiNodeGuide.setLineStyle(lineStyle);

        WsMessage<WsMultiNodeGuide> wsMessage;
        if (elementIds.contains(his.getTarget())) {
            wsMessage = new WsMessage<>(MessageType.DirectorSchedulingEE.getDesc(), wsMultiNodeGuide);
        } else {
            wsMessage = new WsMessage<>(MessageType.DirectorSchedulingEA.getDesc(), wsMultiNodeGuide);
        }
        situationHandlerService.pushDataToRoom(wsMessage, wsMultiNodeGuide.getSceneId());
        return true;
    }

    @Override
    public List<AttackTopFiveRsp> attackTop5(String roomId) {
        //获取任务导调和手动导调记录
        List<AttackTypeCountGroup> attackTypeCountGroupList = situationGuideHisMapper.countByCategory(roomId, SituationGuideScheme.EventType.ATTACK.getCode());
        List<AttackTopFiveRsp> result = attackTypeCountGroupList.stream()
                .map(attackTypeCountGroup -> new AttackTopFiveRsp(SituationGuideScheme.AttackUrlEnum.getEnumByCode(attackTypeCountGroup.getCategory()).getMsg(), attackTypeCountGroup.getCount()))
                .collect(Collectors.toList());
        //获取主机采集事件和流量采集事件
        LocalScene localScene = localSceneService.getByBusinessInstanceId(roomId);
        if (localScene != null) {
            JSONArray attackCountList = remoteSceneService.getAttackTypeCountGroup(localScene.getMpSceneId());
            List<AttackTypeCount> attackTypeCountList = attackCountList.toJavaList(AttackTypeCount.class);
            result.addAll(attackTypeCountList.stream().map(attackTypeCount -> new AttackTopFiveRsp(attackTypeCount.getAttack_type(), attackTypeCount.getTimes())).collect(Collectors.toList()));
        }
        //排序，取Top5
        result.sort(Comparator.comparingInt(AttackTopFiveRsp::getCount).reversed());
        if (result.size() < 5) {
            return result;
        }
        return result.subList(0, 5);
    }

    @Override
    public List<GuideRoleStageRsp> missionGuide(String roomId, Integer businessType) {

        //根据roomId和训练类型获取角色任务配置(团队训练/红蓝对抗)
        SituationGuideMissionBo situationGuideMissionBo = situationGuideSchemeMapper.selectRoleConfigByBusinessType(roomId, businessType);
        if (Objects.isNull(situationGuideMissionBo) || StringUtils.isBlank(situationGuideMissionBo.getRoleConfig())) {
            return new ArrayList<>();
        }
        //获取模版角色任务配置
        List<CrRoleConfig> crRoleConfigs = getRoleConfigs(businessType, situationGuideMissionBo);

        //根据roomId获取训练下发任务,'未下发',任务导调才可编辑 (未下发无task数据)
        List<String> missionIds = crTaskMapper.selectByRoomId(roomId).stream().map(CrTask::getMissionId).collect(Collectors.toList());

        //根据roomId获取历史记录中已触发的导调任务
        LambdaQueryWrapper<SituationGuideHistory> hisWrapper = new LambdaQueryWrapper<>();
        hisWrapper.eq(SituationGuideHistory::getRoomId, roomId);
        hisWrapper.eq(SituationGuideHistory::getType, SituationGuideScheme.Type.MISSION.getCode());
        List<String> guideIdList = StreamApiUtils.map(situationGuideHisMapper.selectList(hisWrapper), SituationGuideHistory::getGuideId);

        //获取训练房间所有导调任务
        LambdaQueryWrapper<SituationGuideScheme> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SituationGuideScheme::getRoomId, roomId);
        queryWrapper.eq(SituationGuideScheme::getType, SituationGuideScheme.Type.MISSION.getCode());
        Map<String, SituationGuideScheme> missionGuideMap = StreamApiUtils.toMap(situationGuideSchemeMapper.selectList(queryWrapper), SituationGuideScheme::getMissionId, item -> item);

        //根据roomId获取训练任务mission
        List<Mission> missions = missionMapper.selectByRoomId(roomId).stream().filter(item -> StringUtils.isNotBlank(item.getMissionTemplateId())).collect(Collectors.toList());
        Map<String, Mission> missionMap = StreamApiUtils.toMap(missions, Mission::getMissionTemplateId, item -> item);
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
                    if (!missionMap.containsKey(missionTemplate.getResourceId())){
                        continue;
                    }
                    //组装训练任务,编辑状态,是否触发,态势导调任务id
                    GuideMissionRsp guideMissionRsp = getMissionRsp(missionIds, missionGuideMap, missionMap, guideIdList, missionTemplate);
                    missionList.add(guideMissionRsp);
                }
                missionStageRsp.setMissionList(missionList);
                stageList.add(missionStageRsp);
            }
            guideRoleStageRsp.setStageList(stageList);
            result.add(guideRoleStageRsp);
        }
        return result;
    }

    private GuideMissionRsp getMissionRsp(List<String> missionIds, Map<String, SituationGuideScheme> missionGuideMap, Map<String, Mission> missionMap, List<String> guideIdList, CrRoleConfigMission missionTemplate) {
        GuideMissionRsp guideMissionRsp = new GuideMissionRsp();
        Mission mission = missionMap.get(missionTemplate.getResourceId());
        guideMissionRsp.setMissionTitle(mission.getTitle());
        guideMissionRsp.setMissionId(mission.getResourceId());

        Boolean isEditable = !missionIds.contains(mission.getResourceId());
        guideMissionRsp.setIsEditable(isEditable);

        SituationGuideScheme guideScheme = missionGuideMap.get(mission.getResourceId());
        guideMissionRsp.setGuideId(Objects.isNull(guideScheme) ? null : guideScheme.getResourceId());
        guideMissionRsp.setEventType(Objects.isNull(guideScheme) ? null : guideScheme.getEventType());

        Boolean isTriggered= !Objects.isNull(guideScheme) && guideIdList.contains(guideScheme.getResourceId());
        guideMissionRsp.setIsTriggered(isTriggered);
        return guideMissionRsp;
    }

    private List<CrRoleConfig> getRoleConfigs(Integer businessType, SituationGuideMissionBo situationGuideMissionBo) {
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
        return crRoleConfigs;
    }

    @Override
    public GuideStatisticsRsp guideStatistics(String roomId) {
        GuideStatisticsRsp result = new GuideStatisticsRsp();
        ArrayList<GuideStatisticsDetailRsp> detailList = new ArrayList<>();
        //获取任务导调和态势导调数量
        detailList.add(new GuideStatisticsDetailRsp(SituationGuideScheme.Type.MISSION.getDesc(),
                situationGuideHisMapper.countByType(roomId, SituationGuideScheme.Type.MISSION.getCode())));
        detailList.add(new GuideStatisticsDetailRsp(SituationGuideScheme.Type.MANUALLY.getDesc(),
                situationGuideHisMapper.countByType(roomId, SituationGuideScheme.Type.MANUALLY.getCode())));
        //获取流量采集事件和主机采集事件数量
        LocalScene localScene = localSceneService.getByBusinessInstanceId(roomId);
        if (localScene == null) {
            detailList.add(new GuideStatisticsDetailRsp(SituationGuideScheme.Type.FLOW.getDesc(), 0));
            detailList.add(new GuideStatisticsDetailRsp(SituationGuideScheme.Type.HOST.getDesc(), 0));
        } else {
            JSONObject jsonObject = remoteSceneService.getEventTotalCount(localScene.getMpSceneId());
            detailList.add(new GuideStatisticsDetailRsp(SituationGuideScheme.Type.FLOW.getDesc(), jsonObject.getInteger("flow")));
            detailList.add(new GuideStatisticsDetailRsp(SituationGuideScheme.Type.HOST.getDesc(), jsonObject.getInteger("host")));
        }
        Integer totalCount = detailList.stream().map(GuideStatisticsDetailRsp::getCount).reduce(0, Integer::sum);

        result.setList(detailList);
        result.setTotalCount(totalCount);
        return result;
    }

    @Override
    public GuideTimeDistributionRsp guideTimeDistribution(String roomId) {
        GuideTimeDistributionRsp result = new GuideTimeDistributionRsp();
        //获取流量采集事件和主机采集事件数量
        List<Integer> eventList = new ArrayList<>();
        LocalScene localScene = localSceneService.getByBusinessInstanceId(roomId);
        if (localScene != null) {
            JSONArray attackTrendList = remoteSceneService.getEventTrentCount(localScene.getMpSceneId());
            List<SceneTrend> sceneTrendList = attackTrendList.toJavaList(SceneTrend.class);
            eventList = sceneTrendList.stream().map(SceneTrend::getData_size).collect(Collectors.toList());
        }
        // 导调24小时内数据
        Date oneDayAgo = TimeUtil.addHours(new Date(), -24);
        List<AttackTimeCountGroup> attackTimeCountGroupList = situationGuideHisMapper.countByTime(roomId, oneDayAgo);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:00:00");
        List<String> timeList = new ArrayList<>();
        List<Integer> countList = new ArrayList<>();
        for (int i = 1; i < 25; i++) {
            String dateFormatStr = dateFormat.format(TimeUtil.addHours(oneDayAgo, i));
            String currentFormat = simpleDateFormat.format(TimeUtil.addHours(oneDayAgo, i));
            timeList.add(currentFormat);
            int count = 0;
            for (AttackTimeCountGroup attack : attackTimeCountGroupList) {
                if (dateFormatStr.equals(attack.getCreateTime())) {
                    count = attack.getCount();
                }
            }
            count += CollectionUtils.isEmpty(eventList) ? 0 : eventList.get(i - 1);
            countList.add(count);
        }
        result.setTimeList(timeList);
        result.setCountList(countList);
        return result;
    }

    @Override
    public GuideParamTypeRsp getParamType(String roomId, Integer businessType) {
        GuideParamTypeRsp result = new GuideParamTypeRsp();
        if (BusinessType.TRAINING_TEAM.getCode().equals(businessType)) {
            List<ParamTypeRsp> eventType = new ArrayList<>();
            eventType.add(new ParamTypeRsp(SituationGuideScheme.EventType.ATTACK.getDesc(), SituationGuideScheme.EventType.ATTACK.getCode()));
            eventType.add(new ParamTypeRsp(SituationGuideScheme.EventType.DEFENSE.getDesc(), SituationGuideScheme.EventType.DEFENSE.getCode()));
            eventType.add(new ParamTypeRsp(SituationGuideScheme.EventType.BLOCKING.getDesc(), SituationGuideScheme.EventType.BLOCKING.getCode()));
            result.setEventType(eventType);
        } else {
            result.setEventType(Arrays.stream(SituationGuideScheme.EventType.values()).map(item -> new ParamTypeRsp(item.getDesc(), item.getCode())).collect(Collectors.toList()));
        }

        result.setResultType(Arrays.stream(SituationGuideScheme.ResultType.values()).map(item -> new ParamTypeRsp(item.getDesc(), item.getCode())).collect(Collectors.toList()));
        result.setRoleType(Arrays.stream(SituationGuideScheme.RoleType.values()).map(item -> new ParamTypeRsp(item.getDesc(), item.getCode())).collect(Collectors.toList()));
        result.setConditionType(Arrays.stream(SituationGuideScheme.ConditionType.values()).map(item -> new ParamTypeRsp(item.getDesc(), item.getCode())).collect(Collectors.toList()));

        SituationModelRsp situationModelRsp = listModel();
        result.setAttackType(Arrays.stream(SituationGuideScheme.AttackUrlEnum.values())
                .map(item -> new AttackSituationOffensiveDescRsp(item.getCode(), item.getMsg(), item.getDesc(), item.getSuccessUrl(), item.getFailUrl(), item.getType()))
                .collect(Collectors.toList()));
        result.setDefenseType(situationModelRsp.getDefenseList());
        result.setBlockingUrl(situationModelRsp.getBlockingUrl());
        result.setBelongUrl(situationModelRsp.getBelongUrl());

        SituationTopo situationTopo = getSituationTopoByRoomId(roomId, businessType);

        List<MissionCategoryRsp> nodeList=new ArrayList<>();
        List<MissionCategoryRsp> blockingNodeType=new ArrayList<>();
        for (ElementInfo elementInfo : situationTopo.getElements().values()) {
            MissionCategoryRsp missionCategoryRsp = new MissionCategoryRsp(elementInfo.getElementId(), elementInfo.getName());
            if (Objects.nonNull(elementInfo.getOneCategoryCode()) && 4 == elementInfo.getOneCategoryCode()) {
                blockingNodeType.add(missionCategoryRsp);
            }
            nodeList.add(missionCategoryRsp);
        }
        result.setNodeType(nodeList);
        result.setBlockingNodeType(blockingNodeType);

        if (CollectionUtils.isEmpty(situationTopo.getAreas())) {
            result.setAreaType(new ArrayList<>());
            return result;
        }
        List<MissionCategoryRsp> areaList = situationTopo.getAreas().values().stream()
                .map(areaInfo -> new MissionCategoryRsp(areaInfo.getAreaId(), "area-" + areaInfo.getAreaId()))
                .collect(Collectors.toList());
        result.setAreaType(areaList);

        return result;
    }

    @Override
    public SituationModelRsp listModel() {
        SituationModelRsp situationModelRsp = new SituationModelRsp();
        List<SituationOffensiveDescRsp> attackList = new ArrayList<>();
        List<SituationOffensiveDescRsp> defenseList = new ArrayList<>();

        for (SituationGuideScheme.AttackUrlEnum modelEnum : SituationGuideScheme.AttackUrlEnum.values()) {
            SituationOffensiveDescRsp model = new SituationOffensiveDescRsp(modelEnum.getCode(), modelEnum.getMsg(), modelEnum.getDesc(), modelEnum.getSuccessUrl(), modelEnum.getFailUrl());
            attackList.add(model);
        }

        for (SituationGuideScheme.DefenseUrlEnum modelEnum : SituationGuideScheme.DefenseUrlEnum.values()) {
            SituationOffensiveDescRsp model = new SituationOffensiveDescRsp(modelEnum.getCode(), modelEnum.getMsg(), modelEnum.getDesc(), modelEnum.getSuccessUrl(), modelEnum.getFailUrl());
            defenseList.add(model);
        }
        situationModelRsp.setAttackList(attackList);
        situationModelRsp.setDefenseList(defenseList);
        situationModelRsp.setBlockingUrl(SituationGuideScheme.BlockingAndBelongingUrlEnum.BLOCKING.getOneUrl());
        SituationBelongDescRsp belong = new SituationBelongDescRsp();
        belong.setRedUrl(SituationGuideScheme.BlockingAndBelongingUrlEnum.BELONGING.getOneUrl());
        belong.setBlueUrl(SituationGuideScheme.BlockingAndBelongingUrlEnum.BELONGING.getTwoUrl());
        situationModelRsp.setBelongUrl(belong);
        return situationModelRsp;
    }

    @Override
    public ListRsp<GuideHistoryRsp> guideRecord(GuideRecordReq pageReq) {

        Date oneDayAgo = TimeUtil.addHours(new Date(), -24);
        ScreenGuideHistoryReq historyReq = new ScreenGuideHistoryReq();
        historyReq.setEventSubTypeList(Arrays.asList(SituationGuideScheme.Type.FLOW.getCode(), SituationGuideScheme.Type.HOST.getCode()));
        historyReq.setRoomId(pageReq.getRoomId());
        historyReq.setBusinessType(pageReq.getBusinessType());
        ScreenBo screenBo = new ScreenBo();
        screenBo.setStartTime(TimeUtil.format1(oneDayAgo));
        screenBo.setEndTime(TimeUtil.format1(new Date()));
        historyReq.setAttackScreenBo(screenBo);
        historyReq.setDefenseScreenBo(screenBo);
        List<GuideHistoryRsp> mpEventList = mpEventToGuideHistoryRsp(getMpEventList(historyReq));

        List<SituationGuideHistory> historyList = situationGuideHisMapper.guideRecordListByTime(pageReq.getRoomId(), oneDayAgo);
        Set<String> sceneSet = historyList.stream().map(SituationGuideHistory::getSceneId).filter(StringUtils::isNotBlank).collect(Collectors.toSet());
        Map<String, ElementInfo> elementMap = Maps.newHashMap();

        for (String sceneId : sceneSet) {
            SituationTopo situationTopo = situationHandlerService.getSituationTopoBySceneId(sceneId, true);
            elementMap.putAll(situationTopo.getElements());
        }

        historyList.sort(Comparator.comparingLong(item -> item.getCreateTime().getTime()));
        List<GuideHistoryRsp> resultList = new ArrayList<>();
        for (SituationGuideHistory his : historyList) {
            GuideHistoryRsp guideHistoryRsp = new GuideHistoryRsp();
            guideHistoryRsp.setEventName(his.getEventName());
            SituationGuideScheme.Type triggerType = SituationGuideScheme.Type.getTypeByCode(his.getType());
            guideHistoryRsp.setTriggerType(Objects.isNull(triggerType) ? null : triggerType.getDesc());
            ElementInfo sourceElement = elementMap.get(his.getSource());
            ElementInfo targetElement = elementMap.get(his.getTarget());
            guideHistoryRsp.setSourceName(Objects.isNull(sourceElement) ? his.getSource() : sourceElement.getName());
            guideHistoryRsp.setTargetName(Objects.isNull(targetElement) ? his.getTarget() : targetElement.getName());
            guideHistoryRsp.setCreateTime(his.getCreateTime());
            guideHistoryRsp.setActionType(SituationGuideScheme.EventType.getEventTypeByCode(his.getEventType()).getMsg());
            if (SituationGuideScheme.EventType.ATTACK.getCode().equals(his.getEventType())) {
                SituationGuideScheme.AttackUrlEnum attackUrlEnum = SituationGuideScheme.AttackUrlEnum.getEnumByCode(his.getCategory());
                guideHistoryRsp.setCategory(Collections.singletonList(attackUrlEnum.getMsg()));
            } else if (SituationGuideScheme.EventType.DEFENSE.getCode().equals(his.getEventType())) {
                SituationGuideScheme.DefenseUrlEnum defenseUrlEnum = SituationGuideScheme.DefenseUrlEnum.getEnumByCode(his.getCategory());
                guideHistoryRsp.setCategory(Collections.singletonList(defenseUrlEnum.getMsg()));
            }
            resultList.add(guideHistoryRsp);
        }
        resultList.addAll(mpEventList);
        resultList.sort(Comparator.comparing(GuideHistoryRsp::getCreateTime).reversed());
        return PageUtil.getListResult(resultList, pageReq.toPageParam(), resultList.size());
    }

    @Override
    public List<MpEvent> getMpEventList(ScreenGuideHistoryReq screenGuideHistoryReq) {

        SceneEventContext sceneEventContext = new SceneEventContext();
        //默认返回前1000条数据
        sceneEventContext.setPage(1);
        sceneEventContext.setPageSize(1000);
        //获取该训练过程中的所有场景id
        String sceneIdsRecord = situationGuideHisMapper.getSceneIdsByRoom(screenGuideHistoryReq.getRoomId(), screenGuideHistoryReq.getBusinessType());
        List<String> sceneIds = JsonUtil.toJsonObjectArray(sceneIdsRecord, String.class);
        if (CollectionUtils.isEmpty(sceneIds)) {
            return new ArrayList<>();
        }
        sceneEventContext.setSceneId(StringUtils.join(sceneIds, ","));
        //是否查询流量事件
        sceneEventContext.setFlow(screenGuideHistoryReq.getEventSubTypeList().contains(SituationGuideScheme.Type.FLOW.getCode()));
        //是否查询主机事件
        sceneEventContext.setHost(screenGuideHistoryReq.getEventSubTypeList().contains(SituationGuideScheme.Type.HOST.getCode()));
        //是否筛选攻击
        if (screenGuideHistoryReq.getAttackScreenBo() == null) {
            sceneEventContext.setAttack(null);
        } else {
            sceneEventContext.setAttack(screenToAttackScreenContext(screenGuideHistoryReq.getAttackScreenBo(), true));
        }
        //是否筛选防御
        if (screenGuideHistoryReq.getDefenseScreenBo() == null) {
            sceneEventContext.setDefense(null);
        } else {
            sceneEventContext.setDefense(screenToAttackScreenContext(screenGuideHistoryReq.getDefenseScreenBo(), false));
        }
        //中台无阻断和归属事件，不做处理

        //处理成前端展示类型
        JSONArray mpEventArray = remoteSceneService.getEventList(sceneEventContext);
        return mpEventArray.toJavaList(MpEvent.class);
    }

    @Override
    public List<GuideHistoryRsp> mpEventToGuideHistoryRsp(List<MpEvent> mpEventList) {
        List<GuideHistoryRsp> result = new ArrayList<>();
        Map<String, Integer> attCk = Arrays.stream(SituationGuideScheme.AttackUrlEnum.values())
                .collect(Collectors.toMap(SituationGuideScheme.AttackUrlEnum::getMpCode, SituationGuideScheme.AttackUrlEnum::getCode));
        attCk.putAll(Arrays.stream(SituationGuideScheme.DefenseUrlEnum.values())
                .collect(Collectors.toMap(SituationGuideScheme.DefenseUrlEnum::getMpCode, SituationGuideScheme.DefenseUrlEnum::getCode)));

        for (MpEvent mpEvent : mpEventList) {
            GuideHistoryRsp guideHistoryRsp = new GuideHistoryRsp();
            guideHistoryRsp.setEventName(mpEvent.getEventName());
            guideHistoryRsp.setTriggerType(SituationGuideScheme.Type.getTypeByMpCode(mpEvent.getTriggerType()).getMsg());
            guideHistoryRsp.setTriggerCode(SituationGuideScheme.Type.getTypeByMpCode(mpEvent.getTriggerType()).getCode());
            guideHistoryRsp.setActionType(SituationGuideScheme.EventType.getEventTypeByMpCode(mpEvent.getActionType()).getMsg());
            guideHistoryRsp.setActionCode(SituationGuideScheme.EventType.getEventTypeByMpCode(mpEvent.getActionType()).getCode());
            guideHistoryRsp.setCategory(mpEvent.getAttCkNames());
            List<Integer> categoryCode = new ArrayList<>();
            for (String mpCode : mpEvent.getAttCks()) {
                categoryCode.add(attCk.get(mpCode));
            }
            guideHistoryRsp.setCategoryCode(categoryCode);
            guideHistoryRsp.setSourceName(mpEvent.getSourceNodeName());
            guideHistoryRsp.setSourceIp(mpEvent.getSourceNodeIp());
            guideHistoryRsp.setSourcePort(mpEvent.getSourceNodePort());
            guideHistoryRsp.setSourceId(mpEvent.getSourceNodeId());
            guideHistoryRsp.setTargetName(mpEvent.getTargetNodeName());
            guideHistoryRsp.setTargetIp(mpEvent.getTargetNodeIp());
            guideHistoryRsp.setTargetPort(mpEvent.getTargetNodePort());
            guideHistoryRsp.setTargetId(mpEvent.getTargetNodeId());
            guideHistoryRsp.setResult(SituationGuideScheme.ResultType.SUCCESS.getCode());
            guideHistoryRsp.setRole(SituationGuideScheme.RoleType.RED.getCode());
            guideHistoryRsp.setCreateTime(TimeUtil.toDate1(mpEvent.getTime()));
            result.add(guideHistoryRsp);
        }
        return result;
    }

    @Override
    public List<SituationGuideHistory> guideHistoryRspToSituationGuideHistory(List<ExecGuideHisReq> guideHistoryRspList) {
        List<SituationGuideHistory> situationGuideHistoryList = new ArrayList<>();
        for (ExecGuideHisReq execGuideHisReq : guideHistoryRspList) {
            for (Integer category : execGuideHisReq.getCategoryCode()) {
                SituationGuideHistory situationGuideHistory = new SituationGuideHistory();
                situationGuideHistory.setEventName(execGuideHisReq.getEventName());
                situationGuideHistory.setCategory(category);
                situationGuideHistory.setEventType(execGuideHisReq.getActionCode());
                situationGuideHistory.setSource(execGuideHisReq.getSourceId());
                situationGuideHistory.setTarget(execGuideHisReq.getTargetId());
                situationGuideHistory.setResult(execGuideHisReq.getResult());
                situationGuideHistory.setRole(execGuideHisReq.getRole());
                situationGuideHistoryList.add(situationGuideHistory);
            }
        }
        return situationGuideHistoryList;
    }

    /**
     * 校验导调参数
     */
    private void validGuideSchemeParam(AddGuideSchemeReq req) {
        //任务导调判断是否可新增/编辑
        boolean isMissionEdit = SituationGuideScheme.Type.MISSION.getCode().equals(req.getType()) && Objects.nonNull(req.getMissionId());
        if (isMissionEdit) {
            List<CrTask> taskList = crTaskMapper.selectByMissionId(req.getMissionId()).stream().filter(item -> StringUtils.isNotBlank(item.getMissionTemplateId())).collect(Collectors.toList());
            //'未下发',任务导调才可编辑
            throwExceptionError(!CollectionUtils.isEmpty(taskList));
        }

        //判断参数是否为空
        SituationGuideScheme.EventType eventType = SituationGuideScheme.EventType.getEventTypeByCode(req.getEventType());
        boolean missionFlag = SituationGuideScheme.Type.MISSION.getCode().equals(req.getType()) && req.getMissionId() == null;
        boolean flag;
        switch (eventType) {
            case ATTACK:
            case DEFENSE:
                flag = req.getCategory() == null || req.getSource() == null || req.getTarget() == null || req.getResult() == null;
                throwExceptionError(flag || missionFlag);
                break;
            case BLOCKING:
                flag = req.getSource() == null || req.getTarget() == null;
                throwExceptionError(flag || missionFlag);
                break;
            case BELONGING:
                flag = req.getTarget() == null;
                throwExceptionError(flag || missionFlag);
                break;
            default:
                throw new BusinessException(SituationErrorCode.INVALID_GUIDE_SCHEME);
        }
    }

    private void throwExceptionError(boolean b) {
        if (b) {
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
        validateLength(target, MIN_LEN, MAX_NAME_LEN, errorCode);
    }

    public SituationTopo getSituationTopoByRoomId(String roomId, Integer businessType) {
        SituationGuideSceneBo situationGuideSceneBo = situationGuideSchemeMapper.selectSceneByRoomId(roomId, businessType);
        if (Objects.isNull(situationGuideSceneBo)) {
            SituationTopo situationTopo = new SituationTopo();
            situationTopo.setElements(Maps.newHashMap());
            return situationTopo;
        }
        if (StringUtils.isNotBlank(situationGuideSceneBo.getSceneId())) {
            return situationHandlerService.getSituationTopoBySceneId(situationGuideSceneBo.getSceneId(), true);
        }
        return situationHandlerService.getSituationTopoBySceneId(situationGuideSceneBo.getTopoId(), false);
    }

    public String viewScene(String roomId) {
        LocalScene localScene = localSceneService.getByBusinessInstanceId(roomId);
        if (null == localScene) {
            return null;
        }
        return localScene.getMpSceneId();
    }

    /**
     * 转化攻击、防御的筛选条件
     */
    private AttackScreenContext screenToAttackScreenContext(ScreenBo screenBo, Boolean attack) {
        AttackScreenContext screenContext = new AttackScreenContext();
        screenContext.setStartTime(screenBo.getStartTime());
        screenContext.setEndTime(screenBo.getEndTime());
        if (null != screenBo.getCategory()) {
            String defenseType;
            if (Boolean.TRUE.equals(attack)) {
                defenseType = SituationGuideScheme.AttackUrlEnum.getEnumByCode(screenBo.getCategory()).getMsg();
            } else {
                defenseType = SituationGuideScheme.DefenseUrlEnum.getEnumByCode(screenBo.getCategory()).getMsg();
            }
            screenContext.setDefenseTypes(Collections.singletonList(defenseType));
        } else {
            screenContext.setDefenseTypes(null);
        }
        if (screenBo.getEventName() != null) {
            screenContext.setEventName(Collections.singletonList(screenBo.getEventName()));
        } else {
            screenContext.setEventName(null);
        }
        List<String> sourceNodeIds = StringUtils.isBlank(screenBo.getSource()) ? null : Collections.singletonList(screenBo.getSource());
        screenContext.setSourceNodeIds(sourceNodeIds);
        List<String> targetNodeIds = StringUtils.isBlank(screenBo.getSource()) ? null : Collections.singletonList(screenBo.getTarget());
        screenContext.setTargetNodeIds(targetNodeIds);
        return screenContext;
    }

    public void checkRoomOpening(SituationGuideScheme guideScheme) {
        SituationRoomBo situationRoomBo = situationGuideSchemeMapper.selectRoomTrainStatus(guideScheme.getRoomId(), guideScheme.getBusinessType());

        if (BusinessType.TRAINING_TEAM.getCode().equals(guideScheme.getBusinessType())) {
            if (situationRoomBo.getTrainingStatus().equals(0)) {
                log.error("teamTrain not begin! roomId=[{}]", guideScheme.getRoomId());
                throw new BusinessException(CrBusinessErrorCode.TRAIN_NOT_BEGIN);
            } else if (situationRoomBo.getTrainingStatus().equals(2)) {
                log.error("teamTrain is over! roomId=[{}]", guideScheme.getRoomId());
                throw new BusinessException(CrBusinessErrorCode.TRAIN_IS_OVER);
            }
        } else {
            if (situationRoomBo.getTrainingStatus().equals(4)) {
                log.error("RedVsBlueTrain not begin! roomId=[{}]", guideScheme.getRoomId());
                throw new BusinessException(CrBusinessErrorCode.TRAIN_NOT_BEGIN);
            } else if (situationRoomBo.getTrainingStatus().equals(1)) {
                log.error("RedVsBlueTrain is paused! roomId=[{}]", guideScheme.getRoomId());
                throw new BusinessException(CrBusinessErrorCode.ALREADY_PAUSED);
            } else if (situationRoomBo.getTrainingStatus().equals(0)) {
                log.error("RedVsBlueTrain is over! roomId=[{}]", guideScheme.getRoomId());
                throw new BusinessException(CrBusinessErrorCode.TRAIN_IS_OVER);
            }
        }
    }
}
