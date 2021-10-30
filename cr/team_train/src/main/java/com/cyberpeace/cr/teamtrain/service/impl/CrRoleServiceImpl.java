package com.cyberpeace.cr.teamtrain.service.impl;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.teamtrain.dao.entity.CrRole;
import com.cyberpeace.cr.teamtrain.dao.mapper.CrRoleMapper;
import com.cyberpeace.cr.teamtrain.dao.mapper.RoleReq;
import com.cyberpeace.cr.teamtrain.model.role.CrRoleRes;
import com.cyberpeace.cr.teamtrain.model.role.NameElement;
import com.cyberpeace.cr.teamtrain.model.role.RoleResData;
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
	public CrRoleRes getRoles(String roomId) {
		CrRoleRes crRoleRes = new CrRoleRes();
		RoleReq roleReq = new RoleReq();
		roleReq.setRoomId(roomId);
		roleReq.setRoleGroup(CrRole.RoleGroupCodeInteger.STUDENT.getValue());

		List<CrRole> crRoles = crRoleMapper.selectCrRoleByRoomId(roleReq);
		if (crRoles == null) {
			throw new ResourceNotFoundException("团队训练" + roomId + "不存在训练角色");
		}

		List<RoleResData> roleResDataList = Lists.newArrayList();
		for (CrRole crRole : crRoles) {
			String missionSchemeId = crRole.getMissionSchemeId();
			// todo 调用获取任务
			RoleResData roleResData = new RoleResData();
			roleResData.setName(crRole.getName());
			roleResData.setResourceId(crRole.getResourceId());
			roleResData.setMissionsList(missionSchemeId);
			roleResData.setUsers(getUserByRoleUsers(crRole.getUserIds()));
			roleResDataList.add(roleResData);
		}
		crRoleRes.setRoles(roleResDataList);
		crRoleRes.setShowWriteupMapping("tode");
		crRoleRes.setMissionMapping("todo");

		return crRoleRes;
	}

	private List<NameElement> getUserByRoleUsers(String userIds) {

		List<NameElement> elements = Lists.newArrayList();
		List<String> userList = JsonUtil.toJsonObjectArray(userIds, String.class);

		List<BasicNameValuePair> nvps = Lists.newArrayList();
		// nvps.add(new BasicNameValuePair("ids",
		// userIds.replaceAll("\"","").substring(1, userIds.length() - 1)));
		nvps.add(new BasicNameValuePair("perPage", String.valueOf(1000)));
		nvps.add(new BasicNameValuePair("page", String.valueOf(1)));

		List<UserInfo> userInfoList = remoteUserService.getLazyUserList(nvps);
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
