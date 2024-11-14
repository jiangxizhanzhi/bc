package com.cyberpeace.cr.teamtrain.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.entity.CrRole;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.common.context.role.CrRoleNode;
import com.cyberpeace.common.context.role.CrRoleNodesData;
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

/**
 * 团队常用装置Service实现
 *
 * @author SN
 * @date 2024/11/14
 */
@Service
public class TeamCommonDeviceServiceImpl extends ServiceImpl<TeamCommonDeviceMapper, TeamCommonDevice> implements TeamCommonDeviceService {

	/**
	 * 团队常用装置Mapper
	 */
	@Autowired
	private TeamCommonDeviceMapper teamCommonDeviceMapper;

	/**
	 * 靶场岗位Service
	 */
	@Autowired
	private CrRoleService crRoleService;

	/**
	 * 过滤器可见常用设备
	 *
	 * @param roomId           房间id
	 * @param allCommonDevices 全部常用设备
	 * @return {@code List<TeamCommonDeviceContext> }
	 */
	@Override
	public List<TeamCommonDeviceContext> filterVisibleCommonDevices(String roomId,
			List<TeamCommonDeviceContext> allCommonDevices) {
		List<TeamCommonDeviceContext> commonDeviceList = new ArrayList<>();

		String userId = UserInfoContext.getUserContext().getUserId();
		CrRole role = crRoleService.getCrRoleByRoomIdAndUserId(roomId, userId);
		CrRoleNodesData nodesData = role.getNodeData();
		if (nodesData == null) {
			return commonDeviceList;
		}

		List<CrRoleNode> visibleNodes = nodesData.getVisibleNodes();
		if (CollectionUtils.isEmpty(visibleNodes) || CollectionUtils.isEmpty(allCommonDevices)) {
			return commonDeviceList;
		}

		commonDeviceList = allCommonDevices.stream().filter(item -> {
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
	 * 获取收集常用设备
	 *
	 * @param roomId        房间id
	 * @param collectUserId 收集用户id
	 * @return {@code List<TeamCommonDevice> }
	 */
	@Override
	public List<TeamCommonDevice> getCollectedCommonDevices(String roomId, String collectUserId) {
		return teamCommonDeviceMapper.selectByCollectUserAndRoomId(roomId, collectUserId);
	}

	/**
	 * 获取全部收集常用装置设备
	 *
	 * @param roomId 房间id
	 * @return {@code List<TeamCommonDevice> }
	 */
	@Override
	public List<TeamCommonDevice> getAllCollectedCommonDeviceDevices(String roomId) {
		return teamCommonDeviceMapper.selectAllDevices(roomId);
	}

	/**
	 * 新增列表自收集
	 *
	 * @param commonDeviceContextList 常用装置上下文列表
	 */
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

	/**
	 * 新增自收集
	 *
	 * @param commonDeviceContext 常用装置上下文
	 */
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

	/**
	 * del collect
	 *
	 * @param resourceId 资源id
	 */
	@Override
	public void delCollect(String resourceId) {
		teamCommonDeviceMapper.deleteByResourceId(resourceId);
	}

	/**
	 * 删除由装置id
	 *
	 * @param nodeId 节点id
	 */
	@Override
	public void deleteByDeviceId(String nodeId) {
		teamCommonDeviceMapper.deleteByDeviceId(nodeId);
	}

	/**
	 * 戴尔由房间id
	 *
	 * @param roomId 房间id
	 */
	@Override
	public void delByRoomId(String roomId) {
		teamCommonDeviceMapper.deleteByRoomId(roomId);
	}
}
