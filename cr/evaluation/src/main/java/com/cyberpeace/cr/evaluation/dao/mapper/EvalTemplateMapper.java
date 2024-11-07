package com.cyberpeace.cr.evaluation.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTemplate;
import com.cyberpeace.cr.evaluation.service.context.EvalTemplateListQuery;

/**
 * 评估模板Mapper
 *
 * @author SN
 * @date 2024/11/07
 */
@Mapper
@Repository
public interface EvalTemplateMapper extends BaseMapper<EvalTemplate> {

	/**
	 * 列出评估模板
	 *
	 * @param page                  分页
	 * @param evalTemplateListQuery EVAL 模板列表查询
	 * @param userId                用户id
	 * @return {@code IPage<EvalTemplate> }
	 */
	IPage<EvalTemplate> listEvalTemplates(Page page, EvalTemplateListQuery evalTemplateListQuery,
			String userId);

	/**
	 * 校验模板名称
	 *
	 * @param templateName 模板名称
	 * @param userId       用户id
	 * @param templateId   模板id
	 * @return {@code Boolean }
	 */
	Boolean existByNameAndUserIdAndId(@Param("templateName") String templateName,
			@Param("createUserId") String userId, @Param("templateId") String templateId);

}
