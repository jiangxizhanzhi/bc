package com.cyberpeace.cr.crbase.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.cyberpeace.common.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.entity.CrRole;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.ScreenService;
import com.cyberpeace.cr.crbase.service.context.screen.ScreenRecordRsp;
import com.cyberpeace.cr.crbase.service.context.screen.ScreenRecordUserRole;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.service.RemoteSceneService;

/**
 * 屏幕Service实现
 *
 * @author SN
 * @date 2024/11/13
 */
@Slf4j
@Service
public class ScreenServiceImpl implements ScreenService {
    /**
     * 远程场景Service
     */
    @Autowired
    private RemoteSceneService remoteSceneService;

    /**
     * 当地场景Service
     */
    @Autowired
    private LocalSceneService localSceneService;

    /**
     * 靶场岗位Service
     */
    @Autowired
    private CrRoleService crRoleService;

    /**
     * 获取复盘用户时间信息
     *
     * @param roomId        房间id
     * @param replaySceneId 复盘场景id
     * @param nodeId        节点id
     * @param groups        组
     * @return {@code List<ScreenRecordUserRole> }
     */
    @Override
	public List<ScreenRecordUserRole> getReplayUserTimeInfo(String roomId, String replaySceneId , String nodeId, Collection<Integer> groups) {
        LocalScene localScene = localSceneService.getByBusinessInstanceId(roomId);
        //如果场景已关闭获取上次的场景Id
        String mpSceneId =localScene == null?replaySceneId : localScene.getMpSceneId();
        if (StringUtils.isBlank(mpSceneId))  {
            return new ArrayList<>();
        }
        JSONObject jsonObject = remoteSceneService.screenrecord(mpSceneId, nodeId);
        List<Object> list = jsonObject.getObject("list", List.class);
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        List<CrRole> roles = crRoleService.getRolesByRoomAndGroups(roomId,groups);
        Map<String,Integer> map = new HashMap<>();
        for (CrRole role : roles) {
            for (String userId : role.getUserIdList()) {
                map.put(userId,role.getRoleGroup());
            }
        }
        List<ScreenRecordRsp> userNodes = BeanUtils.deepCopyList(list, ScreenRecordRsp.class);
        Map<String, List<ScreenRecordRsp>> userMap = userNodes.stream().collect(Collectors.groupingBy(ScreenRecordRsp::getUser_id));
        List<ScreenRecordUserRole> resList= new ArrayList<>();
        for (Map.Entry<String, List<ScreenRecordRsp>> entry : userMap.entrySet()) {
            String userId = entry.getKey();
            if (!map.containsKey(userId)){
                continue;
            }
            List<ScreenRecordRsp> recordRsps = entry.getValue();
            ScreenRecordUserRole recordUserRole = new ScreenRecordUserRole();
            recordUserRole.setName(recordRsps.get(0).getUser_name());
            recordUserRole.setType(map.get(userId));
            recordUserRole.setScreenRecords(recordRsps);
            resList.add(recordUserRole);
        }
        return resList;
    }

    /**
     * 过滤器以前实体结束时间
     *
     * @param screenRecordUserRoles 屏幕录制用户角色
     * @param actualEndTime         实体结束时间
     * @return {@code List<ScreenRecordUserRole> }
     */
    @Override
    public List<ScreenRecordUserRole> filterBeforeActualEndTime(List<ScreenRecordUserRole> screenRecordUserRoles, Date actualEndTime) {
        if(CollectionUtils.isEmpty(screenRecordUserRoles) || actualEndTime == null) {
            return screenRecordUserRoles;
        }
        for(ScreenRecordUserRole screenRecordUserRole : screenRecordUserRoles) {
            List<ScreenRecordRsp> screenRecords = screenRecordUserRole.getScreenRecords();
            if(CollectionUtils.isEmpty(screenRecords)) {
                continue;
            }
            List<ScreenRecordRsp> collect = screenRecords.stream().filter(item -> {
                if (StringUtils.isEmpty(item.getCreate_time())) {
                    return false;
                }
                Date createDate = DateUtil.StringToDate(item.getCreate_time(), "yyyy-MM-dd HH:mm:ss");
                return createDate.before(actualEndTime) || createDate.equals(actualEndTime);
            }).collect(Collectors.toList());
            screenRecordUserRole.setScreenRecords(collect);
        }

        return screenRecordUserRoles;
    }
}
