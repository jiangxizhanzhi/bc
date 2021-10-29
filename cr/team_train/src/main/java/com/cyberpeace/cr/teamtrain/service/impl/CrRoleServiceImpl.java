package com.cyberpeace.cr.teamtrain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.ListUtil;
import com.cyberpeace.cr.teamtrain.controller.CrRoleOutput;
import com.cyberpeace.cr.teamtrain.controller.NameElement;
import com.cyberpeace.cr.teamtrain.controller.RoleOutputData;
import com.cyberpeace.cr.teamtrain.dao.entity.CrRole;
import com.cyberpeace.cr.teamtrain.dao.mapper.CrRoleMapper;
import com.cyberpeace.cr.teamtrain.dao.mapper.RoleReq;
import com.cyberpeace.cr.teamtrain.service.CrRoleService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;

/**
 *
 */
@Service
public class CrRoleServiceImpl extends ServiceImpl<CrRoleMapper, CrRole> implements CrRoleService {

	@Autowired
	private CrRoleMapper crRoleMapper;

	@Autowired
	private RemoteUserService remoteUserService;

	@Override
	public CrRoleOutput getOr404(String roomId) {
		CrRoleOutput crRoleOutput = new CrRoleOutput();
		RoleReq roleReq = new RoleReq();
		roleReq.setRoomId(roomId);
		roleReq.setRoleGroup(CrRole.RoleGroupCodeInteger.STUDENT.getValue());

		List<CrRole> crRoles = crRoleMapper.selectCrRoleByRoomId(roleReq);
		if (crRoles == null) {
			throw new ResourceNotFoundException("团队训练" + roomId + "不存在训练角色");
		}

		List<RoleOutputData> roleOutputDataList = Lists.newArrayList();
		for (CrRole crRole : crRoles) {
			String missionSchemeId = crRole.getMissionSchemeId();
			// 调用获取任务
			RoleOutputData roleOutputData = new RoleOutputData();
			roleOutputData.setName(crRole.getName());
			roleOutputData.setResourceId(crRole.getResourceId());
			roleOutputData.setMissionsList(missionSchemeId);
			roleOutputData.setUsers(getUserByRoleUsers(crRole.getUserIds()));
			roleOutputDataList.add(roleOutputData);
		}
		crRoleOutput.setRoles(roleOutputDataList);
		crRoleOutput.setShowWriteupMapping("tode");
		crRoleOutput.setMissionMapping("todo");

		return crRoleOutput;
	}

	private List<NameElement> getUserByRoleUsers(String userIds) {


		List<NameElement> elements = Lists.newArrayList();
		List<String> userList = ListUtil.string2List(userIds);
		List<UserInfo> userInfoList = remoteUserService.getList();
		userInfoList.forEach(userInfo -> {

			if (userList.contains(userInfo.getUserId())) {
				NameElement element = new NameElement();
				element.setName(userInfo.getUserName());
				elements.add(element);
			}
		});

		return elements;
	}
}
