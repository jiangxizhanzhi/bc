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
 * 团队模板Service
 *
 * @author SN
 * @date 2024/11/14
 */
public interface TeamTemplateService extends IService<TeamTemplate> {

	/**
	 * 获取团队训练模板分页数据
	 *
	 * @param listQuery 分页查询体
	 * @return 团队训练模板分页数据
	 */
	IPage<TeamTemplateData> page(ListQuery listQuery);

	/**
	 * 创建团队训练模板实例
	 *
	 * @param teamTrainTemplateContext 团队训练模板实例数据
	 * @return 团队训练模板实例
	 */
	TeamTemplate create(TeamTrainTemplateContext teamTrainTemplateContext);

	/**
	 * 更新团队模板
	 *
	 * @param teamTrainTemplateContext 团队训练模板上下文
	 * @param businessTemplate         业务模板
	 * @return {@code TeamTemplate }
	 */
	TeamTemplate updateTeamTemplate(TeamTrainTemplateContext teamTrainTemplateContext,
			BusinessTemplate businessTemplate);

	/**
	 * 根据业务模板id获取团队模板
	 *
	 * @param resourceId 业务模板id
	 * @return 团队训练模板实例
	 */
	TeamTemplate getByTemplateBusinessId(String resourceId);

	/**
	 * 批量删除团队训练模板实例
	 *
	 * @param businessIds 业务模板id
	 */
	void batchDelete(List<String> businessIds);

	/**
	 * 获取团队模板由业务id列表
	 *
	 * @param businessIds 业务id列表
	 * @return {@code List<TeamTemplate> }
	 */
	List<TeamTemplate> getTeamTemplateByBusinessIds(List<String> businessIds);

	/**
	 * 删除由业务模板id
	 *
	 * @param businessTemplateId 业务模板id
	 * @return {@code Boolean }
	 */
	Boolean removeByBusinessTemplateId(String businessTemplateId);

	/**
	 * 从 roleConfig Json字符串读取任务模板
	 *
	 * @param roleConfig 角色配置json字符串
	 * @return 任务模板id列表
	 */
	List<String> getMissionTemplateIdsByRoleConfig(String roleConfig);
}
