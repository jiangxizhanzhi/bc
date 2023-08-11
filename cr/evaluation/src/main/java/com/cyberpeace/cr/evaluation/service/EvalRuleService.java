package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.evaluation.controller.req.EvalRuleListReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalRuleReq;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRule;

import java.util.List;

/**
 *
 */
public interface EvalRuleService extends IService<EvalRule> {

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
	 * 导出规则pdf
	 */
	String dumpEvalRule(String roomId);

	/**
	 * 初始化评估规则数据
	 */
	List<EvalRule> getInitialData(String evalTemplateId);
}
