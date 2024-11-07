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

/**
 * 团队装置Service实现
 *
 * @author SN
 * @date 2024/11/07
 */
@Service
public class TeamDeviceServiceImpl extends ServiceImpl<TeamDeviceMapper, TeamTrainDevice> implements TeamDeviceService {
    /**
     * 团队装置Mapper
     */
    @Autowired
    private TeamDeviceMapper teamDeviceMapper;

    /**
     * 远程用户Service
     */
    @Autowired
    private RemoteUserService remoteUserService;

    /**
     * 获取占领
     *
     * @param roomId   房间id
     * @param deviceId 装置id
     * @return {@code TeamTrainDevice }
     */
    @Override
    public TeamTrainDevice getOccupied(String roomId, String deviceId) {
        return teamDeviceMapper.selectByRoomIdAndDeviceId(roomId, deviceId);
    }

    /**
     * 占据
     *
     * @param device 装置
     * @return boolean
     */
    @Override
    public boolean occupy(TeamTrainDevice device) {
        return teamDeviceMapper.insert(device) > 0;
    }

    /**
     * 删除
     *
     * @param roomId   房间id
     * @param deviceId 装置id
     * @param userId   用户id
     * @return boolean
     */
    @Override
    public boolean remove(String roomId, String deviceId, String userId) {
        teamDeviceMapper.deleteByRoomIdAndDeviceIdAndCreateUserId(roomId, deviceId, userId);
        return true;
    }

    /**
     * 获取占有
     *
     * @param roomId   房间id
     * @param deviceId 装置id
     * @return {@code List<TeamTrainDeviceContext> }
     */
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
