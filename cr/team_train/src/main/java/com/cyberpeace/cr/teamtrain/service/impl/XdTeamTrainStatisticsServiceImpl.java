package com.cyberpeace.cr.teamtrain.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.req.TrainStatisticsDetailReq;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskMapper;
import com.cyberpeace.cr.teamtrain.controller.req.XdStatisticsReq;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamRoomMapper;
import com.cyberpeace.cr.teamtrain.service.XdTeamTrainStatisticsService;
import com.cyberpeace.cr.teamtrain.service.bo.XdTeamPersonScoreStatisticsBo;
import com.cyberpeace.cr.teamtrain.service.bo.XdTeamPersonStatisticsBo;
import com.cyberpeace.cr.teamtrain.service.bo.XdTeamPersonalStatisticsBo;
import com.cyberpeace.cr.teamtrain.service.bo.XdTeamTimesStatisticsBo;
import com.cyberpeace.cr.teamtrain.util.DateRangeUtils;
import com.cyberpeace.integratation.service.RemoteOrganizationService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * xd项目团队训练统计Service
 *
 * @author SN
 * @date 2025/09/14
 */
@Service
@Slf4j
public class XdTeamTrainStatisticsServiceImpl implements XdTeamTrainStatisticsService {

    @Autowired
    TeamRoomMapper teamRoomMapper;

    /**
     * 远程用户Service
     */
    @Autowired
    RemoteUserService remoteUserService;

    /**
     * 靶场任务Mapper
     */
    @Autowired
    CrTaskMapper crTaskMapper;

    /**
     * 远程组织Service
     */
    @Autowired
    RemoteOrganizationService remoteOrganizationService;

