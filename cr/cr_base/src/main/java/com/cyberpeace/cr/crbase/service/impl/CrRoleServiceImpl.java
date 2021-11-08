package com.cyberpeace.cr.crbase.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.mapper.CrRoleMapper;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleContext;
import com.cyberpeace.cr.crbase.service.context.role.NameElement;
import com.cyberpeace.cr.crbase.service.context.role.RoleData;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.integratation.model.UserPageContext;
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
	public CrRoleContext getRoles(String roomId) {
		CrRoleContext crRoleRes = new CrRoleContext();
		RoleQuery roleQuery = new RoleQuery();
		roleQuery.setRoomId(roomId);
		roleQuery.setRoleGroup(CrRole.RoleGroupCodeInteger.STUDENT.getValue());

		List<CrRole> crRoles = crRoleMapper.selectCrRoleByRoomId(roleQuery);
		if (crRoles == null) {
			throw new ResourceNotFoundException(roomId);
		}

		List<RoleData> roleResDataList = Lists.newArrayList();
		for (CrRole crRole : crRoles) {
			String missionSchemeId = crRole.getMissionSchemeId();
			// todo 调用获取任务
			RoleData roleResData = new RoleData();
			roleResData.setName(crRole.getName());
			roleResData.setResourceId(crRole.getResourceId());
			roleResData.setMissionsList(missionSchemeId);
			roleResData.setUsers(getUserByRoleUsers(crRole.getUserIds()));
			roleResDataList.add(roleResData);
		}
		crRoleRes.setRoles(roleResDataList);

		// "todo 任务题解"
		// crRoleRes.setShowWriteupMapping("todo");
		// crRoleRes.setMissionMapping("todo");

		return crRoleRes;
	}

	@Override
	public List<String> getRoomIdsByUserId(String userId) {
		return crRoleMapper.getRoomIdsByUserId(userId);

	}

	@Override
	public List<CrRole> selectCrRoleByRoomId(RoleQuery roleQuery) {
		return crRoleMapper.selectCrRoleByRoomId(roleQuery);
	}


	private List<NameElement> getUserByRoleUsers(String userIds) {

		List<NameElement> userElements = Lists.newArrayList();
		List<String> userList = JsonUtil.toJsonObjectArray(userIds, String.class);

		UserPageContext userPageContext = new UserPageContext();
		userPageContext.setIds(String.join(",", userList));
		List<UserInfo> userInfoList = remoteUserService.getLazyUserList(userPageContext);
		userInfoList.forEach(userInfo -> {
			if (userList.contains(userInfo.getUserId())) {
				NameElement nameElement = new NameElement();
				nameElement.setName(userInfo.getUserName());
				userElements.add(nameElement);

			}
		});

		return userElements;
	}
}
