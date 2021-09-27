package com.cyberpeace.rtks.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.rtks.controller.req.DeleteReq;
import com.cyberpeace.rtks.dao.entity.Role;
import com.cyberpeace.rtks.dao.mapper.RoleMapper;
import com.cyberpeace.rtks.service.KnowledgeService;
import com.cyberpeace.rtks.service.RoleService;
import com.cyberpeace.rtks.service.SkillService;
import com.google.common.collect.Lists;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

	@Autowired
	RoleMapper roleMapper;

	@Autowired
	private KnowledgeService knowledgeService;

	@Autowired
	private SkillService skillService;

	@Override
	public Role getOr404(String roleId) {
		Role role = getById(roleId);
		if (role == null) {
			throw new ResourceNotFoundException("岗位" + roleId + "未找到");
		}
		return role;
	}

	@Override
	public void removeAll(String table) {
		roleMapper.removeAll(table);
	}

	@Override
	public void initDefaultFromPreset(String table) {
		roleMapper.removeAll(table);
		roleMapper.copyFromPreset(table);
	}

	@Override
	@Async
	public void updateBatchByModifyRelatedIds(String id, String type) {
		List<String> ids = Lists.newArrayList();
		ids.add(id);
		List<Role> roleList = Lists.newArrayList();

		roleList.addAll(roleMapper.selectRoleListByIds(ids, type));
		if ("knowledge".equals(type)) {
			roleList.forEach(role -> {
				List<String> knowledgeIds = JsonUtil.toJsonObjectArray(role.getKnowledgeIds(),
						String.class);
				List<String> courseIds = knowledgeService.getCourseByIds(knowledgeIds);
				role.setSkillCourses(JsonUtil.toJsonString(courseIds));
			});
		}
		if ("skill".equals(type)) {
			roleList.forEach(role -> {
				List<String> skillIds = JsonUtil.toJsonObjectArray(role.getSkillIds(),
						String.class);
				List<String> courseIds = skillService.getCourseByIds(skillIds);
				role.setSkillCourses(JsonUtil.toJsonString(courseIds));
			});
		}

	}

	@Override
	@Async
	public void updateBatchByDeleteRelatedIds(DeleteReq deleteReq, String type) {
		List<Role> roleList = Lists.newArrayList();

		roleList.addAll(roleMapper.selectRoleListByIds(deleteReq.getIds(), type));
		if ("knowledge".equals(type)) {
			roleList.forEach(role -> {
				role.setKnowledgeIds(deleteIds(role.getKnowledgeIds(), deleteReq.getIds()));
				List<String> knowledgeIds = JsonUtil.toJsonObjectArray(role.getKnowledgeIds(),
						String.class);
				List<String> courseIds = knowledgeService.getCourseByIds(knowledgeIds);
				role.setSkillCourses(JsonUtil.toJsonString(courseIds));
			});
		}
		if ("skill".equals(type)) {
			roleList.forEach(role -> {
				role.setSkillIds(deleteIds(role.getSkillIds(), deleteReq.getIds()));
				List<String> skillIds = JsonUtil.toJsonObjectArray(role.getSkillIds(),
						String.class);
				List<String> courseIds = skillService.getCourseByIds(skillIds);
				role.setSkillCourses(JsonUtil.toJsonString(courseIds));
			});
		}
		if ("ability".equals(type)) {
			roleList.forEach(role -> {
				role.setSkillIds(deleteIds(role.getSkillIds(), deleteReq.getIds()));
			});
		}
		if ("task".equals(type)) {
			roleList.forEach(role -> {
				role.setSkillIds(deleteIds(role.getTaskIds(), deleteReq.getIds()));
			});
		}

		this.updateBatchById(roleList);

	}

	private String deleteIds(String Ids, List<String> ids) {

		List<String> originIds = JsonUtil.toJsonObjectArray(Ids, String.class);
		originIds.removeAll(ids);
		return JsonUtil.toJsonString(originIds);

	}

	@Override
	public List<Role> getRolesByLevelId(String levelId) {
		return roleMapper.getRolesUnderLevel(levelId);
	}

	@Override
	public List<String> getRoleIdsByLevelIds(List<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return new ArrayList<>();
		}
		return roleMapper.getRoleIdsByLevelIds(ids);
	}

	@Override
	public IPage<Role> listRoles(Page<Role> page, String keyword, String frameId) {
		return roleMapper.listRole(page, keyword, frameId);
	}

}