    /**
     * 训练场次统计
     * @return
     */
    @Override
    public List<XdTeamTimesStatisticsBo> getXdTeamTimes(XdStatisticsReq request) {
        List<XdTeamTimesStatisticsBo> rtnList = new ArrayList<>();
        List<Map<String, Object>> syList = new ArrayList<>();
        if ("year".equals(request.getType())) {
            syList = teamRoomMapper.syNumStatisticsYear(request);
        } else if ("day".equals(request.getType())) {
            syList = teamRoomMapper.syNumStatisticsDay(request);
        } else {
            syList = teamRoomMapper.syNumStatisticsMonth(request);
        }
        Map<String, Long> syMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(syList)) {
            for (Map<String, Object> temp : syList) {
                syMap.put((String)temp.get("month"), (Long)temp.get("num"));
            }
        }
        List<Map<String, Object>> kmList = new ArrayList<>();
        if ("year".equals(request.getType())) {
            kmList = teamRoomMapper.kmNumStatisticsYear(request);
        } else if ("day".equals(request.getType())) {
            kmList = teamRoomMapper.kmNumStatisticsDay(request);
        } else {
            kmList = teamRoomMapper.kmNumStatisticsMonth(request);
        }
        Map<String, Long> kmMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(kmList)) {
            for (Map<String, Object> temp : kmList) {
                kmMap.put((String)temp.get("month"), (Long)temp.get("num"));
            }
        }
        List<Map<String, Object>> zhyxList = new ArrayList<>();
        if ("year".equals(request.getType())) {
            zhyxList = teamRoomMapper.zhyxNumStatisticsYear(request);
        } else if ("day".equals(request.getType())) {
            zhyxList = teamRoomMapper.zhyxNumStatisticsDay(request);
        } else {
            zhyxList = teamRoomMapper.zhyxNumStatisticsMonth(request);
        }
        Map<String, Long> zhyxMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(zhyxList)) {
            for (Map<String, Object> temp : zhyxList) {
                zhyxMap.put((String)temp.get("month"), (Long)temp.get("num"));
            }
        }
        String startTime = request.getStartTime();
        String endTime = request.getEndTime();
        if (StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)) {
            LocalDate today = LocalDate.now();
            // 获取今年第一天
            LocalDate firstDayOfYear = today.withDayOfYear(1);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            startTime = firstDayOfYear.format(formatter);
            endTime = today.format(formatter);
        }
        List<String> timeList = new ArrayList<>();
        if ("year".equals(request.getType())) {
            timeList = DateRangeUtils.getAllYears(startTime, endTime);
        } else if ("day".equals(request.getType())) {
            timeList = DateRangeUtils.getAllDays(startTime, endTime);
        } else {
            timeList = DateRangeUtils.getAllMonths(startTime, endTime);
        }
        if (timeList.size() > 50) {
            throw new BusinessException(CrBusinessErrorCode.STATISTICS_TOO_BIG);
        }

        for (String temp : timeList) {
            XdTeamTimesStatisticsBo xdTeamTimesStatisticsBo = new XdTeamTimesStatisticsBo();
            xdTeamTimesStatisticsBo.setMonth(temp);
            xdTeamTimesStatisticsBo.setSyNum(syMap.get(temp) == null ? 0L : syMap.get(temp));
            xdTeamTimesStatisticsBo.setKmNum(kmMap.get(temp) == null ? 0L : kmMap.get(temp));
            xdTeamTimesStatisticsBo.setZhyxNum(zhyxMap.get(temp) == null ? 0L : zhyxMap.get(temp));
            rtnList.add(xdTeamTimesStatisticsBo);
        }
        return rtnList;
    }

    /**
     * 训练人数统计
     * @return
     */
    @Override
    public List<XdTeamPersonStatisticsBo> getXdTeamPersonNum(XdStatisticsReq request) {
        List<XdTeamPersonStatisticsBo> rtnList = new ArrayList<>();
        List<Map<String, Object>> syList = new ArrayList<>();
        if ("year".equals(request.getType())) {
            syList = teamRoomMapper.syPersonNumStatisticsYear(request);
        } else if ("day".equals(request.getType())) {
            syList = teamRoomMapper.syPersonNumStatisticsDay(request);
        } else {
            syList = teamRoomMapper.syPersonNumStatisticsMonth(request);
        }
        Map<String, Long> syMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(syList)) {
            for (Map<String, Object> temp : syList) {
                syMap.put((String)temp.get("month"), ((BigDecimal)temp.get("person_num")).longValue());
            }
        }
        List<Map<String, Object>> kmList = new ArrayList<>();
        if ("year".equals(request.getType())) {
            kmList = teamRoomMapper.kmPersonNumStatisticsYear(request);
        } else if ("day".equals(request.getType())) {
            kmList = teamRoomMapper.kmPersonNumStatisticsDay(request);
        } else {
            kmList = teamRoomMapper.kmPersonNumStatisticsMonth(request);
        }
        Map<String, Long> kmMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(kmList)) {
            for (Map<String, Object> temp : kmList) {
                kmMap.put((String)temp.get("month"), ((BigDecimal)temp.get("person_num")).longValue());
            }
        }
        List<Map<String, Object>> zhyxList = new ArrayList<>();
        if ("year".equals(request.getType())) {
            zhyxList = teamRoomMapper.zhyxPersonNumStatisticsYear(request);
        } else if ("day".equals(request.getType())) {
            zhyxList = teamRoomMapper.zhyxPersonNumStatisticsDay(request);
        } else {
            zhyxList = teamRoomMapper.zhyxPersonNumStatisticsMonth(request);
        }
        Map<String, Long> zhyxMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(zhyxList)) {
            for (Map<String, Object> temp : zhyxList) {
                zhyxMap.put((String)temp.get("month"), ((BigDecimal)temp.get("person_num")).longValue());
            }
        }

        String startTime = request.getStartTime();
        String endTime = request.getEndTime();
        if (StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)) {
            LocalDate today = LocalDate.now();
            // 获取今年第一天
            LocalDate firstDayOfYear = today.withDayOfYear(1);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            startTime = firstDayOfYear.format(formatter);
            endTime = today.format(formatter);
        }
        List<String> timeList = new ArrayList<>();
        if ("year".equals(request.getType())) {
            timeList = DateRangeUtils.getAllYears(startTime, endTime);
        } else if ("day".equals(request.getType())) {
            timeList = DateRangeUtils.getAllDays(startTime, endTime);
        } else {
            timeList = DateRangeUtils.getAllMonths(startTime, endTime);
        }
        if (timeList.size() > 50) {
            throw new BusinessException(CrBusinessErrorCode.STATISTICS_TOO_BIG);
        }
        for (String temp : timeList) {
            XdTeamPersonStatisticsBo xdTeamPersonStatisticsBo = new XdTeamPersonStatisticsBo();
            xdTeamPersonStatisticsBo.setMonth(temp);
            xdTeamPersonStatisticsBo.setSyNum(syMap.get(temp) == null ? 0L : syMap.get(temp));
            xdTeamPersonStatisticsBo.setKmNum(kmMap.get(temp) == null ? 0L : kmMap.get(temp));
            xdTeamPersonStatisticsBo.setZhyxNum(zhyxMap.get(temp) == null ? 0L : zhyxMap.get(temp));
            rtnList.add(xdTeamPersonStatisticsBo);
        }
        return rtnList;
    }

    @Override
    public List<XdTeamPersonScoreStatisticsBo> getPersonScore(XdStatisticsReq request) {
        try {
            List<XdTeamPersonScoreStatisticsBo> rtn = new ArrayList<>();

            List<Map<String, Object>> totalScoreList = teamRoomMapper.totalScoreStatistics(request);
            if (CollectionUtils.isEmpty(totalScoreList)) {
                return rtn;
            }
            List<String> userIds = new ArrayList<>();
            for (Map<String, Object> temp : totalScoreList) {
                userIds.add((String)temp.get("user_id"));
            }
            List<UserInfo> remoteUserInfos = remoteUserService.getByIdList(userIds);
            Map<String, UserInfo> userid2infos = remoteUserInfos.stream().collect(Collectors.toMap(UserInfo::getUserId, pc -> pc));

            for (Map<String, Object> temp : totalScoreList) {
                XdTeamPersonScoreStatisticsBo xdTeamPersonScoreStatisticsBo = new XdTeamPersonScoreStatisticsBo();
                xdTeamPersonScoreStatisticsBo.setUserId((String)temp.get("user_id"));
                xdTeamPersonScoreStatisticsBo.setTotalScore((Double) temp.get("total_score"));
                UserInfo userInfo = userid2infos.get((String)temp.get("user_id"));
                if (userInfo != null) {
                    xdTeamPersonScoreStatisticsBo.setUsername(userInfo.getUserName());
                    xdTeamPersonScoreStatisticsBo.setName(userInfo.getName());
                }
                rtn.add(xdTeamPersonScoreStatisticsBo);
            }

            return rtn;
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<XdTeamPersonScoreStatisticsBo> getPersonScoreSy(XdStatisticsReq request) {
        try {
            List<XdTeamPersonScoreStatisticsBo> rtn = new ArrayList<>();
            List<Map<String, Object>> totalScoreList = teamRoomMapper.totalScoreSyStatistics(request);
            if (CollectionUtils.isEmpty(totalScoreList)) {
                return rtn;
            }
            List<String> userIds = new ArrayList<>();
            for (Map<String, Object> temp : totalScoreList) {
                userIds.add((String)temp.get("user_id"));
            }
            List<UserInfo> remoteUserInfos = remoteUserService.getByIdList(userIds);
            Map<String, UserInfo> userid2infos = remoteUserInfos.stream().collect(Collectors.toMap(UserInfo::getUserId, pc -> pc));

            for (Map<String, Object> temp : totalScoreList) {
                XdTeamPersonScoreStatisticsBo xdTeamPersonScoreStatisticsBo = new XdTeamPersonScoreStatisticsBo();
                xdTeamPersonScoreStatisticsBo.setUserId((String)temp.get("user_id"));
                xdTeamPersonScoreStatisticsBo.setTotalScore((Double) temp.get("total_score"));
                UserInfo userInfo = userid2infos.get((String)temp.get("user_id"));
                if (userInfo != null) {
                    xdTeamPersonScoreStatisticsBo.setUsername(userInfo.getUserName());
                    xdTeamPersonScoreStatisticsBo.setName(userInfo.getName());
                }
                rtn.add(xdTeamPersonScoreStatisticsBo);
            }
            return rtn;
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<XdTeamPersonScoreStatisticsBo> getPersonScoreKm(XdStatisticsReq request) {
        try {
            List<XdTeamPersonScoreStatisticsBo> rtn = new ArrayList<>();
            List<Map<String, Object>> totalScoreList = teamRoomMapper.totalScoreKmStatistics(request);
            if (CollectionUtils.isEmpty(totalScoreList)) {
                return rtn;
            }
            List<String> userIds = new ArrayList<>();
            for (Map<String, Object> temp : totalScoreList) {
                userIds.add((String)temp.get("user_id"));
            }
            List<UserInfo> remoteUserInfos = remoteUserService.getByIdList(userIds);
            Map<String, UserInfo> userid2infos = remoteUserInfos.stream().collect(Collectors.toMap(UserInfo::getUserId, pc -> pc));

            for (Map<String, Object> temp : totalScoreList) {
                XdTeamPersonScoreStatisticsBo xdTeamPersonScoreStatisticsBo = new XdTeamPersonScoreStatisticsBo();
                xdTeamPersonScoreStatisticsBo.setUserId((String)temp.get("user_id"));
                xdTeamPersonScoreStatisticsBo.setTotalScore((Double) temp.get("total_score"));
                UserInfo userInfo = userid2infos.get((String)temp.get("user_id"));
                if (userInfo != null) {
                    xdTeamPersonScoreStatisticsBo.setUsername(userInfo.getUserName());
                    xdTeamPersonScoreStatisticsBo.setName(userInfo.getName());
                }
                rtn.add(xdTeamPersonScoreStatisticsBo);
            }
            return rtn;
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<XdTeamPersonScoreStatisticsBo> getPersonScoreZhyx(XdStatisticsReq request) {
        try {
            List<XdTeamPersonScoreStatisticsBo> rtn = new ArrayList<>();
            List<Map<String, Object>> totalScoreList = teamRoomMapper.totalScoreZhyxStatistics(request);
            if (CollectionUtils.isEmpty(totalScoreList)) {
                return rtn;
            }
            List<String> userIds = new ArrayList<>();
            for (Map<String, Object> temp : totalScoreList) {
                userIds.add((String)temp.get("user_id"));
            }
            List<UserInfo> remoteUserInfos = remoteUserService.getByIdList(userIds);
            Map<String, UserInfo> userid2infos = remoteUserInfos.stream().collect(Collectors.toMap(UserInfo::getUserId, pc -> pc));

            for (Map<String, Object> temp : totalScoreList) {
                XdTeamPersonScoreStatisticsBo xdTeamPersonScoreStatisticsBo = new XdTeamPersonScoreStatisticsBo();
                xdTeamPersonScoreStatisticsBo.setUserId((String)temp.get("user_id"));
                xdTeamPersonScoreStatisticsBo.setTotalScore((Double) temp.get("total_score"));
                UserInfo userInfo = userid2infos.get((String)temp.get("user_id"));
                if (userInfo != null) {
                    xdTeamPersonScoreStatisticsBo.setUsername(userInfo.getUserName());
                    xdTeamPersonScoreStatisticsBo.setName(userInfo.getName());
                }
                rtn.add(xdTeamPersonScoreStatisticsBo);
            }
            return rtn;
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ArrayList<>();
        }
    }


    @Override
    public ListRsp<XdTeamPersonalStatisticsBo> getAllStatisticsDetail(TrainStatisticsDetailReq detailReq, Page page, Integer code) {
        List<XdTeamPersonalStatisticsBo> rsp = Lists.newArrayList();

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

            XdTeamPersonalStatisticsBo xdTeamPersonalStatisticsBo = new XdTeamPersonalStatisticsBo();
            xdTeamPersonalStatisticsBo.setName(tmpUser.getName());
            xdTeamPersonalStatisticsBo.setUsername(tmpUser.getUserName());
            xdTeamPersonalStatisticsBo.setOrganizationData(tmpUser.getOrganizationData());
            xdTeamPersonalStatisticsBo.setOrganizationId(tmpUser.getOrganizationId());
            xdTeamPersonalStatisticsBo.setUserId(userId);
            xdTeamPersonalStatisticsBo.setRoles(tmpUser.getRoles());

            xdTeamPersonalStatisticsBo.setTaskNum(taskList.size());
            Integer totalScore = taskList.stream().mapToInt(CrTask::getScore).sum();
            xdTeamPersonalStatisticsBo.setTotalScore(totalScore);

            Map<String, List<CrTask>> trainTimeMap = taskList.stream().collect(Collectors.groupingBy(CrTask::getRoomId));
            xdTeamPersonalStatisticsBo.setTrainTimes(trainTimeMap.size());

            List<String> roomIds = trainTimeMap.keySet().stream().collect(Collectors.toList());
            List<TeamRoom> roomList = teamRoomMapper.selectAllByIds(roomIds);
            if (CollectionUtils.isNotEmpty(roomList)) {
                Integer syTimes = Math.toIntExact(roomList.stream().filter(obj -> (obj.getXdType() == 1 || obj.getXdType() == 2 || obj.getXdType() == 3 || obj.getXdType() == 4 || obj.getXdType() == 5 || obj.getXdType() == 6 || obj.getXdType() == 7)).count());
                xdTeamPersonalStatisticsBo.setSyTimes(syTimes);
                Integer kmTimes = Math.toIntExact(roomList.stream().filter(obj -> (obj.getXdType() == 8 || obj.getXdType() == 9 || obj.getXdType() == 10 || obj.getXdType() == 11 || obj.getXdType() == 12 || obj.getXdType() == 13 || obj.getXdType() == 14)).count());
                xdTeamPersonalStatisticsBo.setKmTimes(kmTimes);
                Integer zhyxTimes = Math.toIntExact(roomList.stream().filter(obj -> (obj.getXdType() == 15)).count());
                xdTeamPersonalStatisticsBo.setZhyxTimes(zhyxTimes);
            }

            Map<Integer, List<CrTask>> groupTaskMap = taskList.stream().collect(Collectors.groupingBy(CrTask::getStatus));
            int correctSize = groupTaskMap.get(successCode) == null ? 0 : groupTaskMap.get(successCode).size();
            double accuracy = BigDecimal.valueOf((double) correctSize / taskList.size()).setScale(2, RoundingMode.HALF_UP).doubleValue();
            xdTeamPersonalStatisticsBo.setTaskAccuracy(accuracy);

            rsp.add(xdTeamPersonalStatisticsBo);
        }

        List<XdTeamPersonalStatisticsBo> newRsp = Lists.newArrayList();

        //按分数排序后再按照用户名自然排序
        rsp = rsp.stream().sorted(Comparator.comparing(XdTeamPersonalStatisticsBo::getTotalScore, Comparator.reverseOrder())
                .thenComparing(XdTeamPersonalStatisticsBo::getName, Comparator.naturalOrder())).collect(Collectors.toList());

        //获取子组织列表
        List<String> childrenList = new ArrayList<>();
        if (searchOrg) {
            childrenList.addAll(remoteOrganizationService.childrenList(organizationId, Boolean.TRUE));
        }

        // 增加综合排名
        for (int i=0;i < rsp.size();i++) {
            XdTeamPersonalStatisticsBo tmp = rsp.get(i);
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
