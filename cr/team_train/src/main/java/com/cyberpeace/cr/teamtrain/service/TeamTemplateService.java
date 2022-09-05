package com.cyberpeace.cr.teamtrain.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplateData;
import com.cyberpeace.cr.teamtrain.service.context.TeamTrainTemplateContext;

/**
 *
 */
public interface TeamTemplateService extends IService<TeamTemplate> {

	/**
	 * 获取团队训练模板分页数据
	 * @param listQuery 分页查询体
	 * @return 团队训练模板分页数据
	 */
	IPage<TeamTemplateData> page(ListQuery listQuery);

	/**
	 * 创建团队训练模板实例
	 * @param teamTrainTemplateContext 团队训练模板实例数据
	 * @return 团队训练模板实例
	 */
	TeamTemplate create(TeamTrainTemplateContext teamTrainTemplateContext);

	TeamTemplate updateTeamTemplate(TeamTrainTemplateContext teamTrainTemplateContext,
			BusinessTemplate businessTemplate);

	/**
	 * 根据业务模板id获取团队模板
	 * @param resourceId 业务模板id
	 * @return 团队训练模板实例
	 */
	TeamTemplate getByTemplateBusinessId(String resourceId);

	/**
	 * 批量删除团队训练模板实例
	 * @param businessIds 业务模板id
	 */
	void batchDelete(List<String> businessIds);

	List<TeamTemplate> getTeamTemplateByBusinessIds(List<String> businessIds);

	Boolean removeByBusinessTemplateId(String businessTemplateId);

	/**
	 * 从 roleConfig Json字符串读取任务模板
	 * @param roleConfig 角色配置json字符串
	 * @return 任务模板id列表
	 */
	List<String> getMissionTemplateIdsByRoleConfig(String roleConfig);
}
