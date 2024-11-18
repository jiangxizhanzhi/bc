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
 * 业务模板映射
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface BusinessTemplateMapper extends BaseMapper<BusinessTemplate> {

	/**
	 * 列出团队模板
	 *
	 * @param page      分页
	 * @param listQuery 列表查询
	 * @return {@code IPage<BusinessTemplateData> }
	 * @throws
	 * @see Page
	 * @see BusinessTemplateQuery
	 * @see IPage<BusinessTemplateData>
	 */
	IPage<BusinessTemplateData> listTeamTemplate(Page page,
			@Param("listQuery") BusinessTemplateQuery listQuery);

	/**
	 * 查询id列表由用户id
	 *
	 * @param userId 用户id
	 * @return {@code List<String> }
	 * @throws
	 * @see String
	 * @see List<String>
	 */
	List<String> selectIdsByUserId(@Param("userId") String userId);

	/**
	 * 查询业务模板由用户id
	 *
	 * @param userId 用户id
	 * @return {@code List<BusinessTemplate> }
	 * @throws
	 * @see String
	 * @see List<BusinessTemplate>
	 */
	List<BusinessTemplate> selectBusinessTemplatesByUserId(@Param("userId") String userId);

	/**
	 * 查询由名称和类型
	 *
	 * @param name 名称
	 * @param type 类型
	 * @param id   id
	 * @return {@code BusinessTemplate }
	 * @throws
	 * @see String
	 * @see Integer
	 * @see String
	 * @see BusinessTemplate
	 */
	BusinessTemplate selectByNameAndType(String name, Integer type, String id);

	/**
	 * 查询由当地场景id
	 *
	 * @param localSceneId 当地场景id
	 * @return {@code BusinessTemplate }
	 * @throws
	 * @see String
	 * @see BusinessTemplate
	 */
	BusinessTemplate selectByLocalSceneId(String localSceneId);

	/**
	 * 存在由名称
	 *
	 * @param templateName 模板名称
	 * @return {@code Boolean }
	 * @throws
	 * @see String
	 * @see Boolean
	 */
	Boolean existByName(@Param("templateName") String templateName);

	/**
	 * 获取存在名字
	 *
	 * @param templateNames 模板名称
	 * @return {@code List<String> }
	 * @throws
	 * @see List<String>
	 * @see List<String>
	 */
	List<String> getExistedNames(@Param("templateNames") List<String> templateNames);

	/**
	 * 获取id由名称
	 *
	 * @param templateName 模板名称
	 * @return {@code String }
	 * @throws
	 * @see String
	 * @see String
	 */
	String getIdByName(@Param("templateName") String templateName);

	/**
	 * 校验模板名称
	 *
	 * @param templateName 模板名称
	 * @param type         类型
	 * @param templateId   模板id
	 * @return {@code Boolean }
	 * @throws
	 * @see String
	 * @see Integer
	 * @see String
	 * @see Boolean
	 */
	Boolean existByNameAndTypeAndId(@Param("templateName") String templateName,
			@Param("type") Integer type, @Param("templateId") String templateId);

	/**
	 * 获取攻击相关id列表
	 *
	 * @param businessTemplateIds 业务模板id列表
	 * @return {@code List<String> }
	 * @throws
	 * @see List<String>
	 * @see List<String>
	 */
	List<String>
			getAttackRelatedIds(@Param("businessTemplateIds") List<String> businessTemplateIds);

	/**
	 * 获取聘相关id列表
	 *
	 * @param businessTemplateIds 业务模板id列表
	 * @return {@code List<String> }
	 * @throws
	 * @see List<String>
	 * @see List<String>
	 */
	List<String>
			getEngageRelatedIds(@Param("businessTemplateIds") List<String> businessTemplateIds);

	/**
	 * 简单列表
	 *
	 * @param page       分页
	 * @param type       类型
	 * @param difficulty 难度
	 * @param pattern    模式
	 * @return {@code IPage<BusinessTemplateSimple> }
	 * @throws
	 * @see Page
	 * @see Integer
	 * @see Integer
	 * @see String
	 * @see IPage<BusinessTemplateSimple>
	 */
	IPage<BusinessTemplateSimple> listSimple(@Param("page") Page page, @Param("type") Integer type,
			@Param("difficulty") Integer difficulty, @Param("pattern") String pattern);

	/**
	 * 获取批量id列表由名字
	 *
	 * @param businessTemplateNames 业务模板名字
	 * @param type                  类型
	 * @return {@code List<String> }
	 * @throws
	 * @see List<String>
	 * @see Integer
	 * @see List<String>
	 */
	List<String> getBatchIdsByNames(
			@Param("business_template_names") List<String> businessTemplateNames,
			@Param("type") Integer type);

	/**
	 * 获取都由id列表
	 *
	 * @param resourceIds 资源id列表
	 * @param type        类型
	 * @return {@code List<BusinessTemplate> }
	 * @throws
	 * @see List<String>
	 * @see Integer
	 * @see List<BusinessTemplate>
	 */
	List<BusinessTemplate> getAllByIds(@Param("resourceIds") List<String> resourceIds,
			@Param("type") Integer type);

	/**
	 * 获取都id列表
	 *
	 * @return {@code List<String> }
	 * @throws
	 * @see List<String>
	 */
	List<String> getAllIds();
}
