package com.cyberpeace.cr.redvsblue.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.context.role.CrRoleNode;
import com.cyberpeace.common.context.role.CrRoleNodesData;
import com.cyberpeace.common.entity.CrRole;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbCommonDeviceMapper;
import com.cyberpeace.cr.redvsblue.entity.RbCommonDevice;
import com.cyberpeace.cr.redvsblue.service.RbCommonDeviceService;
import com.cyberpeace.cr.redvsblue.service.context.RbCommonDeviceContext;

@Service
public class RbCommonDeviceServiceImpl extends ServiceImpl<RbCommonDeviceMapper, RbCommonDevice> implements RbCommonDeviceService {

	@Autowired
	private RbCommonDeviceMapper rbCommonDeviceMapper;

	@Autowired
	private CrRoleService crRoleService;

	@Override
	public List<RbCommonDeviceContext> filterVisibleCommonDevices(String roomId,
			List<RbCommonDeviceContext> commonDeviceContextList) {
		List<RbCommonDeviceContext> commonDeviceList = new ArrayList<>();

		String userId = UserInfoContext.getUserContext().getUserId();
		CrRole role = crRoleService.getCrRoleByRoomIdAndUserId(roomId, userId);
		CrRoleNodesData nodesData = role.getNodeData();
		if (nodesData == null) {
			return commonDeviceList;
		}

		List<CrRoleNode> visibleNodes = nodesData.getVisibleNodes();
		if (CollectionUtils.isEmpty(visibleNodes)
				|| CollectionUtils.isEmpty(commonDeviceContextList)) {
			return commonDeviceList;
		}

		commonDeviceList = commonDeviceContextList.stream().filter(item -> {
			for (CrRoleNode visibleNode : visibleNodes) {
				if (visibleNode == null || StringUtils.isEmpty(visibleNode.getId())
						|| !visibleNode.getId().equals(item.getDeviceId())) {
					continue;
				}
				return true;
			}
			return false;
		}).collect(Collectors.toList());

		return commonDeviceList;
	}

	@Override
	public List<RbCommonDevice> getCollectedCommonDevices(String roomId, String collectUserId) {
		return rbCommonDeviceMapper.selectByCollectUserAndRoomId(roomId, collectUserId);
	}

	@Override
	public List<RbCommonDevice> getAllCollectedCommonDeviceDevices(String roomId) {
		return rbCommonDeviceMapper.selectAllDevices(roomId);
	}

	@Override
	public void addListToCollect(List<RbCommonDeviceContext> commonDeviceContextList) {
		List<RbCommonDevice> commonDeviceList = commonDeviceContextList.stream().map(item -> {
			RbCommonDevice commonDevice = new RbCommonDevice();
			BeanUtils.copyProperties(item, commonDevice);

			commonDevice.setResourceId(IDGeneratorUtil.getID());
			String userId = UserInfoContext.getUserContext().getUserId();
			commonDevice.setCollectUserId(userId);
			commonDevice.setCreateUserId(userId);
			commonDevice.setCreateTime(new Date());
			return commonDevice;
		}).collect(Collectors.toList());
		rbCommonDeviceMapper.insertList(commonDeviceList);
	}

	@Override
	public void addToCollect(RbCommonDeviceContext commonDeviceContext) {
		RbCommonDevice commonDevice = new RbCommonDevice();
		BeanUtils.copyProperties(commonDeviceContext, commonDevice);

		String userId = UserInfoContext.getUserContext().getUserId();
		commonDevice.setCollectUserId(userId);
		commonDevice.setCreateUserId(userId);
		commonDevice.setCreateTime(new Date());
		rbCommonDeviceMapper.insert(commonDevice);
	}

	@Override
	public void delCollect(String resourceId) {
		rbCommonDeviceMapper.deleteByResourceId(resourceId);
	}

	@Override
	public void deleteByDeviceId(String deviceId) {
		rbCommonDeviceMapper.deleteByDeviceId(deviceId);
	}

	@Override
	public void delByRoomId(String roomId) {
		rbCommonDeviceMapper.deleteByRoomId(roomId);
	}
}
