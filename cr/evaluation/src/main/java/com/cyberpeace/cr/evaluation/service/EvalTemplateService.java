package com.cyberpeace.cr.evaluation.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTemplate;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskSlimContext;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskStepConfig;
import com.cyberpeace.cr.evaluation.service.context.EvalTemplateBaseInfoContext;
import com.cyberpeace.cr.evaluation.service.context.EvalTemplateListQuery;
import com.cyberpeace.integratation.model.scene.MissionStartContext;

/**
 * 测评靶场-模板的处理
 */
public interface EvalTemplateService extends IService<EvalTemplate> {

	/** 校验用户身份并获取测评靶场模板实体
	 * @param templateId
	 * @return
	 */
	EvalTemplate getTemplate(String templateId);

	/** 列表查询测试靶场
	 * @param evalTemplateListQuery
	 * @return
	 */
	IPage<EvalTemplate> list(EvalTemplateListQuery evalTemplateListQuery);

	/** 查询测试靶场模板的任务列表
	 * @param evalTemplate
	 * @param isFinished
	 * @return
	 */
	List<EvalTaskSlimContext> listTask(EvalTemplate evalTemplate, Boolean isFinished);

	/** 获取Logo文件路径
	 * @param attachmentId
	 * @return
	 */
	String getLogoPath(String attachmentId);

	/** 批量删除测评靶场模板
	 * @param templateIds
	 * @return
	 */
	Boolean batchDelete(List<String> templateIds);

	/** 判断模板下是否存在指定状态的任务
	 * @param templateId
	 * @param taskStatus
	 * @return
	 */
	Boolean validateExistTask(String templateId, EvalTask.Status taskStatus);

	/** 校验模板的基础信息
	 * @param evalTemplateBaseInfoContext
	 * @return
	 */
	Boolean validateBaseInfo(EvalTemplateBaseInfoContext evalTemplateBaseInfoContext);

	/** 校验模板的步骤配置
	 * @param evalTaskStepConfigList
	 * @return
	 */
	Boolean validateStepConfig(List<EvalTaskStepConfig> evalTaskStepConfigList);

    void executeCommond(MissionStartContext missionStartContext);
}
