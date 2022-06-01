package com.cyberpeace.rtks.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.ListUtil;
import com.cyberpeace.rtks.constant.RtksBusinessErrorCode;
import com.cyberpeace.rtks.dao.entity.Role;
import com.cyberpeace.rtks.dao.entity.RoleSkillRelation;
import com.cyberpeace.rtks.dao.entity.Skill;
import com.cyberpeace.rtks.dao.mapper.RoleMapper;
import com.cyberpeace.rtks.dao.mapper.RoleSkillRelationMapper;
import com.cyberpeace.rtks.dao.mapper.SkillMapper;
import com.cyberpeace.rtks.service.KnowledgeService;
import com.cyberpeace.rtks.service.RoleService;
import com.cyberpeace.rtks.service.bo.Difference;
import com.cyberpeace.rtks.service.bo.skill.IdWeight;
import com.cyberpeace.rtks.service.bo.skill.RoleSkillRes;
import com.cyberpeace.rtks.service.bo.skill.SkillWeightRes;
import com.cyberpeace.rtks.util.RtksCommonUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

	@Autowired
	RoleMapper roleMapper;

	@Autowired
	private KnowledgeService knowledgeService;

	@Autowired
	private RoleSkillRelationMapper roleSkillRelationMapper;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private SkillMapper skillMapper;

	@Override
	public Role getOr404(String roleId) {
		Role role = getById(roleId);
		if (role == null) {
			throw new BusinessException(RtksBusinessErrorCode.ROLE_NOT_EXIST);
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
				role.setKnowledgeCourses(JsonUtil.toJsonString(courseIds));
			});
		}
		this.updateBatchById(roleList);

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
				role.setKnowledgeCourses(JsonUtil.toJsonString(courseIds));
			});
		}
		if ("ability".equals(type)) {
			roleList.forEach(role -> {
				role.setAbilityIds(deleteIds(role.getAbilityIds(), deleteReq.getIds()));
			});
		}
		if ("task".equals(type)) {
			roleList.forEach(role -> {
				role.setTaskIds(deleteIds(role.getTaskIds(), deleteReq.getIds()));
			});
		}

		this.updateBatchById(roleList);

	}

	@Override
	public void deleteSkillRelBySkillIds(List<String> skillIds) {
		if (skillIds.isEmpty()) {
			return;
		}
		roleSkillRelationMapper.deleteBySkillIds(skillIds);
	}

	@Override
	public void addSkillRelation(String resourceId, List<IdWeight> skillIdWeights) {
		if (CollectionUtils.isEmpty(skillIdWeights)) {
			return;
		}
		// validate skill list, if skill does exist
		List<String> skillIds = skillIdWeights.stream().map(IdWeight::getResourceId)
				.collect(Collectors.toList());
		List<Skill> skills = skillMapper.selectBatchIds(skillIds);
		Set<String> validSkillIds = skills.stream().map(Skill::getResourceId)
				.collect(Collectors.toSet());

		List<RoleSkillRelation> relations = Lists.newArrayList();
		for (IdWeight skill : skillIdWeights) {
			if (!validSkillIds.contains(skill.getResourceId())) {
				continue;
			}
			RoleSkillRelation relation = new RoleSkillRelation();
			relation.setRoleId(resourceId);
			relation.setSkillId(skill.getResourceId());
			relation.setWeight(skill.getWeight());
			relations.add(relation);
		}
		// validation before insertion
		if (relations.isEmpty()) {
			return;
		}

		roleSkillRelationMapper.batchInsert(relations);
	}

	@Override
	public void updateSkillRel(String resourceId, List<IdWeight> skillIdWeights) {
		List<RoleSkillRelation> relations = roleSkillRelationMapper.batchGetByRoleId(resourceId);
		Difference<RoleSkillRelation> difference = RtksCommonUtil.differ(relations, "skill_id",
				skillIdWeights);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				// add relations
				addSkillRelation(resourceId, difference.getAdded());
				// delete
				roleSkillRelationMapper.deleteByRoleIdAndSkillIds(resourceId,
						difference.getDeleted());
				// update
				for (RoleSkillRelation relation : difference.getModified()) {
					roleSkillRelationMapper.updateById(relation);
				}
			}
		});
	}

	@Override
	public void updateSkillRelSimple(String roleId, List<IdWeight> skillIdWeights) {
		// compare
		List<IdWeight> idWeightList = roleSkillRelationMapper.listByRoleId(roleId);
		if (ListUtil.compare2List(idWeightList, skillIdWeights)) {
			return;
		}
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				// delete all
				roleSkillRelationMapper.deleteByRoleId(roleId);
				// create all
				addSkillRelation(roleId, skillIdWeights);
			}
		});
	}

	@Override
	public List<SkillWeightRes> listSkillByRoleId(String roleId) {
		return roleSkillRelationMapper.listSkillByRoleId(roleId);
	}

	@Override
	public void deleteSkillRelByRoleIds(List<String> roleIds) {
		if (roleIds.isEmpty()) {
			return;
		}
		roleSkillRelationMapper.deleteByRoleIds(roleIds);
	}

	@Override
	public Map<String, RoleSkillRes> mapRoleSkillCount(List<String> roleIds) {
		Map<String, RoleSkillRes> mapping = Maps.newHashMap();
		if (roleIds.isEmpty()) {
			return mapping;
		}
		List<RoleSkillRes> resList = roleSkillRelationMapper.countByRoleIds(roleIds);
		resList.forEach((res) -> {
			mapping.put(res.getRoleId(), res);
		});
		return mapping;
	}

	@Override
	public List<String> getRelMissionIds(String roleId) {
		return roleSkillRelationMapper.getMissionIdsByRoleId(roleId);
	}

	@Override
	public void clearRole() {
		roleMapper.clearRole();
		roleSkillRelationMapper.clearRoleSkillRel();
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