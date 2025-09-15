package com.cyberpeace.cr.teamtrain.service.impl;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamRoomMapper;
import com.cyberpeace.cr.teamtrain.service.XdTeamTrainStatisticsService;
import com.cyberpeace.cr.teamtrain.service.bo.XdTeamPersonScoreStatisticsBo;
import com.cyberpeace.cr.teamtrain.service.bo.XdTeamPersonStatisticsBo;
import com.cyberpeace.cr.teamtrain.service.bo.XdTeamTimesStatisticsBo;
import com.cyberpeace.integratation.service.RemoteUserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * xd项目团队训练统计Service
 *
 * @author SN
 * @date 2025/09/14
 */
@Service
public class XdTeamTrainStatisticsServiceImpl implements XdTeamTrainStatisticsService {

    @Autowired
    TeamRoomMapper teamRoomMapper;

    /**
     * 远程用户Service
     */
    @Autowired
    RemoteUserService remoteUserService;

    /**
     * 训练场次统计
     * @return
     */
    @Override
    public List<XdTeamTimesStatisticsBo> getXdTeamTimes() {
        List<XdTeamTimesStatisticsBo> rtnList = new ArrayList<>();
        List<Map<String, Object>> syList = teamRoomMapper.syNumStatistics();
        Map<Integer, Long> syMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(syList)) {
            for (Map<String, Object> temp : syList) {
                syMap.put((Integer)temp.get("month"), (Long)temp.get("num"));
            }
        }
        List<Map<String, Object>> kmList = teamRoomMapper.kmNumStatistics();
        Map<Integer, Long> kmMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(kmList)) {
            for (Map<String, Object> temp : kmList) {
                kmMap.put((Integer)temp.get("month"), (Long)temp.get("num"));
            }
        }
        List<Map<String, Object>> zhyxList = teamRoomMapper.zhyxNumStatistics();
        Map<Integer, Long> zhyxMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(zhyxList)) {
            for (Map<String, Object> temp : zhyxList) {
                zhyxMap.put((Integer)temp.get("month"), (Long)temp.get("num"));
            }
        }
        for (int i=1; i<=12; i++) {
            XdTeamTimesStatisticsBo xdTeamTimesStatisticsBo = new XdTeamTimesStatisticsBo();
            xdTeamTimesStatisticsBo.setMonth(i);
            xdTeamTimesStatisticsBo.setSyNum(syMap.get(i) == null ? 0L : syMap.get(i));
            xdTeamTimesStatisticsBo.setKmNum(kmMap.get(i) == null ? 0L : kmMap.get(i));
            xdTeamTimesStatisticsBo.setZhyxNum(zhyxMap.get(i) == null ? 0L : zhyxMap.get(i));
            rtnList.add(xdTeamTimesStatisticsBo);
        }
        return rtnList;
    }

    /**
     * 训练人数统计
     * @return
     */
    @Override
    public List<XdTeamPersonStatisticsBo> getXdTeamPersonNum() {
        List<XdTeamPersonStatisticsBo> rtnList = new ArrayList<>();
        List<Map<String, Object>> syList = teamRoomMapper.syPersonNumStatistics();
        Map<Integer, Long> syMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(syList)) {
            for (Map<String, Object> temp : syList) {
                syMap.put((Integer)temp.get("month"), ((BigDecimal)temp.get("person_num")).longValue());
            }
        }
        List<Map<String, Object>> kmList = teamRoomMapper.kmPersonNumStatistics();
        Map<Integer, Long> kmMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(kmList)) {
            for (Map<String, Object> temp : kmList) {
                kmMap.put((Integer)temp.get("month"), ((BigDecimal)temp.get("person_num")).longValue());
            }
        }
        List<Map<String, Object>> zhyxList = teamRoomMapper.zhyxPersonNumStatistics();
        Map<Integer, Long> zhyxMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(zhyxList)) {
            for (Map<String, Object> temp : zhyxList) {
                zhyxMap.put((Integer)temp.get("month"), ((BigDecimal)temp.get("person_num")).longValue());
            }
        }
        for (int i=1; i<=12; i++) {
            XdTeamPersonStatisticsBo xdTeamPersonStatisticsBo = new XdTeamPersonStatisticsBo();
            xdTeamPersonStatisticsBo.setMonth(i);
            xdTeamPersonStatisticsBo.setSyNum(syMap.get(i) == null ? 0L : syMap.get(i));
            xdTeamPersonStatisticsBo.setKmNum(kmMap.get(i) == null ? 0L : kmMap.get(i));
            xdTeamPersonStatisticsBo.setZhyxNum(zhyxMap.get(i) == null ? 0L : zhyxMap.get(i));
            rtnList.add(xdTeamPersonStatisticsBo);
        }
        return rtnList;
    }

    @Override
    public List<XdTeamPersonScoreStatisticsBo> getPersonScore() {
        List<XdTeamPersonScoreStatisticsBo> rtn = new ArrayList<>();
        List<Map<String, Object>> totalScoreList = teamRoomMapper.totalScoreStatistics();
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
            xdTeamPersonScoreStatisticsBo.setTotalScore((Integer) temp.get("total_score"));
            UserInfo userInfo = userid2infos.get((String)temp.get("user_id"));
            if (userInfo != null) {
                xdTeamPersonScoreStatisticsBo.setUsername(userInfo.getUserName());
                xdTeamPersonScoreStatisticsBo.setName(userInfo.getName());
            }
            rtn.add(xdTeamPersonScoreStatisticsBo);
        }
        return rtn;
    }

    @Override
    public List<XdTeamPersonScoreStatisticsBo> getPersonScoreSy() {
        List<XdTeamPersonScoreStatisticsBo> rtn = new ArrayList<>();
        List<Map<String, Object>> totalScoreList = teamRoomMapper.totalScoreSyStatistics();
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
            xdTeamPersonScoreStatisticsBo.setTotalScore((Integer) temp.get("total_score"));
            UserInfo userInfo = userid2infos.get((String)temp.get("user_id"));
            if (userInfo != null) {
                xdTeamPersonScoreStatisticsBo.setUsername(userInfo.getUserName());
                xdTeamPersonScoreStatisticsBo.setName(userInfo.getName());
            }
            rtn.add(xdTeamPersonScoreStatisticsBo);
        }
        return rtn;
    }

    @Override
    public List<XdTeamPersonScoreStatisticsBo> getPersonScoreKm() {
        List<XdTeamPersonScoreStatisticsBo> rtn = new ArrayList<>();
        List<Map<String, Object>> totalScoreList = teamRoomMapper.totalScoreKmStatistics();
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
            xdTeamPersonScoreStatisticsBo.setTotalScore((Integer) temp.get("total_score"));
            UserInfo userInfo = userid2infos.get((String)temp.get("user_id"));
            if (userInfo != null) {
                xdTeamPersonScoreStatisticsBo.setUsername(userInfo.getUserName());
                xdTeamPersonScoreStatisticsBo.setName(userInfo.getName());
            }
            rtn.add(xdTeamPersonScoreStatisticsBo);
        }
        return rtn;
    }

    @Override
    public List<XdTeamPersonScoreStatisticsBo> getPersonScoreZhyx() {
        List<XdTeamPersonScoreStatisticsBo> rtn = new ArrayList<>();
        List<Map<String, Object>> totalScoreList = teamRoomMapper.totalScoreZhyxStatistics();
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
            xdTeamPersonScoreStatisticsBo.setTotalScore((Integer) temp.get("total_score"));
            UserInfo userInfo = userid2infos.get((String)temp.get("user_id"));
            if (userInfo != null) {
                xdTeamPersonScoreStatisticsBo.setUsername(userInfo.getUserName());
                xdTeamPersonScoreStatisticsBo.setName(userInfo.getName());
            }
            rtn.add(xdTeamPersonScoreStatisticsBo);
        }
        return rtn;
    }

}
