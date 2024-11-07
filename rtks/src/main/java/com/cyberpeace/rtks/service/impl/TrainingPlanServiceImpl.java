package com.cyberpeace.rtks.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.dao.mapper.MissionTemplateMapper;
import com.cyberpeace.cr.crbase.service.context.mission.MissionTemplateRes;
import com.cyberpeace.rtks.constant.RtksBusinessErrorCode;
import com.cyberpeace.rtks.controller.RoleController;
import com.cyberpeace.rtks.controller.frame.LevelTreeOutput;
import com.cyberpeace.rtks.controller.res.KnowledgeRes;
import com.cyberpeace.rtks.controller.res.KnowledgeTreeRes;
import com.cyberpeace.rtks.controller.res.RelatedCourse;
import com.cyberpeace.rtks.controller.res.RoleDetailRes;
import com.cyberpeace.rtks.controller.role.RoleDetailOutput;
import com.cyberpeace.rtks.controller.role.RoleOutput;
import com.cyberpeace.rtks.dao.entity.Knowledge;
import com.cyberpeace.rtks.dao.entity.Level;
import com.cyberpeace.rtks.dao.entity.Role;
import com.cyberpeace.rtks.dao.mapper.KnowledgeMapper;
import com.cyberpeace.rtks.dao.mapper.RoleMapper;
import com.cyberpeace.rtks.dao.mapper.RoleSkillRelationMapper;
import com.cyberpeace.rtks.service.KnowledgeService;
import com.cyberpeace.rtks.service.TrainingPlanService;
import com.cyberpeace.rtks.service.bo.skill.MissionTemplateSimple;
import com.cyberpeace.rtks.service.bo.skill.SkillRes;
import com.cyberpeace.rtks.service.bo.skill.SkillWeightRes;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * 训练计划Service实现
 *
 * @author SN
 * @date 2024/11/07
 */
@Service
@Slf4j
public class TrainingPlanServiceImpl implements TrainingPlanService {

	/**
	 * 角色控制器
	 */
	@Autowired
	private RoleController roleController;

	/**
	 * 角色Mapper
	 */
	@Autowired
	private RoleMapper roleMapper;

	/**
	 * 角色技能关系Mapper
	 */
	@Autowired
	private RoleSkillRelationMapper roleSkillRelationMapper;

	/**
	 * 任务模板Mapper
	 */
	@Autowired
	private MissionTemplateMapper missionTemplateMapper;

	/**
	 * 知识Mapper
	 */
	@Autowired
	private KnowledgeMapper knowledgeMapper;

	/**
	 * 知识Service
	 */
	@Autowired
	private KnowledgeService knowledgeService;

	/**
	 * Parse tree 节点
	 *
	 * @param frame       框架
	 * @param roleListMap 角色列表映射
	 */
	public void parseTreeNode(LevelTreeOutput frame,
			Map<String, List<RoleDetailOutput>> roleListMap) {
		if (frame.getChildren() != null) {
			for (int index = 0; index < frame.getChildren().size(); index++) {
				LevelTreeOutput element = frame.getChildren().get(index);

				if (element.getChildren() == null) {
					// 处理叶子结点
					element.setRoleDetailList(roleListMap.get(element.getResourceId()));
				} else {
					parseTreeNode(element, roleListMap);
				}
			}
		}

	}

	/**
	 * 解析树
	 *
	 * @param frame 框架
	 */
	@Override
	public void parseTree(LevelTreeOutput frame) {
		List<Role> roleList = roleMapper.selectRoleByFrameId(frame.getResourceId());
		List<RoleDetailOutput> roleDetailOutputList = Lists.newArrayList();

		for (Role role : roleList) {
			RoleDetailOutput detailOutputFromRole = roleController.getOutputFromEntity(role);
			roleDetailOutputList.add(detailOutputFromRole);
		}
		// 以层级对角色进行分组
		Map<String, List<RoleDetailOutput>> roleListMap = roleDetailOutputList.stream()
				.collect(Collectors.groupingBy(RoleOutput::getLevelId));

		parseTreeNode(frame, roleListMap);
	}

