package com.cyberpeace.rtks.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.rtks.dao.entity.CourseName;
import com.cyberpeace.rtks.service.CourseNameService;
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
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.ListUtil;
import com.cyberpeace.cr.crbase.service.MissionTemplateService;
import com.cyberpeace.cr.crbase.service.context.mission.MissionTemplateRes;
import com.cyberpeace.rtks.constant.RtksBusinessErrorCode;
import com.cyberpeace.rtks.controller.course.CourseConfigOutput;
import com.cyberpeace.rtks.dao.entity.Role;
import com.cyberpeace.rtks.dao.entity.RoleSkillRelation;
import com.cyberpeace.rtks.dao.mapper.RoleMapper;
import com.cyberpeace.rtks.dao.mapper.RoleSkillRelationMapper;
import com.cyberpeace.rtks.dao.mapper.SkillMapper;
import com.cyberpeace.rtks.service.KnowledgeService;
import com.cyberpeace.rtks.service.RoleService;
import com.cyberpeace.rtks.service.bo.Difference;
import com.cyberpeace.rtks.service.bo.skill.IdWeight;
import com.cyberpeace.rtks.service.bo.skill.MissionTemplateSimple;
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

	@Autowired
	private MissionTemplateService missionTemplateService;

	@Autowired
	private CourseNameService courseNameService;

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
	public void updateBatchByModifyRelatedIds(String id, String type) {
		List<String> ids = Lists.newArrayList();
		ids.add(id);
		List<Role> roleList = Lists.newArrayList();

		roleList.addAll(roleMapper.selectRoleListByIds(ids, type));
		if ("knowledge".equals(type)) {
			for (Role role : roleList) {
				List<String> knowledgeIds = JsonUtil.toJsonObjectArray(role.getKnowledgeIds(),
						String.class);
				List<String> courseIds = knowledgeService.getCourseByIds(knowledgeIds);
				List<String> newOrder = ListUtil.mergeWithOldOrder(
						JsonUtil.toJsonObjectArray(role.getKnowledgeCourses(), String.class),
						courseIds);
				role.setKnowledgeCourses(JsonUtil.toJsonString(newOrder));
			}
			this.updateBatchById(roleList);
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
		Set<String> validSkillIds = skillMapper.validateIds(skillIds);

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

	@Override
	public Boolean ifNameDuplicate(String name, String resourceId) {
		return roleMapper.ifNameDuplicate(name, resourceId);
	}

	@Override
	public List<CourseConfigOutput> getCourseList(String roleId) {
		List<CourseConfigOutput> res = Lists.newArrayList();
		Role role = this.getOr404(roleId);

		// get knowledge ids
		List<String> knowledgeIds = JsonUtil.toJsonObjectArray(role.getKnowledgeIds(), String.class);
		// get course ids
		List<String> courseIds = knowledgeService.getCourseByIds(knowledgeIds);
		// get courses
		List<CourseName> courseNameList = courseNameService.selectByIds(courseIds);
		if (courseNameList.isEmpty()) {
			return res;
		}
		Map<String, CourseName> courseNameMap = StreamApiUtils.toMap(courseNameList,
				CourseName::getResourceId, courseName -> courseName);
		// get saved old order
		List<String> oldOrder = JsonUtil.toJsonObjectArray(role.getKnowledgeCourses(), String.class);
		// merge with current order
		for (String courseId : oldOrder) {
			CourseName courseName  = courseNameMap.get(courseId);
			if(null != courseName){
				CourseConfigOutput output = new CourseConfigOutput();
				output.setName(courseName.getName());
				output.setResourceId(courseName.getResourceId());
				// 按保存顺序加入
				res.add(output);
				courseNameMap.remove(courseId);
			}
		}
		// 加入剩余的
		for (CourseName courseName : courseNameMap.values()) {
			CourseConfigOutput output = new CourseConfigOutput();
			output.setName(courseName.getName());
			output.setResourceId(courseName.getResourceId());
			res.add(output);
		}
		// save new order
		List<String> newOrder = res.stream().map(CourseConfigOutput::getResourceId).collect(Collectors.toList());
		String newOrderJson = JsonUtil.toJsonString(newOrder);
		if(!newOrderJson.equals(role.getKnowledgeCourses())){
			role.setKnowledgeCourses(newOrderJson);
			this.updateById(role);
		}

		return res;
	}

	@Override
	public List<MissionTemplateSimple> getMissionList(String roleId) {

		List<String> missionIds = this.getRelMissionIds(roleId);
		Map<String, MissionTemplateRes> missionMapping = missionTemplateService
				.mapWithBusiness(missionIds);
		// merge with current order
		Role role = this.getOr404(roleId);
		List<String> currentMissionIds = JsonUtil.toJsonObjectArray(role.getSkillMissions(),
				String.class);
		List<MissionTemplateRes> newOrder = Lists.newArrayList();
		for (String missionId : currentMissionIds) {
			MissionTemplateRes mission = missionMapping.get(missionId);
			if (mission != null) {
				newOrder.add(mission);
				missionMapping.remove(missionId);
			}
		}
		for (Map.Entry<String, MissionTemplateRes> entry : missionMapping.entrySet()) {
			newOrder.add(entry.getValue());
		}
		// save new order
		List<String> newOrderIds = newOrder.stream().map(MissionTemplateRes::getResourceId)
				.collect(Collectors.toList());
		String newOrderJson = JsonUtil.arrayToString(newOrderIds);
		if (!newOrderJson.equals(role.getSkillMissions())) {
			role.setSkillMissions(JsonUtil.arrayToString(newOrderIds));
			this.updateById(role);
		}
		// convert
		List<MissionTemplateSimple> result = BeanUtils.deepCopyList(newOrder,
				MissionTemplateSimple.class);

		return result;
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