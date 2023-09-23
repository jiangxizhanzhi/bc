package com.cyberpeace.cr.redvsblue.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTemplate;
import com.cyberpeace.cr.redvsblue.service.context.RbTemplateData;

@Mapper
public interface RbTemplateMapper extends BaseMapper<RbTemplate> {

	RbTemplate selectByBusinessTemplateId(String businessTemplateId);

	IPage<RbTemplateData> listRbTemplate(Page<RbTemplateData> page, ListQuery listQuery);

	List<RbTemplate> getRbTemplateByBusinessIds(List<String> ids);

	Boolean deleteByBusinessTemplateId(@Param("businessTemplateId") String businessTemplateId);

	List<RbTemplate> batchGetByBusinessId(List<String> businessTemplateIds);
}
