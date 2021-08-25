package com.cyberpeace.oj.rtks.service.impl;

import java.util.List;

import com.cyberpeace.oj.rtks.service.KnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.oj.rtks.dao.entity.Knowledge;
import com.cyberpeace.oj.rtks.dao.entity.Level;
import com.cyberpeace.oj.rtks.dao.entity.Role;
import com.cyberpeace.oj.rtks.service.RtksStatisticService;
import com.cyberpeace.oj.rtks.service.TreeService;
import com.cyberpeace.oj.rtks.service.bo.RtksRadarBo;
import com.google.common.collect.Lists;

@Service
public class RtksStatisticServiceImpl implements RtksStatisticService {

	@Autowired
	private TreeService treeService;

	@Autowired
	private KnowledgeService knowledgeService;

	@Override
	public String roleMatchPercent(String roleId, String userId) {
		return null;
	}

	@Override
	public List<RtksRadarBo> calculateRadar(String roleId, String userId) {
		List<RtksRadarBo> ret = Lists.newArrayList();

		// 1、获取当前岗位对应的框架数据
		Level nearestLevel = treeService.getNearestLevelByRole(roleId);
		String frameId = nearestLevel.getFrame();

		// 2、获取当前岗位对应的第一树形结构
		List<Level> firstLevel = treeService.getFirstLevel(frameId);

		// 3、计算当前树形节点的评分
		for (Level level : firstLevel) {
			String matchPercent = calculateRadarByLevel(level.getResourceId());

			RtksRadarBo rtksRadarBo = new RtksRadarBo();

			rtksRadarBo.setFirstTreeId(level.getResourceId());
			rtksRadarBo.setTreeName(level.getName());
			rtksRadarBo.setMatchPercent(matchPercent);

			ret.add(rtksRadarBo);
		}

		return ret;
	}

	/**
	 * 计算当前层级下所有知识点的完成度
	 * @param levelId
	 * @return
	 */
	private String calculateRadarByLevel(String levelId) {
		// 1、获取当前层级下所有role数据
		List<Role> roles = treeService.roleUnderLevel(levelId);

		// 2、获取所有知识点
		List<Knowledge> knowledges = mergeKnowledgeByRoles(roles);

		// 3、统计所有知识点的课时完成度
		String ret = calculateKnowledgeFinishPercent(knowledges);

		return ret;
	}

	/**
	 * 计算知识点涉及到的所有课时的完成度
	 * @return
	 */
	private String calculateKnowledgeFinishPercent(List<Knowledge> knowledges) {
		// 1、统计出知识点下的所有课程
		// 2、统计所有课程的总课时A；
		// 3、统计所有课程的已学课时B;
		// 4、B/A，向上取整
		// TODO
		return null;
	}

	/**
	 * 获取角色列表的所有知识点集合。
	 *
	 * 注意去重！
	 * @param roles
	 * @return
	 */
	private List<Knowledge> mergeKnowledgeByRoles(List<Role> roles) {
		// TODO
		return null;
	}

	/**
	 * 计算知识点的完成度。
	 * 计算方法：
	 * 该知识点下所有课程的学习进度和课程权重的加权平均。
	 * @param knowledgeId
	 * @return
	 */
	private String calculateKnowledgeFinishPercent(String knowledgeId) {
		// TODO
		return null;
	}

}
