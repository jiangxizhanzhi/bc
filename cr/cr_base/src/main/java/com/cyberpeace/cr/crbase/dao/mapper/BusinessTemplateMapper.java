/**
 * 这段代码定义了一个名为 `BusinessTemplateMapper` 的接口，继承自 MyBatis 的 `BaseMapper` 和 Spring 的 `Repository` 注解，用于操作数据库中的 `BusinessTemplate` 实体及相关数据。以下是代码的简要分析： 
 * 1. **注解与继承**： 
 * - 使用了 `@Mapper` 注解，表明这是一个 MyBatis 的 Mapper 接口。 
 * - 继承了 `BaseMapper<BusinessTemplate>`，提供了基本的 CRUD 操作。 
 * - 使用了 `@Repository` 注解，将其标记为一个 Spring 容器中的 Bean。 
 * 2. **方法功能**： 
 * - **分页查询**：如 `listTeamTemplate` 和 `listSimple` 方法，支持根据条件进行分页查询。 
 * - **根据 ID 查询**：如 `selectIdsByUserId`、`selectBusinessTemplatesByUserId` 等方法，通过用户 ID 查询相关的模板或模板 ID。 
 * - **根据条件查询**：如 `selectByNameAndType`、`selectByLocalSceneId` 等方法，通过特定字段（如名称、类型、场景 ID）查询模板。 
 * - **存在性检查**：如 `existByName`、`existByNameAndTypeAndId` 等方法，用于检查某些条件是否存在。 
 * - **批量操作**：如 `getAttackRelatedIds`、`getEngageRelatedIds`、`getBatchIdsByNames` 等方法，处理批量数据查询。 
 * - **获取全部数据**：如 `getAllIds` 和 `getAllByIds` 方法，获取所有模板 ID 或根据 ID 列表获取模板。 
 * 3. **参数与返回值**： 
 * - 大多数方法使用了 `@Param` 注解来指定参数名称，便于 MyBatis 映射到 SQL 中。 
 * - 返回值类型多样，包括 `IPage<T>`（分页结果）、`List<T>`（集合）、`String`（单个值）和 `Boolean`（布尔值）。 
 * 4. **设计目的**： 
 * - 该接口旨在提供对 `BusinessTemplate` 及其相关数据的全面操作能力，适用于复杂的业务逻辑需求。 
 * - 方法命名清晰，涵盖了常见的查询、插入、更新、删除等操作场景。 
 * 总体而言，这段代码是一个典型的 MyBatis Mapper 接口，设计合理且功能全面，适合在实际项目中使用。 
**/
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
 * 业务模板Mapper
 *
 * @author SN
 * @date 2024/11/08
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
	 */
	IPage<BusinessTemplateData> listTeamTemplate(Page page,
			@Param("listQuery") BusinessTemplateQuery listQuery);

	/**
	 * 查询id列表由用户id
	 *
	 * @param userId 用户id
	 * @return {@code List<String> }
	 */
	List<String> selectIdsByUserId(@Param("userId") String userId);

	/**
	 * 查询业务模板由用户id
	 *
	 * @param userId 用户id
	 * @return {@code List<BusinessTemplate> }
	 */
	List<BusinessTemplate> selectBusinessTemplatesByUserId(@Param("userId") String userId);

	/**
	 * 查询由名称和类型
	 *
	 * @param name 名称
	 * @param type 类型
	 * @param id   id
	 * @return {@code BusinessTemplate }
	 */
	BusinessTemplate selectByNameAndType(String name, Integer type, String id);

	/**
	 * 查询由当地场景id
	 *
	 * @param localSceneId 当地场景id
	 * @return {@code BusinessTemplate }
	 */
	BusinessTemplate selectByLocalSceneId(String localSceneId);

	/**
	 * 存在由名称
	 *
	 * @param templateName 模板名称
	 * @return {@code Boolean }
	 */
	Boolean existByName(@Param("templateName") String templateName);

	/**
	 * 获取存在名字
	 *
	 * @param templateNames 模板名称
	 * @return {@code List<String> }
	 */
	List<String> getExistedNames(@Param("templateNames") List<String> templateNames);

	/**
	 * 获取id由名称
	 *
	 * @param templateName 模板名称
	 * @return {@code String }
	 */
	String getIdByName(@Param("templateName") String templateName);

	/**
	 * 校验模板名称
	 *
	 * @param templateName 模板名称
	 * @param type         类型
	 * @param templateId   模板id
	 * @return {@code Boolean }
	 */
	Boolean existByNameAndTypeAndId(@Param("templateName") String templateName,
			@Param("type") Integer type, @Param("templateId") String templateId);

	/**
	 * 获取攻击相关id列表
	 *
	 * @param businessTemplateIds 业务模板id列表
	 * @return {@code List<String> }
	 */
	List<String>
			getAttackRelatedIds(@Param("businessTemplateIds") List<String> businessTemplateIds);

	/**
	 * 获取聘相关id列表
	 *
	 * @param businessTemplateIds 业务模板id列表
	 * @return {@code List<String> }
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
	 */
	IPage<BusinessTemplateSimple> listSimple(@Param("page") Page page, @Param("type") Integer type,
			@Param("difficulty") Integer difficulty, @Param("pattern") String pattern);

	/**
	 * 获取批量id列表由名字
	 *
	 * @param businessTemplateNames 业务模板名字
	 * @param type                  类型
	 * @return {@code List<String> }
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
	 */
	List<BusinessTemplate> getAllByIds(@Param("resourceIds") List<String> resourceIds,
			@Param("type") Integer type);

	/**
	 * 获取都id列表
	 *
	 * @return {@code List<String> }
	 */
	List<String> getAllIds();
}