	/**
	 * 获取知识树
	 *
	 * @return {@code List<KnowledgeTreeRes> }
	 */
	@Override
	public List<KnowledgeTreeRes> getKnowledgeTree() {
		List<KnowledgeTreeRes> knowledgeTreeResList = Lists.newArrayList();
		List<KnowledgeRes> knowledgeResList = knowledgeService.getAllKnowledge();

		Map<String, List<KnowledgeRes>> knowledgeResMap = knowledgeResList.stream()
				.collect(Collectors.groupingBy(KnowledgeRes::getCategory));
		for (String key : knowledgeResMap.keySet()) {
			KnowledgeTreeRes knowledgeTreeRes = new KnowledgeTreeRes();
			knowledgeTreeRes.setCategory(key);
			knowledgeTreeRes.setKnowledgeResList(knowledgeResMap.get(key));
			knowledgeTreeResList.add(knowledgeTreeRes);
		}

		return knowledgeTreeResList;
	}

	/**
	 * 获取角色输出由角色id
	 *
	 * @param resource_id 资源id
	 * @param type        类型
	 * @return {@code RoleDetailRes }
	 */
	@Override
	public RoleDetailRes getRoleOutputByRoleId(String resource_id, String type) {
		Role role = getRoleByRoleId(resource_id);

		RoleDetailOutput roleDetailOutput = roleController.getOutputFromEntity(role);
		RoleDetailRes roleDetailRes = BeanUtils.deepCopy(roleDetailOutput, RoleDetailRes.class);
		// roleDetailRes.setCategory(roleDetailOutput.getCategoryName());

		// 获取知识点的课程列表
		List<Knowledge> knowledgeDataList = roleDetailOutput.getKnowledgeDataList();
		List<KnowledgeRes> knowledgeResList = BeanUtils.deepCopyList(knowledgeDataList,
				KnowledgeRes.class);

		knowledgeService.checkKnowledgeRes(knowledgeResList, type);
		roleDetailRes.setKnowledgeResDataList(knowledgeResList);

		// 获取技能的课程列表
		List<SkillWeightRes> skillDataList = roleDetailOutput.getSkillDataList();
		List<SkillRes> skillResList = BeanUtils.deepCopyList(skillDataList, SkillRes.class);

		roleDetailRes.setSkillResDataList(skillResList);

		return roleDetailRes;
	}

	/**
	 * 获取知识课程列表
	 *
	 * @param pageReqBase 分页请求体基础
	 * @param existRoleId 存在角色id
	 * @return {@code ListRsp<KnowledgeRes> }
	 */
	@Override
	public ListRsp<KnowledgeRes> getKnowledgeCourseList(PageReqBase pageReqBase,
			String existRoleId) {
		Page page = pageReqBase.toPageParam();
		List<String> knowledgeIds = getKnowledgeIdsByRole(existRoleId);

		IPage<KnowledgeRes> result = knowledgeMapper.getKnowledgeListByIds(page, knowledgeIds);

		List<KnowledgeRes> knowledgeResList = result.getRecords();
		knowledgeService.checkKnowledgeRes(knowledgeResList, RoleDetailRes.RELATED_COURSE_ALL);

		return PageUtil.getListResult(result);
	}

	/**
	 * 获取学习路径树
	 *
	 * @param resource_id 资源id
	 * @param type        类型
	 * @return {@code RoleDetailRes }
	 */
	@Override
	public RoleDetailRes getLearningPathTree(String resource_id, String type) {
		Role role = getRoleByRoleId(resource_id);
		if (null == role) {
			throw new BusinessException(RtksBusinessErrorCode.ROLE_NOT_EXIST);
		}

		RoleDetailOutput roleDetailOutput = roleController.getOutputFromEntity(role);
		RoleDetailRes roleDetailRes = BeanUtils.deepCopy(roleDetailOutput, RoleDetailRes.class);

		List<Level> levelList = roleMapper.selectLevelsByRoleId(resource_id);
		if (levelList.size() > 0) {
			roleDetailRes.setLevelTreeOutput(getLevelTree(levelList, role.getLevelId()));
		}

		// 获取知识点的课程列表
		List<Knowledge> knowledgeDataList = roleDetailOutput.getKnowledgeDataList();
		List<KnowledgeRes> knowledgeResList = BeanUtils.deepCopyList(knowledgeDataList,
				KnowledgeRes.class);

		knowledgeService.checkKnowledgeRes(knowledgeResList, type);
		List<RelatedCourse> basicCourseList = Lists.newArrayList();
		for (KnowledgeRes knowledgeRes : knowledgeResList) {
			if (knowledgeRes.getCourseList() != null) {
				basicCourseList.addAll(knowledgeRes.getCourseList());
			}
		}
		roleDetailRes.setBasicCourseList(basicCourseList);

		// 获取技能的任务列表
		List<String> missionTemplateIds = roleSkillRelationMapper
				.getMissionIdsByRoleId(resource_id);
		List<MissionTemplateRes> missionTemplateResList = missionTemplateMapper
				.listWithBusiness(missionTemplateIds);
		List<MissionTemplateSimple> missions = BeanUtils.deepCopyList(missionTemplateResList,
				MissionTemplateSimple.class);
		roleDetailRes.setMissions(missions);

		return roleDetailRes;
	}

