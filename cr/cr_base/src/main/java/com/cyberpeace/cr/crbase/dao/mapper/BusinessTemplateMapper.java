package com.cyberpeace.cr.crbase.dao.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplateData;
import com.cyberpeace.cr.crbase.service.context.BusinessTemplateQuery;
import com.cyberpeace.cr.crbase.service.context.template.BusinessTemplateSimple;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BusinessTemplateMapper extends BaseMapper<BusinessTemplate> {

	IPage<BusinessTemplateData> listTeamTemplate(Page page,
			@Param("listQuery") BusinessTemplateQuery listQuery);

	List<String> selectIdsByUserId(@Param("userId") String userId);

	List<BusinessTemplate> selectBusinessTemplatesByUserId(@Param("userId") String userId);

	BusinessTemplate selectByNameAndType(String name, Integer type, String id);

	BusinessTemplate selectByLocalSceneId(String localSceneId);

	Boolean existByName(@Param("templateName") String templateName);

	List<String> getExistedNames(@Param("templateNames") List<String> templateNames);

	String getIdByName(@Param("templateName") String templateName);

	/**
	 * 校验模板名称
	 */
	Boolean existByNameAndTypeAndId(@Param("templateName") String templateName,
			@Param("type") Integer type, @Param("templateId") String templateId, @Param("trainCategory") Integer trainCategory);

	List<String>
			getAttackRelatedIds(@Param("businessTemplateIds") List<String> businessTemplateIds);

	List<String>
			getEngageRelatedIds(@Param("businessTemplateIds") List<String> businessTemplateIds);

	IPage<BusinessTemplateSimple> listSimple(@Param("page") Page page, @Param("type") Integer type,
			@Param("difficulty") Integer difficulty, @Param("pattern") String pattern);

	List<String> getBatchIdsByNames(
			@Param("business_template_names") List<String> businessTemplateNames,
			@Param("type") Integer type);

	List<BusinessTemplate> getAllByIds(@Param("resourceIds") List<String> resourceIds,
			@Param("type") Integer type);

	List<String> getAllIds();

	List<JSONObject> selectTeamTrainCategory(@Param("ids") List<String> ids);
}
