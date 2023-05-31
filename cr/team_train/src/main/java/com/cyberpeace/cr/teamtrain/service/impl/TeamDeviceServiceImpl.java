package com.cyberpeace.cr.teamtrain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTrainDevice;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamDeviceMapper;
import com.cyberpeace.cr.teamtrain.service.TeamDeviceService;
import com.cyberpeace.cr.teamtrain.service.context.TeamTrainDeviceContext;
import com.cyberpeace.integratation.service.RemoteUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TeamDeviceServiceImpl extends ServiceImpl<TeamDeviceMapper, TeamTrainDevice> implements TeamDeviceService {
    @Autowired
    private TeamDeviceMapper teamDeviceMapper;

    @Autowired
    private RemoteUserService remoteUserService;

    @Override
    public TeamTrainDevice getOccupied(String roomId, String deviceId) {
        return teamDeviceMapper.selectByRoomIdAndDeviceId(roomId, deviceId);
    }

    @Override
    public boolean occupy(TeamTrainDevice device) {
        return teamDeviceMapper.insert(device) > 0;
    }

    @Override
    public boolean remove(String roomId, String deviceId, String userId) {
        teamDeviceMapper.deleteByRoomIdAndDeviceIdAndCreateUserId(roomId, deviceId, userId);
        return true;
    }

    @Override
    public List<TeamTrainDeviceContext> getOccupies(String roomId, String deviceId) {
        List<TeamTrainDevice> devices = teamDeviceMapper.selectAllByRoomIdAndDeviceId(roomId, deviceId);
        // convert userId to username
        List<String> userIds = devices.stream().map(TeamTrainDevice::getCreateUserId)
                .collect(Collectors.toList());
        Map<String, String> idNameMap = remoteUserService.getIdNameMap(userIds);
        List<TeamTrainDeviceContext> contexts = BeanUtils.deepCopyList(devices, TeamTrainDeviceContext.class);
        for (TeamTrainDeviceContext context : contexts) {
            context.setCreateUser(idNameMap.get(context.getCreateUserId()));
        }
        return contexts;
    }


}
