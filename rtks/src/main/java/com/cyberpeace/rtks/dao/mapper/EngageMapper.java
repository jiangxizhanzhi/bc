package com.cyberpeace.rtks.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.service.context.template.BusinessTemplateSimple;
import com.cyberpeace.rtks.service.bo.career.EngageContext;
import com.cyberpeace.rtks.service.bo.career.RelCount;

@Mapper
@Repository
public interface EngageMapper {

	List<EngageContext> getAll();

	List<RelCount> countRelsByEngageId();

	List<String> getRelatedTemplateIds(@Param("resourceId") String resourceId);

	void deleteRel(@Param("resourceId") String resourceId,
			@Param("businessTemplateIds") List<String> businessTemplateIds);

	void addRel(@Param("resourceId") String resourceId,
			@Param("businessTemplateIds") List<String> businessTemplateIds);

	List<String> getRelByBusinessTemplateId(@Param("businessTemplateId") String businessTemplateId);

	IPage<BusinessTemplateSimple> getRelatedTemplates(@Param("page") Page page,
			@Param("resourceId") String resourceId, @Param("type") Integer type,
			@Param("difficulty") Integer difficulty, @Param("pattern") String pattern);
}
