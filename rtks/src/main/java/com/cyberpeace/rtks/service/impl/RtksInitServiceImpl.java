package com.cyberpeace.rtks.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.rtks.dao.entity.Knowledge;
import com.cyberpeace.rtks.dao.entity.Role;
import com.cyberpeace.rtks.dao.entity.RoleSkillRelation;
import com.cyberpeace.rtks.dao.mapper.RtksInitMapper;
import com.cyberpeace.rtks.service.RtksInitService;
import com.cyberpeace.rtks.service.bo.KnowledgeUnderCourseBo;
import com.cyberpeace.rtks.service.bo.resource.IdAndWeight;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * RTKS初始化Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see RtksInitService
 * @since 1.0.0
 */
@Service
@Slf4j
public class RtksInitServiceImpl implements RtksInitService {

	/**
	 * RTKS初始化Mapper
	 *
	 * @see RtksInitMapper
	 */
	@Autowired
	private RtksInitMapper rtksInitMapper;

	/**
	 * 事务模板
	 *
	 * @see TransactionTemplate
	 */
	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * 获取初始化课程id列表由id列表
	 *
	 * @param knowledgeIds 知识点id列表
	 * @return {@code List<String> }
	 * @throws
	 * @see List<String>
	 * @see List<String>
	 */
	@Override
	public List<String> getInitCourseIdsByIds(List<String> knowledgeIds) {
		if (CollectionUtils.isEmpty(knowledgeIds)) {
			return Lists.newArrayList();
		}
		List<Knowledge> initKnowledgeList = rtksInitMapper.getInitKnowledgeByIds(knowledgeIds);
		List<String> courseIds = initKnowledgeList.stream()
				.map(knowledge -> JsonUtil.toJsonObjectArray(knowledge.getCourses(),
						KnowledgeUnderCourseBo.class))
				.flatMap(Collection::stream).map(KnowledgeUnderCourseBo::getResourceId).distinct()
				.collect(Collectors.toList());
		return courseIds;
	}

	/**
	 * 获取最后初始化层级名称
	 *
	 * @return {@code String }
	 * @throws
	 * @see String
	 */
	@Override
	public String getLastInitLevelName() {
		return rtksInitMapper.getLastInitLevelName();
	}

	/**
	 * 保存初始化岗位
	 *
	 * @param role 岗位
	 * @throws
	 * @see Role
	 */
	@Override
	public void saveInitRole(Role role) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				rtksInitMapper.copyRole(role.getResourceId());
				rtksInitMapper.updateRoleCourseIds(role.getResourceId(),
						role.getKnowledgeCourses());
			}
		});
	}

	/**
	 * 保存初始化岗位技能关联
	 *
	 * @param roleId          岗位id
	 * @param skillIdsWeights 技能id列表权重
	 * @throws
	 * @see String
	 * @see List<IdAndWeight>
	 */
	@Override
	public void saveInitRoleSkillRel(String roleId, List<IdAndWeight> skillIdsWeights) {
		if (CollectionUtils.isEmpty(skillIdsWeights)) {
			return;
		}
		List<RoleSkillRelation> relations = Lists.newArrayList();
		for (IdAndWeight item : skillIdsWeights) {
			RoleSkillRelation relation = new RoleSkillRelation();
			relation.setRoleId(roleId);
			relation.setSkillId(item.getId());
			relation.setWeight(item.getWeight());
			relations.add(relation);
		}
		rtksInitMapper.batchInsertRoleSkillRel(relations);
	}
}