	/**
	 * 获取角色由角色id
	 *
	 * @param existRoleId 存在角色id
	 * @return {@code Role }
	 */
	@Override
	public Role getRoleByRoleId(String existRoleId) {
		Role role = roleMapper.selectById(existRoleId);
		if (null == role) {
			throw new BusinessException(RtksBusinessErrorCode.ROLE_NOT_EXIST);
		}

		return role;
	}

	/**
	 * 获取角色详情结果由角色id
	 *
	 * @param existRoleId 存在角色id
	 * @return {@code RoleDetailRes }
	 */
	@Override
	public RoleDetailRes getRoleDetailResByRoleId(String existRoleId) {
		Role role = getRoleByRoleId(existRoleId);
		RoleDetailOutput roleDetailOutput = roleController.getOutputFromEntity(role);
		RoleDetailRes roleDetailRes = BeanUtils.deepCopy(roleDetailOutput, RoleDetailRes.class);

		return roleDetailRes;
	}

	/**
	 * 获取水平树
	 *
	 * @param levelList 级别列表
	 * @param levelId   水平id
	 * @return {@code LevelTreeOutput }
	 */
	private LevelTreeOutput getLevelTree(List<Level> levelList, String levelId) {
		LevelTreeOutput levelTreeOutput = new LevelTreeOutput();
		Level level = new Level();
		Map<String, Level> levelMap = Maps.newHashMap();
		List<Level> newlevelList = Lists.newArrayList();

		for (Level level1 : levelList) {
			if (StringUtils.equals(level1.getResourceId(), levelId)) {
				level = level1;
				newlevelList.add(level);
				break;
			}

		}
		int index = 1;
		while (index < 10 && StringUtils.isNotBlank(level.getParent())) {
			levelMap.put(level.getParent(), level);

			for (Level level1 : levelList) {
				if (StringUtils.equals(level1.getResourceId(), level.getParent())) {

					level = level1;
					newlevelList.add(level);
					index++;
					break;
				}
			}
		}
		if (index >= 10 || index == 1) {
			log.error("level Loop is out of range！levelId=[{}],loop is [{}]", level.getResourceId(),
					index - 1);
			throw new BusinessException(RtksBusinessErrorCode.INVALID_LEVEL);
		}

		for (Level level1 : newlevelList) {
			if (StringUtils.isBlank(level1.getParent())) {
				// 如果是英语环境，修改名称
				if ("default".equals(level.getResourceId()) && !CommonContext.isChinese()) {
					level.setName(Level.DefaultName.ENGLISH.getDesc());
				}
				levelTreeOutput = getLevelTreeOutputFromLevel(level1);
			}
		}

		LevelTreeOutput tempLevelTreeOutput = levelTreeOutput;
		String childId = levelTreeOutput.getResourceId();
		while (levelMap.get(childId) != null) {
			List<LevelTreeOutput> children = Lists.newArrayList();
			LevelTreeOutput child = getLevelTreeOutputFromLevel(levelMap.get(childId));
			children.add(child);
			tempLevelTreeOutput.setChildren(children);
			tempLevelTreeOutput = child;
			childId = child.getResourceId();
		}
		return levelTreeOutput;
	}

	/**
	 * 获取水平树输出从水平
	 *
	 * @param frame 框架
	 * @return {@code LevelTreeOutput }
	 */
	LevelTreeOutput getLevelTreeOutputFromLevel(Level frame) {
		LevelTreeOutput output = new LevelTreeOutput();

		output.setResourceId(frame.getResourceId());
		output.setName(frame.getName());
		output.setDescription(frame.getDescription());

		return output;
	}

	/**
	 * 根据岗位编号查询知识点编号
	 *
	 * @param roleId
	 * @return
	 */
	private List<String> getKnowledgeIdsByRole(String roleId) {
		Role role = getRoleByRoleId(roleId);
		if (null == role) {
			return Lists.newArrayList();
		}
		List<String> knowledgeIds = JsonUtil.toJsonObjectArray(role.getKnowledgeIds(),
				String.class);
		return knowledgeIds;
	}

}