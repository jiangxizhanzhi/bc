package com.cyberpeace.cr.redvsblue.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskMapper;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingPersonalStatistics;
import com.cyberpeace.cr.redvsblue.controller.req.RbStatisticsDetailReq;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbRoomMapper;
import com.cyberpeace.cr.redvsblue.service.RbStatisticsService;
import com.cyberpeace.cr.redvsblue.service.bo.RbOrganizationStatisticsBo;
import com.cyberpeace.cr.redvsblue.service.bo.RbPersonalStatisticsBo;
import com.cyberpeace.cr.redvsblue.service.bo.RbStatisticsDetailBo;
import com.cyberpeace.integratation.service.RemoteOrganizationService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RbStatisticsServiceImpl implements RbStatisticsService {

    @Autowired
    CrTaskMapper crTaskMapper;

    @Autowired
    RemoteUserService remoteUserService;

    @Autowired
    RemoteOrganizationService remoteOrganizationService;

    @Autowired
    RbRoomMapper rbRoomMapper;


    @Override
    public List<RbOrganizationStatisticsBo> getOrganizationRankStatistics() {
        List<RbOrganizationStatisticsBo> rsp = Lists.newArrayList();

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
                statusList, BusinessTemplate.BusinessType.RED_VS_BLUE.getCode(), null, null
        );

        if (null != personalStatistics) {
            for (TrainingPersonalStatistics personalStatistic : personalStatistics) {
                String user_id = personalStatistic.getUserId();

                // 判断user是否还存在
                UserInfo user = id2User.get(user_id);
                if (null == user){
                    continue;
                }

                String orgId = user.getOrganizationId();
                Double score = personalStatistic.getTotalScore() == null ? 0 : (double)personalStatistic.getTotalScore();

                if (org2TotalScore.containsKey(orgId)) {
                    Double totalScore = org2TotalScore.get(orgId);
                    org2TotalScore.put(orgId, totalScore + score);
                } else {
                    org2TotalScore.put(orgId, score);
                }
            }

            for (Map.Entry<String, Double> tmp : org2TotalScore.entrySet()) {
                RbOrganizationStatisticsBo RbOrganizationStatistics = new RbOrganizationStatisticsBo();

                RbOrganizationStatistics.setOrgId(tmp.getKey());
                RbOrganizationStatistics.setOrgName(org2Name.get(tmp.getKey()));

                double AverageScore = BigDecimal.valueOf((double) tmp.getValue() / org2TotalUser.get(tmp.getKey())).setScale(2, RoundingMode.HALF_UP).doubleValue();

                RbOrganizationStatistics.setAverageScore(AverageScore);
                rsp.add(RbOrganizationStatistics);
            }
        }
        //按分数排序后再按照组织机构名自然排序
        rsp = rsp.stream().sorted(Comparator.comparing(RbOrganizationStatisticsBo::getAverageScore, Comparator.reverseOrder()).
                        thenComparing(RbOrganizationStatisticsBo::getOrgName, Comparator.naturalOrder())).collect(Collectors.toList());

        return rsp.size() > 5 ? rsp.subList(0,5) : rsp;
    }


    @Override
    public List<RbPersonalStatisticsBo> getAllPersonalRankStatistics() {
        List<RbPersonalStatisticsBo> rsp = Lists.newArrayList();

        List<Integer> statusList = Lists.newArrayList();
        // 已完成的任务
        statusList.add(CrTask.TaskStatus.SUCCESS.getCode());
        statusList.add(CrTask.TaskStatus.FAIL.getCode());

        List<TrainingPersonalStatistics> personalStatistics = crTaskMapper.selectAllPersonalStatistics(
                statusList, BusinessTemplate.BusinessType.RED_VS_BLUE.getCode(), null, null
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

                RbPersonalStatisticsBo RbPersonalStatistics = new RbPersonalStatisticsBo();
                RbPersonalStatistics.setName(userInfo.getName());
                RbPersonalStatistics.setUserId(personalStatistic.getUserId());
                RbPersonalStatistics.setTotalScore(personalStatistic.getTotalScore());

                RbPersonalStatistics.setUsername(userInfo.getUserName());
                rsp.add(RbPersonalStatistics);
            }
            //按分数排序后再按照用户名自然排序
            rsp = rsp.stream().sorted(Comparator.comparing(RbPersonalStatisticsBo::getTotalScore, Comparator.reverseOrder()).
                    thenComparing(RbPersonalStatisticsBo::getName, Comparator.naturalOrder())).collect(Collectors.toList());

        }

        return rsp.size() > 5 ? rsp.subList(0,5) : rsp;
    }

    @Override
    public ListRsp<RbStatisticsDetailBo> getAllStatisticsDetail(RbStatisticsDetailReq detailReq, Page page) {
        List<RbStatisticsDetailBo> rsp = Lists.newArrayList();

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
                statusList, BusinessTemplate.BusinessType.RED_VS_BLUE.getCode(), null
        );
        //时间过滤
        if (StringUtils.isNotEmpty(detailReq.getStartTime()) && StringUtils.isNotEmpty(detailReq.getEndTime())) {
            Date startTime = java.sql.Date.valueOf(detailReq.getStartTime());
            Date endTime = java.sql.Date.valueOf(detailReq.getEndTime());
            personalTotalStatistics = personalTotalStatistics.stream().filter(personalTotalStatistic -> startTime.before(personalTotalStatistic.getCreateTime()) && personalTotalStatistic.getCreateTime().before(endTime)).collect(Collectors.toList());
        }
        Map<String, List<CrTask>> user2Tasks = new HashMap<>();
        List<String> roomIds = StreamApiUtils.map(personalTotalStatistics, CrTask::getRoomId);
        Map<String, RbRoom> rbRoomMap = new HashMap<>();
        if (roomIds.size() != 0) {
            List<RbRoom> rbRoomList = rbRoomMapper.selectBatchIds(roomIds);
            rbRoomMap = StreamApiUtils.toMap(rbRoomList, RbRoom::getResourceId, rbRoom -> rbRoom);
        }
        for (CrTask personStatistic : personalTotalStatistics) {
            if (rbRoomMap.get(personStatistic.getRoomId()) == null) {
                continue;
            }
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

            RbStatisticsDetailBo tmpDetailBo = new RbStatisticsDetailBo();
            tmpDetailBo.setName(tmpUser.getName());
            tmpDetailBo.setRoles(tmpUser.getRoles());
            tmpDetailBo.setUsername(tmpUser.getUserName());
            tmpDetailBo.setOrganizationData(tmpUser.getOrganizationData());
            tmpDetailBo.setOrganizationId(tmpUser.getOrganizationId());

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

        List<RbStatisticsDetailBo> newRsp = Lists.newArrayList();

        //按分数排序后再按照用户名自然排序
        rsp = rsp.stream().sorted(Comparator.comparing(RbStatisticsDetailBo::getTotalScore, Comparator.reverseOrder()).
                thenComparing(RbStatisticsDetailBo::getName, Comparator.naturalOrder())).collect(Collectors.toList());

        //获取子组织列表
        List<String> childrenList = new ArrayList<>();
        if (searchOrg) {
            childrenList.addAll(remoteOrganizationService.childrenList(organizationId, Boolean.TRUE));
        }

        // 增加综合排名
        for (int i=0;i < rsp.size();i++) {
            RbStatisticsDetailBo tmp = rsp.get(i);
            tmp.setOverallRank(i + 1);
//            rsp.set(i, tmp);

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

}
