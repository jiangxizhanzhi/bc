package com.cyberpeace.cr.teamtrain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamCommonDevice;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamCommonDeviceMapper;
import com.cyberpeace.cr.teamtrain.service.TeamCommonDeviceService;
import com.cyberpeace.cr.teamtrain.service.context.TeamCommonDeviceContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TeamCommonDeviceServiceImpl extends ServiceImpl<TeamCommonDeviceMapper, TeamCommonDevice> implements TeamCommonDeviceService {

    @Autowired
    private TeamCommonDeviceMapper teamCommonDeviceMapper;

    @Override
    public List<TeamCommonDevice> getCollectedCommonDevices(String roomId, String collectUserId) {
        return teamCommonDeviceMapper.selectByCollectUserAndRoomId(roomId, collectUserId);
    }

    @Override
    public void addToCollect(TeamCommonDeviceContext commonDeviceContext) {
        TeamCommonDevice commonDevice = new TeamCommonDevice();
        BeanUtils.copyProperties(commonDeviceContext, commonDevice);

        String userId = UserInfoContext.getUserContext().getUserId();
        commonDevice.setCollectUserId(userId);
        commonDevice.setCreateUserId(userId);
        commonDevice.setCreateTime(new Date());
        teamCommonDeviceMapper.insert(commonDevice);
    }

    @Override
    public void delCollect(String resourceId) {
        teamCommonDeviceMapper.deleteByResourceId(resourceId);
    }
}
