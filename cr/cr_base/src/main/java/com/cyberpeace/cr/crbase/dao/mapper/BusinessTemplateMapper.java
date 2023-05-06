package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplateData;
import com.cyberpeace.cr.crbase.service.context.BusinessTemplateQuery;
import com.cyberpeace.cr.crbase.service.context.template.BusinessTemplateSimple;

/**
 * 业务模板映射器
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Mapper
@Repository
public interface BusinessTemplateMapper extends BaseMapper<BusinessTemplate> {

	/**
	 * 团队模板列表
	 *
	 * @param page      页面
	 * @param listQuery 列表查询
	 * @return {@link IPage}<{@link BusinessTemplateData}>
	 */
	IPage<BusinessTemplateData> listTeamTemplate(Page page,
			@Param("listQuery") BusinessTemplateQuery listQuery);

	/**
	 * 获取id通过用户id
	 *
	 * @param userId 用户id
	 * @return {@link List}<{@link String}>
	 */
	List<String> selectIdsByUserId(@Param("userId") String userId);

	/**
	 * 通过名字和类型获取
	 *
	 * @param name 名字
	 * @param type 类型
	 * @param id   id
	 * @return {@link BusinessTemplate}
	 */
	BusinessTemplate selectByNameAndType(String name, Integer type, String id);

	/**
	 * 通过本地场景id获取
	 *
	 * @param localSceneId 本地场景id
	 * @return {@link BusinessTemplate}
	 */
	BusinessTemplate selectByLocalSceneId(String localSceneId);

	/**
	 * 存在名字
	 *
	 * @param templateName 模板名称
	 * @return {@link Boolean}
	 */
	Boolean existByName(@Param("templateName") String templateName);

	/**
	 * 获取存在名字
	 *
	 * @param templateNames 模板名称
	 * @return {@link List}<{@link String}>
	 */
	List<String> getExistedNames(@Param("templateNames") List<String> templateNames);

	/**
	 * 通过名字获取id
	 *
	 * @param templateName 模板名称
	 * @return {@link String}
	 */
	String getIdByName(@Param("templateName") String templateName);

	/**
	 * 存在名字和类型和id
	 * 校验模板名称
	 *
	 * @param templateName 模板名称
	 * @param type         类型
	 * @param templateId   模板id
	 * @return {@link Boolean}
	 */
	Boolean existByNameAndTypeAndId(@Param("templateName") String templateName,
			@Param("type") Integer type, @Param("templateId") String templateId);

	/**
	 * 获取攻击相关id
	 *
	 * @param businessTemplateIds 业务模板id
	 * @return {@link List}<{@link String}>
	 */
	List<String>
			getAttackRelatedIds(@Param("businessTemplateIds") List<String> businessTemplateIds);

	/**
	 * 获取参与相关id
	 *
	 * @param businessTemplateIds 业务模板id
	 * @return {@link List}<{@link String}>
	 */
	List<String>
			getEngageRelatedIds(@Param("businessTemplateIds") List<String> businessTemplateIds);

	/**
	 * 获取简单列表
	 *
	 * @param page       页面
	 * @param type       类型
	 * @param difficulty 难度
	 * @param pattern    模式
	 * @return {@link IPage}<{@link BusinessTemplateSimple}>
	 */
	IPage<BusinessTemplateSimple> listSimple(@Param("page") Page page, @Param("type") Integer type,
			@Param("difficulty") Integer difficulty, @Param("pattern") String pattern);

	/**
	 * 通过名字获取批处理id
	 *
	 * @param businessTemplateNames 业务模板名称
	 * @param type                  类型
	 * @return {@link List}<{@link String}>
	 */
	List<String> getBatchIdsByNames(
			@Param("business_template_names") List<String> businessTemplateNames,
			@Param("type") Integer type);

	/**
	 * 通过id获取所有
	 *
	 * @param resourceIds 资源id
	 * @param type        类型
	 * @return {@link List}<{@link BusinessTemplate}>
	 */
	List<BusinessTemplate> getAllByIds(@Param("resourceIds") List<String> resourceIds,
			@Param("type") Integer type);
}
