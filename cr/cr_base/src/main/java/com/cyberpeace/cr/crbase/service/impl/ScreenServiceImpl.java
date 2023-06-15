package com.cyberpeace.cr.crbase.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.ScreenService;
import com.cyberpeace.cr.crbase.service.context.screen.ScreenRecordRsp;
import com.cyberpeace.cr.crbase.service.context.screen.ScreenRecordUserRole;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.service.RemoteSceneService;

@Slf4j
@Service
public class ScreenServiceImpl implements ScreenService {
    @Autowired
    private RemoteSceneService remoteSceneService;

    @Autowired
    private LocalSceneService localSceneService;

    @Autowired
    private CrRoleService crRoleService;

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
}
