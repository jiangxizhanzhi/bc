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

/**
 * 测评靶场-模板的处理
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalTemplateService` 的接口，该接口继承自 `IService<EvalTemplate>`。这个接口主要处理与“测评靶场模板”相关的操作。以下是对其方法的简要分析： 
 * 1. **getTemplate(String templateId)**: 
 * - 功能：根据给定的模板ID校验用户身份，并返回对应的测评靶场模板实体。 
 * - 参数：`templateId` - 模板的唯一标识符。 
 * - 返回值：`EvalTemplate` 对象。 
 * 2. **list(EvalTemplateListQuery evalTemplateListQuery)**: 
 * - 功能：根据传入的查询条件分页查询测评靶场模板列表。 
 * - 参数：`evalTemplateListQuery` - 包含查询条件的对象。 
 * - 返回值：`IPage<EvalTemplate>` 对象，包含分页信息及模板列表。 
 * 3. **listTask(EvalTemplate evalTemplate, Boolean isFinished)**: 
 * - 功能：查询指定测评模板下的任务列表，并可按任务是否完成进行筛选。 
 * - 参数：`evalTemplate` - 要查询的任务所属的模板；`isFinished` - 是否仅返回已完成的任务（布尔值）。 
 * - 返回值：`List<EvalTaskSlimContext>` 对象，表示任务列表。 
 * 4. **getLogoPath(String attachmentId)**: 
 * - 功能：根据附件ID获取Logo文件的路径。 
 * - 参数：`attachmentId` - 附件的唯一标识符。 
 * - 返回值：表示Logo文件路径的字符串。 
 * 5. **batchDelete(List<String> templateIds)**: 
 * - 功能：批量删除多个测评靶场模板。 
 * - 参数：`templateIds` - 需要删除的模板ID列表。 
 * - 返回值：布尔值，表示删除操作是否成功。 
 * 6. **validateExistTask(String templateId, EvalTask.Status taskStatus)**: 
 * - 功能：判断指定模板下是否存在特定状态的任务。 
 * - 参数：`templateId` - 模板的唯一标识符；`taskStatus` - 任务的状态。 
 * - 返回值：布尔值，表示是否存在符合条件的任务。 
 * 7. **validateBaseInfo(EvalTemplateBaseInfoContext evalTemplateBaseInfoContext)**: 
 * - 功能：验证模板的基本信息。 
 * - 参数：`evalTemplateBaseInfoContext` - 包含模板基本信息的对象。 
 * - 返回值：布尔值，表示基本信息验证是否通过。 
 * 8. **validateStepConfig(List<EvalTaskStepConfig> evalTaskStepConfigList)**: 
 * - 功能：验证模板的步骤配置。 
 * - 参数：`evalTaskStepConfigList` - 模板步骤配置列表。 
 * - 返回值：布尔值，表示步骤配置验证是否通过。 
 * 总结：`EvalTemplateService` 接口提供了多种操作，包括查询、验证和管理测评靶场模板及其相关任务的功能。这些功能覆盖了从基础信息验证到任务状态检查等多个方面。 
**/
public interface EvalTemplateService extends IService<EvalTemplate> {

	/**
	 * 校验用户身份并获取测评靶场模板实体
	 *
	 * @param templateId
	 * @return
	 */
	EvalTemplate getTemplate(String templateId);

	/**
	 * 列表查询测试靶场
	 *
	 * @param evalTemplateListQuery
	 * @return
	 */
	IPage<EvalTemplate> list(EvalTemplateListQuery evalTemplateListQuery);

	/**
	 * 查询测试靶场模板的任务列表
	 *
	 * @param evalTemplate
	 * @param isFinished
	 * @return
	 */
	List<EvalTaskSlimContext> listTask(EvalTemplate evalTemplate, Boolean isFinished);

	/**
	 * 获取Logo文件路径
	 *
	 * @param attachmentId
	 * @return
	 */
	String getLogoPath(String attachmentId);

	/**
	 * 批量删除测评靶场模板
	 *
	 * @param templateIds
	 * @return
	 */
	Boolean batchDelete(List<String> templateIds);

	/**
	 * 判断模板下是否存在指定状态的任务
	 *
	 * @param templateId
	 * @param taskStatus
	 * @return
	 */
	Boolean validateExistTask(String templateId, EvalTask.Status taskStatus);

	/**
	 * 校验模板的基础信息
	 *
	 * @param evalTemplateBaseInfoContext
	 * @return
	 */
	Boolean validateBaseInfo(EvalTemplateBaseInfoContext evalTemplateBaseInfoContext);

	/**
	 * 校验模板的步骤配置
	 *
	 * @param evalTaskStepConfigList
	 * @return
	 */
	Boolean validateStepConfig(List<EvalTaskStepConfig> evalTaskStepConfigList);

}

