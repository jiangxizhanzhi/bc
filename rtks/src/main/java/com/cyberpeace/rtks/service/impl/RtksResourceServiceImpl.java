package com.cyberpeace.rtks.service.impl;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.rtks.dao.entity.Role;
import com.cyberpeace.rtks.dao.mapper.LevelMapper;
import com.cyberpeace.rtks.service.KnowledgeService;
import com.cyberpeace.rtks.service.RoleService;
import com.cyberpeace.rtks.service.RtksInitService;
import com.cyberpeace.rtks.service.RtksResourceService;
import com.cyberpeace.rtks.service.bo.resource.IdAndWeight;
import com.cyberpeace.rtks.service.bo.resource.RtksResourceReq;
import com.cyberpeace.rtks.service.bo.resource.RtksRoleResource;
import com.cyberpeace.rtks.service.bo.resource.RtksRoleResourceBo;
import com.cyberpeace.rtks.service.bo.skill.IdWeight;

import lombok.extern.slf4j.Slf4j;

/**
 * RTKS资源Service实现
 *
 * @author SN
 * @date 2024/11/07
 */
@Slf4j
@Service
public class RtksResourceServiceImpl implements RtksResourceService {

	/**
	 * 水平Mapper
	 */
	@Autowired
	private LevelMapper levelMapper;

	/**
	 * 知识Service
	 */
	@Autowired
	private KnowledgeService knowledgeService;

	/**
	 * RTKS初始化Service
	 */
	@Autowired
	private RtksInitService rtksInitService;

	/**
	 * 角色Service
	 */
	@Autowired
	private RoleService roleService;

	/**
	 * 事务模板
	 */
	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * 负荷
	 *
	 * @param req 请求体
	 */
	@Override
	public void load(RtksResourceReq req) {
		RtksRoleResourceBo bo = req.getRtks();
		List<RtksRoleResource> resourceList;
		// 判断当前系统rtks是中文还是英文
		if (isChinese()) {
			resourceList = bo.getCn();
		} else {
			resourceList = bo.getEn();
		}
		// 无岗位资源
		if (CollectionUtils.isEmpty(resourceList)) {
			log.info("found no rtks role resource");
			return;
		}
		// 导入
		for (RtksRoleResource resource : resourceList) {
			try {
				saveResource(resource);
			} catch (Exception e) {
				log.error("failed to load rtks role resource: [{}]", resource.getName(), e);
			}
		}
	}

	/**
	 * 保存资源
	 *
	 * @param resource 资源
	 */
	private void saveResource(RtksRoleResource resource) {
		if (Boolean.TRUE.equals(roleService.ifNameDuplicate(resource.getName(), ""))) {
			log.info("duplicated rtks role: [{}]", resource.getName());
			return;
		}
		String levelName = resource.getLevel().split("/")[2].trim();
		String levelId = levelMapper.getLevelIdByName(levelName);
		if (null == levelId) {
			log.error("cant find level called [{}]", levelName);
			return;
		}
		Role role = new Role();
		role.setLevelId(levelId);
		role.setName(resource.getName());
		role.setDescription(resource.getDescription());
		role.setTaskIds(JsonUtil.toJsonString(resource.getTaskId()));
		role.setKnowledgeWeight(resource.getKnowledgeWeight());
		List<String> knowledgeIds = resource.getKnowledgeIdsWeights().stream()
				.map(IdAndWeight::getId).collect(Collectors.toList());
		role.setKnowledgeIds(JsonUtil.toJsonString(knowledgeIds));

		role.setKnowledgeIdWeights(JsonUtil.toJsonString(resource.getKnowledgeIdsWeights()));
		// rtks_role表
		List<String> courseIds = knowledgeService.getCourseByIds(knowledgeIds);
		role.setKnowledgeCourses(JsonUtil.toJsonString(courseIds));
		// init_rtks_role 表
		List<String> initCourseIds = rtksInitService.getInitCourseIdsByIds(knowledgeIds);

		// 该字段只用于保存课程顺序，初始状态没有顺序
		role.setSkillMissions(CommonConstant.EMPTY_LIST_JSON);
		// 填充其他字段
		role.setSkillIdWeights(CommonConstant.EMPTY_LIST_JSON);
		role.setSkillIds(CommonConstant.EMPTY_LIST_JSON);
		role.setAbilityIds(CommonConstant.EMPTY_LIST_JSON);
		role.setCategory("");
		// 设置id
		role.setResourceId(IDGeneratorUtil.getID());

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				// rtks_role
				roleService.save(role);
				// init_rtks_role
				role.setKnowledgeCourses(JsonUtil.toJsonString(initCourseIds));
				rtksInitService.saveInitRole(role);
				// skill, init_rtks_role_skill_relation
				rtksInitService.saveInitRoleSkillRel(role.getResourceId(),
						resource.getSkillIdsWeights());
				// rtks_role_skill_relation
				roleService.addSkillRelation(role.getResourceId(),
						convert(resource.getSkillIdsWeights()));
			}
		});
	}

	/**
	 * 是中国人
	 *
	 * @return boolean
	 */
	private boolean isChinese() {
		String lastLevelName = rtksInitService.getLastInitLevelName();
		Pattern r = Pattern.compile(PatternConstant.CHINESE_CHARACTERS);
		Matcher m = r.matcher(lastLevelName);
		return m.find();
	}

	/**
	 * 转换
	 *
	 * @param list 列表
	 * @return {@code List<IdWeight> }
	 */
	private List<IdWeight> convert(List<IdAndWeight> list) {
		List<IdWeight> res = Lists.newArrayList();
		for (IdAndWeight item : list) {
			IdWeight idWeight = new IdWeight();
			idWeight.setResourceId(item.getId());
			idWeight.setWeight(item.getWeight());
			res.add(idWeight);
		}
		return res;
	}
}
