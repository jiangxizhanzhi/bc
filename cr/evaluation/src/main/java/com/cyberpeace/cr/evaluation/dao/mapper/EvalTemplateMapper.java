package com.cyberpeace.cr.evaluation.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.evaluation.controller.req.EvalTemplateListQuery;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTemplate;

@Mapper
@Repository
public interface EvalTemplateMapper extends BaseMapper<EvalTemplate> {

	IPage<EvalTemplate> listEvalTemplates(Page page, EvalTemplateListQuery evalTemplateListQuery);

}
