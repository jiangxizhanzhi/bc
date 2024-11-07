package com.cyberpeace.cr.redvsblue.service.impl;

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
import com.cyberpeace.cr.redvsblue.dao.entity.RbCommonDevice;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbCommonDeviceMapper;
import com.cyberpeace.cr.redvsblue.service.RbCommonDeviceService;
import com.cyberpeace.cr.redvsblue.service.context.RbCommonDeviceContext;

/**
 * RB常见装置Service实现
 *
 * @author SN
 * @date 2024/11/07
 */
@Service
public class RbCommonDeviceServiceImpl extends ServiceImpl<RbCommonDeviceMapper, RbCommonDevice> implements RbCommonDeviceService {

	/**
	 * RB常见装置Mapper
	 */
	@Autowired
	private RbCommonDeviceMapper rbCommonDeviceMapper;

	/**
	 * 靶场角色Service
	 */
	@Autowired
	private CrRoleService crRoleService;

	/**
	 * 过滤可见常用设备
	 *
	 * @param roomId                  房间id
	 * @param commonDeviceContextList 常见设备上下文列表
	 * @return {@code List<RbCommonDeviceContext> }
	 */
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

	/**
	 * 获取收集常见设备
	 *
	 * @param roomId        房间id
	 * @param collectUserId 收集用户id
	 * @return {@code List<RbCommonDevice> }
	 */
	@Override
	public List<RbCommonDevice> getCollectedCommonDevices(String roomId, String collectUserId) {
		return rbCommonDeviceMapper.selectByCollectUserAndRoomId(roomId, collectUserId);
	}

	/**
	 * 获取都收集常见装置设备
	 *
	 * @param roomId 房间id
	 * @return {@code List<RbCommonDevice> }
	 */
	@Override
	public List<RbCommonDevice> getAllCollectedCommonDeviceDevices(String roomId) {
		return rbCommonDeviceMapper.selectAllDevices(roomId);
	}

	/**
	 * 新增列表自收集
	 *
	 * @param commonDeviceContextList 常见设备上下文列表
	 */
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

	/**
	 * 新增自收集
	 *
	 * @param commonDeviceContext 通用设备上下文
	 */
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

	/**
	 * del collect
	 *
	 * @param resourceId 资源id
	 */
	@Override
	public void delCollect(String resourceId) {
		rbCommonDeviceMapper.deleteByResourceId(resourceId);
	}

	/**
	 * 删除由装置id
	 *
	 * @param deviceId 装置id
	 */
	@Override
	public void deleteByDeviceId(String deviceId) {
		rbCommonDeviceMapper.deleteByDeviceId(deviceId);
	}

	/**
	 * 戴尔由房间id
	 *
	 * @param roomId 房间id
	 */
	@Override
	public void delByRoomId(String roomId) {
		rbCommonDeviceMapper.deleteByRoomId(roomId);
	}
}
