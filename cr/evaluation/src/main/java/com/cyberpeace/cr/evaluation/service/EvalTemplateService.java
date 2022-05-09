package com.cyberpeace.cr.evaluation.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.controller.req.EvalTemplateListQuery;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTemplate;

/**
 * 测试靶场-模板的处理
 */
public interface EvalTemplateService extends IService<EvalTemplate> {

	/** 列表查询测试靶场
	 * @param evalTemplateListQuery
	 * @return
	 */
	IPage<EvalTemplate> list(EvalTemplateListQuery evalTemplateListQuery);

	String getLogoPath(String attachmentId);

	Boolean batchDelete(List<String> templateIds);

	EvalTemplate getTemplate(String templateId);

}
