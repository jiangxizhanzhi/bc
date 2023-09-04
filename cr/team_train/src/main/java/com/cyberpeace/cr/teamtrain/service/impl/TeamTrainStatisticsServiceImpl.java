package com.cyberpeace.cr.teamtrain.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskMapper;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingPersonalStatistics;
import com.cyberpeace.cr.teamtrain.controller.req.TeamTrainStatisticsDetailReq;
import com.cyberpeace.cr.teamtrain.controller.rsp.TeamCapabilityInfoRsp;
import com.cyberpeace.cr.teamtrain.controller.rsp.TeamCapabilityRsp;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamTemplateMapper;
import com.cyberpeace.cr.teamtrain.service.TeamSceneService;
import com.cyberpeace.cr.teamtrain.service.TeamTrainStatisticsService;
import com.cyberpeace.cr.teamtrain.service.bo.TeamOrganizationStatisticsBo;
import com.cyberpeace.cr.teamtrain.service.bo.TeamPersonalStatisticsBo;
import com.cyberpeace.cr.teamtrain.service.bo.TeamStatisticsDetailBo;
import com.cyberpeace.integratation.model.mp.MpFlowAttackCount;
import com.cyberpeace.integratation.service.RemoteOrganizationService;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TeamTrainStatisticsServiceImpl implements TeamTrainStatisticsService {

    @Autowired
    CrTaskMapper crTaskMapper;

    @Autowired
    RemoteUserService remoteUserService;

    @Autowired
    RemoteOrganizationService remoteOrganizationService;

    @Autowired
    private TeamSceneService teamSceneService;

    @Autowired
    private CrRoleService crRoleService;

    @Autowired
    private RemoteSceneService remoteSceneService;

    @Autowired
    private TeamTemplateMapper teamTemplateMapper;

    @Override
    public List<TeamOrganizationStatisticsBo> getOrganizationRankStatistics(Integer teamCategory) {
        List<TeamOrganizationStatisticsBo> rsp = Lists.newArrayList();

		List<UserInfo> userInfos = remoteUserService.getAllUsers();
        List<String> userIds = Lists.newArrayList();
        Map<String, UserInfo> id2User = new HashMap<>();

        Map<String, Integer> org2TotalUser = new HashMap<>();
        Map<String, Double> org2TotalScore = new HashMap<>();
        Map<String, String> org2Name = new HashMap<>();

        for (UserInfo userInfo : userInfos) {
            id2User.put(userInfo.getUserId(), userInfo);
            userIds.add(userInfo.getUserId());

            String orgId = userInfo.getOrganizationId();
            if (org2TotalUser.containsKey(orgId)) {
                Integer totalUser = org2TotalUser.get(orgId);
                org2TotalUser.put(orgId, totalUser + 1);
            } else {
                org2TotalUser.put(orgId, 1);
                org2Name.put(orgId, userInfo.getOrganizationData());
            }
        }

        // 没有用户，返回
        if (userIds.size() == 0) {
            return rsp;
        }


        List<Integer> statusList = Lists.newArrayList();
        // 已完成的任务
        statusList.add(CrTask.TaskStatus.SUCCESS.getCode());
        statusList.add(CrTask.TaskStatus.FAIL.getCode());

        List<TrainingPersonalStatistics> personalStatistics = crTaskMapper.selectAllPersonalStatistics(
                statusList, BusinessTemplate.BusinessType.TRAINING_TEAM.getCode(), null, teamCategory, null, null
        );

        if (null != personalStatistics) {
            for (TrainingPersonalStatistics personalStatistic : personalStatistics) {
                String user_id = personalStatistic.getUserId();

                // 判断user是否还存在
                UserInfo user = id2User.get(user_id);
                if (null == user){
                    continue;
                }

                Double score = personalStatistic.getTotalScore() == null ? 0 : (double)personalStatistic.getTotalScore();
                String orgId = user.getOrganizationId();

                if (org2TotalScore.containsKey(orgId)) {
                    Double totalScore = org2TotalScore.get(orgId);
                    org2TotalScore.put(orgId, totalScore + score);
                } else {
                    org2TotalScore.put(orgId, score);
                }
            }

            for (Map.Entry<String, Double> tmp : org2TotalScore.entrySet()) {
                TeamOrganizationStatisticsBo TeamOrganizationStatistics = new TeamOrganizationStatisticsBo();

                TeamOrganizationStatistics.setOrgId(tmp.getKey());
                TeamOrganizationStatistics.setOrgName(org2Name.get(tmp.getKey()));

                double AverageScore = BigDecimal.valueOf((double) tmp.getValue() / org2TotalUser.get(tmp.getKey())).setScale(2, RoundingMode.HALF_UP).doubleValue();

                TeamOrganizationStatistics.setAverageScore(AverageScore);
                rsp.add(TeamOrganizationStatistics);
            }
        }
        //按分数排序后再按组织机构名自然排序
        rsp = rsp.stream().sorted(Comparator.comparing(TeamOrganizationStatisticsBo::getAverageScore, Comparator.reverseOrder()).
                        thenComparing(TeamOrganizationStatisticsBo::getOrgName, Comparator.naturalOrder()))
                .collect(Collectors.toList());

        return rsp.size() > 5 ? rsp.subList(0,5) : rsp;
    }


    @Override
    public List<TeamPersonalStatisticsBo> getAllPersonalRankStatistics(Integer teamCategory) {
        List<TeamPersonalStatisticsBo> rsp = Lists.newArrayList();

        List<Integer> statusList = Lists.newArrayList();
        // 已完成的任务
        statusList.add(CrTask.TaskStatus.SUCCESS.getCode());
        statusList.add(CrTask.TaskStatus.FAIL.getCode());

        List<TrainingPersonalStatistics> personalStatistics = crTaskMapper.selectAllPersonalStatistics(
                statusList, BusinessTemplate.BusinessType.TRAINING_TEAM.getCode(), null, teamCategory, null, null
        );

        if (null != personalStatistics) {
            List<String> user_ids = personalStatistics.stream().map(TrainingPersonalStatistics::getUserId).collect(Collectors.toList());
            if (user_ids.size() == 0) {
                return rsp;
            }
//            Map<String, UserInfo> userid2infos = remoteUserService.getIdUserInfoMap(user_ids);
            List<UserInfo> remoteUserInfos = remoteUserService.getByIdList(user_ids);
            Map<String, UserInfo> userid2infos = remoteUserInfos.stream().collect(Collectors.toMap(UserInfo::getUserId, pc -> pc));

            for (TrainingPersonalStatistics personalStatistic : personalStatistics) {
                UserInfo userInfo = userid2infos.get(personalStatistic.getUserId());

                if (null == userInfo) {
                    continue;
                }

                TeamPersonalStatisticsBo TeamPersonalStatistics = new TeamPersonalStatisticsBo();

                TeamPersonalStatistics.setUserId(personalStatistic.getUserId());
                TeamPersonalStatistics.setUsername(userInfo.getUserName());
                TeamPersonalStatistics.setTotalScore(personalStatistic.getTotalScore());
                TeamPersonalStatistics.setName(userInfo.getName());

                rsp.add(TeamPersonalStatistics);
            }
            //按分数排序后再按照用户名自然排序
            rsp = rsp.stream().sorted(Comparator.comparing(TeamPersonalStatisticsBo::getTotalScore, Comparator.reverseOrder())
                    .thenComparing(TeamPersonalStatisticsBo::getName,Comparator.naturalOrder())).collect(Collectors.toList());

        }
        return rsp.size() > 5 ? rsp.subList(0,5) : rsp;
    }

    @Override
    public ListRsp<TeamStatisticsDetailBo> getAllStatisticsDetail(TeamTrainStatisticsDetailReq detailReq, Page page, Integer teamCategory) {
        List<TeamStatisticsDetailBo> rsp = Lists.newArrayList();

        String userName = detailReq.getPattern();
        boolean searchName = StringUtils.isNotBlank(userName);
        String organizationId = detailReq.getOrganization();
        boolean searchOrg = StringUtils.isNotBlank(organizationId);


        List<Integer> statusList = Lists.newArrayList();
        // 已完成的任务
        Integer successCode = CrTask.TaskStatus.SUCCESS.getCode();
        Integer failCode = CrTask.TaskStatus.FAIL.getCode();
        statusList.add(successCode);
        statusList.add(failCode);

        List<CrTask> personalTotalStatistics = crTaskMapper.selectByStatusListAndType(
                statusList, BusinessTemplate.BusinessType.TRAINING_TEAM.getCode(), teamCategory
        );
        //时间过滤
        if (StringUtils.isNotEmpty(detailReq.getStartTime()) && StringUtils.isNotEmpty(detailReq.getEndTime())) {
            Date startTime = java.sql.Date.valueOf(detailReq.getStartTime());
            Date endTime = java.sql.Date.valueOf(detailReq.getEndTime());
            personalTotalStatistics = personalTotalStatistics.stream().filter(personalTotalStatistic -> startTime.before(personalTotalStatistic.getCreateTime()) && personalTotalStatistic.getCreateTime().before(endTime)).collect(Collectors.toList());
        }
        Map<String, List<CrTask>> user2Tasks = new HashMap<>();
        for (CrTask personStatistic : personalTotalStatistics) {
            String userId = personStatistic.getUserId();
            List<CrTask> tmpList = user2Tasks.get(userId);
            if (null == tmpList) {
                tmpList = Lists.newArrayList();
            }
            tmpList.add(personStatistic);
            user2Tasks.put(userId, tmpList);
        }

        List<String> user_ids = Lists.newArrayList(user2Tasks.keySet());
        if (user_ids.size() == 0) {
            PageUtil.getListResult(rsp, page, 0);
        }
//        Map<String, UserInfo> userid2infos = remoteUserService.getIdUserInfoMap(user_ids);
        List<UserInfo> remoteUserInfos = remoteUserService.getByIdList(user_ids);
        Map<String, UserInfo> userid2infos = remoteUserInfos.stream().collect(Collectors.toMap(UserInfo::getUserId, pc -> pc));

        for (Map.Entry<String, List<CrTask>> tmp : user2Tasks.entrySet()) {
            String userId = tmp.getKey();

            UserInfo tmpUser = userid2infos.get(userId);

            // 用户已经不存在
            if (null == tmpUser) {
                continue;
            }

            List<CrTask> taskList = tmp.getValue();

            TeamStatisticsDetailBo tmpDetailBo = new TeamStatisticsDetailBo();
            tmpDetailBo.setName(tmpUser.getName());
            tmpDetailBo.setUsername(tmpUser.getUserName());
            tmpDetailBo.setOrganizationId(tmpUser.getOrganizationId());
            tmpDetailBo.setOrganizationData(tmpUser.getOrganizationData());
            tmpDetailBo.setRoles(tmpUser.getRoles());
            tmpDetailBo.setUserId(userId);


            tmpDetailBo.setTaskNum(taskList.size());
            Integer totalScore = taskList.stream().mapToInt(CrTask::getScore).sum();
            tmpDetailBo.setTotalScore(totalScore);

            Map<String, List<CrTask>> trainTimeMap = taskList.stream().collect(Collectors.groupingBy(CrTask::getRoomId));
            tmpDetailBo.setTrainTimes(trainTimeMap.size());

            Map<Integer, List<CrTask>> groupTaskMap = taskList.stream().collect(Collectors.groupingBy(CrTask::getStatus));
            int correctSize = groupTaskMap.get(successCode) == null ? 0 : groupTaskMap.get(successCode).size();
            double accuracy = BigDecimal.valueOf((double) correctSize / taskList.size()).setScale(2, RoundingMode.HALF_UP).doubleValue();
            tmpDetailBo.setTaskAccuracy(accuracy);

            rsp.add(tmpDetailBo);
        }

        List<TeamStatisticsDetailBo> newRsp = Lists.newArrayList();

        //按分数排序后再按照用户名自然排序
        rsp = rsp.stream().sorted(Comparator.comparing(TeamStatisticsDetailBo::getTotalScore, Comparator.reverseOrder())
                .thenComparing(TeamStatisticsDetailBo::getName,Comparator.naturalOrder())).collect(Collectors.toList());

        //获取子组织列表
        List<String> childrenList = new ArrayList<>();
        if (searchOrg) {
            childrenList.addAll(remoteOrganizationService.childrenList(organizationId, Boolean.TRUE));
        }

        // 增加综合排名
        for (int i=0;i < rsp.size();i++) {
            TeamStatisticsDetailBo tmp = rsp.get(i);
            tmp.setOverallRank(i + 1);

            if (searchName && !tmp.getName().contains(userName)){
                continue;
            }

            //过滤本组织及其子组织数据
            if (searchOrg) {
                if (!StringUtils.equals(tmp.getOrganizationId(), organizationId) && !childrenList.contains(tmp.getOrganizationId())) {
                    continue;
                }
            }
            newRsp.add(tmp);
        }

        return PageUtil.getListResult(newRsp, page, newRsp.size());
    }

    @Override
    public JSONObject getFlowAttackCount(String roomId) {
        SceneViewContext context = teamSceneService.viewScene(roomId, null);
        RoleQuery roleQuery=new RoleQuery();
        roleQuery.setRoomId(roomId);
        roleQuery.setRoleGroup(2);
        List<CrRole> crRoles = crRoleService.selectCrRoleByRoomId(roleQuery);

        List<String> userIds = new ArrayList<>();
        Map<String, List<String>> roleIpMap = Maps.newHashMap();
        Map<String, String> roleIdMap = Maps.newHashMap();

        for(CrRole role: crRoles){
            JSONObject jsonObject = JSONObject.parseObject(role.getNodes());
            List<JSONObject> visibleNodes = jsonObject.getJSONArray("assetNodes").toJavaList(JSONObject.class);
            for(JSONObject visibleNode: visibleNodes){
                List<String> idList = roleIpMap.get(role.getName());
                idList = CollectionUtils.isEmpty(idList) ? new ArrayList<>() : idList;
                idList.add(visibleNode.getString("id"));
                roleIpMap.put(role.getName(),idList);
            }
            userIds.addAll(role.getUserIdList());
            roleIdMap.put(role.getName(),role.getResourceId());
        }
        if (CollectionUtils.isEmpty(userIds)) {
            return new JSONObject();
        }
        //获取模版类型(红方/蓝方/日常)
        TeamTemplate template = teamTemplateMapper.getTeamTemplateByRoomId(roomId);
        String typeName = TeamRoom.TrainCategory.RED.getCode().equals(template.getTrainCategory()) ? "红方-" : TeamRoom.TrainCategory.BLUE.getCode().equals(template.getTrainCategory()) ? "蓝方-" : "";

        //获取角色对应完成任务
        List<CrTask> finishedTasks =crTaskMapper.selectByRoomIdAndUserIdsAndStatusList(roomId, userIds, CrTask.TaskStatus.FINISHED_STATUS_LIST);
        Map<String, List<CrTask>> roleTaskMap = StreamApiUtils.groupBy(finishedTasks, CrTask::getRoleId);

        //获取流量节点对应的攻击次数
        List<MpFlowAttackCount> flowAttackCountList = remoteSceneService.getFlowAttackCount(context.getMpSceneId());
        Map<String, Integer> attackMap = StreamApiUtils.toMap(flowAttackCountList, MpFlowAttackCount::getNodeId, MpFlowAttackCount::getTimes);

        Map<String, List<TeamCapabilityInfoRsp>> teamCapabilityInfoMap = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : roleIdMap.entrySet()) {
            String roleName = entry.getKey();
            List<TeamCapabilityInfoRsp> teamCapabilityList = CollectionUtils.isEmpty(teamCapabilityInfoMap.get(roleName)) ? new ArrayList<>() : teamCapabilityInfoMap.get(roleName);
            //攻击次数
            AtomicInteger times = new AtomicInteger();
            List<String> idList = roleIpMap.get(roleName);
            if (!CollectionUtils.isEmpty(idList)) {
                idList.forEach(item -> times.addAndGet(attackMap.get(item) == null ? 0 : attackMap.get(item)));
            }
            TeamCapabilityInfoRsp teamCapabilityInfoRsp = new TeamCapabilityInfoRsp();
            teamCapabilityInfoRsp.setTimes(times.get());
            //角色任务得分
            String roleId = roleIdMap.get(roleName);
            List<CrTask> taskList = roleTaskMap.get(roleId);
            Integer score = CollectionUtils.isEmpty(taskList) ? 0 : taskList.stream().mapToInt(CrTask::getScore).sum();
            teamCapabilityInfoRsp.setScore(score);
            teamCapabilityList.add(teamCapabilityInfoRsp);
            teamCapabilityInfoMap.put(roleName,teamCapabilityList);
        }

        List<TeamCapabilityRsp> result = new ArrayList<>();
        List<String> resultNameList = new ArrayList<>();

        for (Map.Entry<String, List<TeamCapabilityInfoRsp>> entry : teamCapabilityInfoMap.entrySet()) {
            TeamCapabilityRsp teamCapabilityRsp = new TeamCapabilityRsp();
            teamCapabilityRsp.setRoleName(typeName+entry.getKey());
            teamCapabilityRsp.setList(entry.getValue());
            result.add(teamCapabilityRsp);
            resultNameList.add(typeName+entry.getKey());
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("roleNameList",resultNameList);
        jsonObject.put("teamCapabilityList",result);

        return jsonObject;
    }

}
