package com.cyberpeace.cr.singletrain.service.impl;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.enums.BusinessType;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskMapper;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingPersonalStatistics;
import com.cyberpeace.cr.singletrain.service.SingleTrainStatisticsService;
import com.cyberpeace.cr.singletrain.service.bo.SingleOrganizationStatisticsBo;
import com.cyberpeace.cr.singletrain.service.bo.SinglePersonalStatisticsBo;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.integratation.service.RemoteOrganizationService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SingleTrainStatisticsServiceImpl implements SingleTrainStatisticsService {

    @Autowired
    CrTaskMapper crTaskMapper;

    @Autowired
    RemoteUserService remoteUserService;

    @Autowired
    RemoteOrganizationService remoteOrganizationService;

    @Autowired
    private UtilService utilService;

    @Override
    public List<SingleOrganizationStatisticsBo> getOrganizationRankStatistics() {
        List<SingleOrganizationStatisticsBo> rsp = Lists.newArrayList();

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
		if (userIds.isEmpty()) {
            return rsp;
        }


        List<Integer> statusList = Lists.newArrayList();
        // 已完成的任务
        statusList.add(CrTask.SingleTaskStatus.SUCCESS.getCode());
        statusList.add(CrTask.SingleTaskStatus.FAIL.getCode());

        List<TrainingPersonalStatistics> personalStatistics = crTaskMapper.selectAllPersonalStatistics(
                statusList, BusinessType.TRAINING_SINGLE.getCode(), null
        );

        if (null != personalStatistics) {
            for (TrainingPersonalStatistics personalStatistic : personalStatistics) {
                String userId = personalStatistic.getUserId();

                // 判断user是否还存在
                UserInfo user = id2User.get(userId);
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
                SingleOrganizationStatisticsBo singleOrganizationStatistics = new SingleOrganizationStatisticsBo();

                singleOrganizationStatistics.setOrgId(tmp.getKey());
                singleOrganizationStatistics.setOrgName(org2Name.get(tmp.getKey()));

                double averageScore = BigDecimal.valueOf(tmp.getValue() / org2TotalUser.get(tmp.getKey())).setScale(2, RoundingMode.HALF_UP).doubleValue();

                singleOrganizationStatistics.setAverageScore(averageScore);
                rsp.add(singleOrganizationStatistics);
            }
        }
        //按分数排序后再按照组织机构自然排序
        rsp = rsp.stream().sorted(Comparator.comparing(SingleOrganizationStatisticsBo::getAverageScore, Comparator.reverseOrder()).
                        thenComparing(SingleOrganizationStatisticsBo::getOrgName, Comparator.naturalOrder())).collect(Collectors.toList());

        return rsp.size() > 5 ? rsp.subList(0, 5) : rsp;
    }


    @Override
    public List<SinglePersonalStatisticsBo> getAllPersonalRankStatistics() {
        List<SinglePersonalStatisticsBo> rsp = Lists.newArrayList();

        List<Integer> statusList = Lists.newArrayList();
        // 已完成的任务
        statusList.add(CrTask.SingleTaskStatus.SUCCESS.getCode());
        statusList.add(CrTask.SingleTaskStatus.FAIL.getCode());

        List<TrainingPersonalStatistics> personalStatistics = crTaskMapper.selectAllPersonalStatistics(
                statusList, BusinessType.TRAINING_SINGLE.getCode(), null
        );

        if (null != personalStatistics) {
            List<String> userIds = personalStatistics.stream().map(TrainingPersonalStatistics::getUserId).collect(Collectors.toList());
			if (userIds.isEmpty()) {
                return rsp;
            }

            List<UserInfo> remoteUserInfos = remoteUserService.getByIdList(userIds);
            Map<String, UserInfo> userid2infos = remoteUserInfos.stream().collect(Collectors.toMap(UserInfo::getUserId, pc -> pc));

            for (TrainingPersonalStatistics personalStatistic : personalStatistics) {
                UserInfo userInfo = userid2infos.get(personalStatistic.getUserId());

                if (null == userInfo) {
                    continue;
                }

                SinglePersonalStatisticsBo singlePersonalStatistics = new SinglePersonalStatisticsBo();
                singlePersonalStatistics.setTotalScore(personalStatistic.getTotalScore());
                singlePersonalStatistics.setUserId(personalStatistic.getUserId());
                singlePersonalStatistics.setName(userInfo.getName());
                singlePersonalStatistics.setUsername(userInfo.getUserName());
                rsp.add(singlePersonalStatistics);

            }
            //按分数排序后再按照用户名自然排序
            rsp = rsp.stream().sorted(Comparator.comparing(SinglePersonalStatisticsBo::getTotalScore, Comparator.reverseOrder())
                    .thenComparing(SinglePersonalStatisticsBo::getName, Comparator.naturalOrder())).collect(Collectors.toList());

        }
        return rsp.size() > 5 ? rsp.subList(0,5) : rsp;
    }
}
