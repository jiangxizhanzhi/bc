package com.cyberpeace.cr.crbase.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.ScreenService;
import com.cyberpeace.cr.crbase.service.context.screen.ScreenRecordRsp;
import com.cyberpeace.cr.crbase.service.context.screen.ScreenRecordUserRole;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.service.RemoteSceneService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ScreenServiceImpl implements ScreenService {
    @Autowired
    private RemoteSceneService remoteSceneService;

    @Autowired
    private LocalSceneService localSceneService;

    @Autowired
    private CrRoleService crRoleService;

    @Override
    public List<ScreenRecordUserRole> getReplyUserTimeInfo(String roomId, String nodeId) {
        LocalScene localScene = localSceneService.getByBusinessInstanceId(roomId);
        JSONObject jsonObject = remoteSceneService.screenrecord(localScene.getMpSceneId(), nodeId);
        List<Object> list = jsonObject.getObject("list", List.class);
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        List<CrRole> roles = crRoleService.getRolesByRoomId(roomId);
        Map<String,Integer> map = new HashMap<>();
        for (CrRole role : roles) {
            for (String userId : role.getUserIdList()) {
                map.put(userId,role.getBusinessType());
            }
        }
        List<ScreenRecordRsp> userNodes = BeanUtils.deepCopyList(list, ScreenRecordRsp.class);
        Map<String, List<ScreenRecordRsp>> userMap = userNodes.stream().collect(Collectors.groupingBy(ScreenRecordRsp::getUser_id));
        List<ScreenRecordUserRole> resList= new ArrayList<>();
        for (Map.Entry<String, List<ScreenRecordRsp>> entry : userMap.entrySet()) {
            String userId = entry.getKey();
            List<ScreenRecordRsp> recordRsps = entry.getValue();
            ScreenRecordUserRole recordUserRole = new ScreenRecordUserRole();
            recordUserRole.setName(recordRsps.get(0).getUser_name());
            recordUserRole.setType(map.get(userId));
            recordUserRole.setScreenRecords(recordRsps);

        }
        return resList;
    }
}
