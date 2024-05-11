package com.cyberpeace.cr.evaluation.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTemplate;
import com.cyberpeace.cr.evaluation.service.context.EvalTemplateListQuery;

@Mapper
@Repository
public interface EvalTemplateMapper extends BaseMapper<EvalTemplate> {

	IPage<EvalTemplate> listEvalTemplates(Page<EvalTemplate> page, EvalTemplateListQuery evalTemplateListQuery,
			String userId);

	/**
	 * 校验模板名称
	 */
	Boolean existByNameAndUserIdAndId(@Param("templateName") String templateName,
			@Param("createUserId") String userId, @Param("templateId") String templateId);

	/**
	 * 根据模板id查询
	 * @param resourceId
	 * @return
	 */
    EvalTemplate selectByResourceId(@Param("resourceId") String resourceId);

	/**
	 * 根据模板ID查询场景resourceid
	 * @param resourceId
	 * @return
	 */
	String selectBysceneId(@Param("resourceId")String resourceId);
}
