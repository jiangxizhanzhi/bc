package com.cyberpeace.cr.teamtrain.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNode;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNodesData;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamCommonDevice;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamCommonDeviceMapper;
import com.cyberpeace.cr.teamtrain.service.TeamCommonDeviceService;
import com.cyberpeace.cr.teamtrain.service.context.TeamCommonDeviceContext;

@Service
public class TeamCommonDeviceServiceImpl extends ServiceImpl<TeamCommonDeviceMapper, TeamCommonDevice> implements TeamCommonDeviceService {

    @Autowired
    private TeamCommonDeviceMapper teamCommonDeviceMapper;

    @Autowired
    private CrRoleService crRoleService;

    @Override
    public List<TeamCommonDeviceContext> filterVisibleCommonDevices(String roomId, List<TeamCommonDeviceContext> allCommonDevices) {
        List<TeamCommonDeviceContext> commonDeviceList = new ArrayList<>();

        String userId = UserInfoContext.getUserContext().getUserId();
        CrRole role = crRoleService.getCrRoleByRoomIdAndUserId(roomId, userId);
        CrRoleNodesData nodesData = role.getNodeData();
        if(nodesData == null) {
            return commonDeviceList;
        }

        List<CrRoleNode> visibleNodes = nodesData.getVisibleNodes();
        if(CollectionUtils.isEmpty(visibleNodes) || CollectionUtils.isEmpty(allCommonDevices)) {
            return commonDeviceList;
        }

        commonDeviceList = allCommonDevices.stream().filter(item -> {
            for(CrRoleNode visibleNode : visibleNodes) {
                if(visibleNode == null ||
                        StringUtils.isEmpty(visibleNode.getId()) ||
                        !visibleNode.getId().equals(item.getDeviceId())
                ) {
                    continue;
                }
                return true;
            }
            return false;
        }).collect(Collectors.toList());

        return commonDeviceList;
    }

    @Override
    public List<TeamCommonDevice> getCollectedCommonDevices(String roomId, String collectUserId) {
        return teamCommonDeviceMapper.selectByCollectUserAndRoomId(roomId, collectUserId);
    }

    @Override
    public List<TeamCommonDevice> getAllCollectedCommonDeviceDevices(String roomId) {
        return teamCommonDeviceMapper.selectAllDevices(roomId);
    }

    @Override
    public void addListToCollect(List<TeamCommonDeviceContext> commonDeviceContextList) {
        List<TeamCommonDevice> commonDeviceList = commonDeviceContextList.stream().map(item -> {
            TeamCommonDevice commonDevice = new TeamCommonDevice();
            BeanUtils.copyProperties(item, commonDevice);

            commonDevice.setResourceId(IDGeneratorUtil.getID());
            String userId = UserInfoContext.getUserContext().getUserId();
            commonDevice.setCollectUserId(userId);
            commonDevice.setCreateUserId(userId);
            commonDevice.setCreateTime(new Date());
            return commonDevice;
        }).collect(Collectors.toList());
        teamCommonDeviceMapper.insertList(commonDeviceList);
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

	@Override
	public void deleteByDeviceId(String nodeId) {
		teamCommonDeviceMapper.deleteByDeviceId(nodeId);
	}

    @Override
    public void delByRoomId(String roomId) {
        teamCommonDeviceMapper.deleteByRoomId(roomId);
    }
}
