package com.cyberpeace.cr.crbase.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.controller.req.TrainStatisticsDetailReq;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskMapper;
import com.cyberpeace.cr.crbase.service.TrainStatisticsService;
import com.cyberpeace.cr.crbase.service.context.statistics.StatisticsDetail;
import com.cyberpeace.integratation.service.RemoteOrganizationService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;

@Service
public class TrainStatisticsServiceImpl implements TrainStatisticsService {
    @Autowired
    CrTaskMapper crTaskMapper;

    @Autowired
    RemoteUserService remoteUserService;

    @Autowired
    RemoteOrganizationService remoteOrganizationService;
    @Override
    public ListRsp<StatisticsDetail> getAllStatisticsDetail(TrainStatisticsDetailReq detailReq, Page page,Integer code) {
        List<StatisticsDetail> rsp = Lists.newArrayList();

        String userName = detailReq.getPattern();
        boolean searchName = StringUtils.isNotBlank(userName);
        String organizationId = detailReq.getOrganization();
        boolean searchOrg = StringUtils.isNotBlank(organizationId);


        List<Integer> statusList = Lists.newArrayList();
        // 已完成的任务
        Integer successCode = CrTask.SingleTaskStatus.SUCCESS.getCode();
        Integer failCode = CrTask.SingleTaskStatus.FAIL.getCode();
        statusList.add(successCode);
        statusList.add(failCode);

        List<CrTask> personalTotalStatistics = crTaskMapper.selectByStatusListAndType(
                statusList, code
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
		if (user_ids.isEmpty()) {
            PageUtil.getListResult(rsp, page, 0);
        }
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

            StatisticsDetail tmpDetailBo = new StatisticsDetail();
            tmpDetailBo.setName(tmpUser.getName());
            tmpDetailBo.setUsername(tmpUser.getUserName());
            tmpDetailBo.setOrganizationData(tmpUser.getOrganizationData());
            tmpDetailBo.setOrganizationId(tmpUser.getOrganizationId());
            tmpDetailBo.setUserId(userId);
            tmpDetailBo.setRoles(tmpUser.getRoles());



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

        List<StatisticsDetail> newRsp = Lists.newArrayList();

        //按分数排序后再按照用户名自然排序
        rsp = rsp.stream().sorted(Comparator.comparing(StatisticsDetail::getTotalScore, Comparator.reverseOrder())
                .thenComparing(StatisticsDetail::getName, Comparator.naturalOrder())).collect(Collectors.toList());

        //获取子组织列表
        List<String> childrenList = new ArrayList<>();
        if (searchOrg) {
            childrenList.addAll(remoteOrganizationService.childrenList(organizationId, Boolean.TRUE));
        }

        // 增加综合排名
        for (int i=0;i < rsp.size();i++) {
            StatisticsDetail tmp = rsp.get(i);
            tmp.setOverallRank(i + 1);

            if (searchName && !tmp.getName().contains(userName)){
                continue;
            }

			// 过滤本组织及其子组织数据
			if (searchOrg && !StringUtils.equals(tmp.getOrganizationId(), organizationId)
					&& !childrenList.contains(tmp.getOrganizationId())) {
				continue;
			}
			newRsp.add(tmp);
		}

        return PageUtil.getListResult(newRsp, page, newRsp.size());
    }
}
