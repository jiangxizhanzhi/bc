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

/**
 * 角色Service实现
 *
 * @author SN
 * @date 2024/11/07
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

	/**
	 * 角色Mapper
	 */
	@Autowired
	RoleMapper roleMapper;

	/**
	 * 知识Service
	 */
	@Autowired
	private KnowledgeService knowledgeService;

	/**
	 * 角色技能关系Mapper
	 */
	@Autowired
	private RoleSkillRelationMapper roleSkillRelationMapper;

	/**
	 * 事务模板
	 */
	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * 技能Mapper
	 */
	@Autowired
	private SkillMapper skillMapper;

	/**
	 * 任务模板Service
	 */
	@Autowired
	private MissionTemplateService missionTemplateService;

	/**
	 * 课程名称Service
	 */
	@Autowired
	private CourseNameService courseNameService;

	/**
	 * 获取OR404
	 *
	 * @param roleId 角色id
	 * @return {@code Role }
	 */
	@Override
	public Role getOr404(String roleId) {
		Role role = getById(roleId);
		if (role == null) {
			throw new BusinessException(RtksBusinessErrorCode.ROLE_NOT_EXIST);
		}
		return role;
	}

	/**
	 * 全部删除
	 *
	 * @param table 桌子
	 */
	@Override
	public void removeAll(String table) {
		roleMapper.removeAll(table);
	}

	/**
	 * init default from preset （从预设初始化）
	 *
	 * @param table 桌子
	 */
	@Override
	public void initDefaultFromPreset(String table) {
		roleMapper.removeAll(table);
		roleMapper.copyFromPreset(table);
	}

	/**
	 * 更新批量由修改相关id列表
	 *
	 * @param id   id
	 * @param type 类型
	 */
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

	/**
	 * 更新批量由删除相关id列表
	 *
	 * @param deleteReq 删除请求体
	 * @param type      类型
	 */
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

	/**
	 * 删除技能相对由技能id列表
	 *
	 * @param skillIds 技能id列表
	 */
	@Override
	public void deleteSkillRelBySkillIds(List<String> skillIds) {
		if (skillIds.isEmpty()) {
			return;
		}
		roleSkillRelationMapper.deleteBySkillIds(skillIds);
	}

	/**
	 * 新增技能关系
	 *
	 * @param resourceId     资源id
	 * @param skillIdWeights 技能id权重
	 */
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

	/**
	 * 更新技能 rel
	 *
	 * @param resourceId     资源id
	 * @param skillIdWeights 技能id权重
	 */
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

	/**
	 * 更新技能 rel simple
	 *
	 * @param roleId         角色id
	 * @param skillIdWeights 技能id权重
	 */
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

	/**
	 * 列表技能由角色id
	 *
	 * @param roleId 角色id
	 * @return {@code List<SkillWeightRes> }
	 */
	@Override
	public List<SkillWeightRes> listSkillByRoleId(String roleId) {
		return roleSkillRelationMapper.listSkillByRoleId(roleId);
	}

	/**
	 * 删除技能相对由角色id列表
	 *
	 * @param roleIds 角色id列表
	 */
	@Override
	public void deleteSkillRelByRoleIds(List<String> roleIds) {
		if (roleIds.isEmpty()) {
			return;
		}
		roleSkillRelationMapper.deleteByRoleIds(roleIds);
	}

	/**
	 * 地图角色技能计数
	 *
	 * @param roleIds 角色id列表
	 * @return {@code Map<String, RoleSkillRes> }
	 */
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

	/**
	 * 获取相对任务id列表
	 *
	 * @param roleId 角色id
	 * @return {@code List<String> }
	 */
	@Override
	public List<String> getRelMissionIds(String roleId) {
		return roleSkillRelationMapper.getMissionIdsByRoleId(roleId);
	}

	/**
	 * 明确角色
	 */
	@Override
	public void clearRole() {
		roleMapper.clearRole();
		roleSkillRelationMapper.clearRoleSkillRel();
	}

	/**
	 * 如果名称重复
	 *
	 * @param name       名称
	 * @param resourceId 资源id
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean ifNameDuplicate(String name, String resourceId) {
		return roleMapper.ifNameDuplicate(name, resourceId);
	}

	/**
	 * 获取课程列表
	 *
	 * @param roleId 角色id
	 * @return {@code List<CourseConfigOutput> }
	 */
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

	/**
	 * 获取任务列表
	 *
	 * @param roleId 角色id
	 * @return {@code List<MissionTemplateSimple> }
	 */
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

	/**
	 * 删除id列表
	 *
	 * @param Ids id列表
	 * @param ids id列表
	 * @return {@code String }
	 */
	private String deleteIds(String Ids, List<String> ids) {

		List<String> originIds = JsonUtil.toJsonObjectArray(Ids, String.class);
		originIds.removeAll(ids);
		return JsonUtil.toJsonString(originIds);

	}

	/**
	 * 获取角色由水平id
	 *
	 * @param levelId 水平id
	 * @return {@code List<Role> }
	 */
	@Override
	public List<Role> getRolesByLevelId(String levelId) {
		return roleMapper.getRolesUnderLevel(levelId);
	}

	/**
	 * 获取角色id列表由水平id列表
	 *
	 * @param ids id列表
	 * @return {@code List<String> }
	 */
	@Override
	public List<String> getRoleIdsByLevelIds(List<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return new ArrayList<>();
		}
		return roleMapper.getRoleIdsByLevelIds(ids);
	}

	/**
	 * 列出角色
	 *
	 * @param page    分页
	 * @param keyword 关键词
	 * @param frameId 框架id
	 * @return {@code IPage<Role> }
	 */
	@Override
	public IPage<Role> listRoles(Page<Role> page, String keyword, String frameId) {
		return roleMapper.listRole(page, keyword, frameId);
	}

}