package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationContext;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationQuery;
import com.cyberpeace.cr.evaluation.controller.req.EvalRuleListReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalRuleReq;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRule;

import java.util.List;

/**
 *
 */
public interface TrainEvalService extends IService<EvalRule> {

	/**
	 * @return 分页结构数据
	 */
	ListRsp<EvalRule> pageList(EvalRuleListReq req);

	/**
	 * 创建
	 */
	Boolean create(EvalRuleReq req);

	/**
	 * 更新
	 */
	Boolean update(EvalRuleReq req);

	/**
	 * 获取
	 */
	EvalRule get(String resourceId);

	/**
	 * 批量删除
	 */
	Boolean delete(List<String> ids);

	/**
	 * 分页查询红蓝任务评估
	 *
	 * @param missionEvaluationQuery 分页查询体
	 * @return 任务评估
	 */
	IPage<MissionEvaluationContext>
			getRbMissionEvaluationList(MissionEvaluationQuery missionEvaluationQuery);




}
