/**
 * 这段代码定义了一个基于 MyBatis 和 MyBatis-Plus 的数据访问对象（DAO）接口 `MissionSchemeTemplateMapper`，用于操作数据库中的 `MissionSchemeTemplate` 实体类。以下是代码的主要分析： 
 * ### 主要功能： 
 * 1. **继承与扩展**： 
 * - 接口继承了 `BaseMapper<MissionSchemeTemplate>`，这是 MyBatis-Plus 提供的基础 CRUD 操作接口。 
 * - 使用了 `@Mapper` 注解，表明这是一个 MyBatis 的 Mapper 接口。 
 * - 使用了 `@Repository` 注解，表明这是一个 Spring 的数据访问组件。 
 * 2. **自定义方法**： 
 * - **分页查询**：`list(Page page, String pattern)` 方法支持分页查询，并通过传入的 `pattern` 参数进行模糊匹配。 
 * - **批量插入**：`batchInsert(List<MissionSchemeTemplate> missionSchemeTemplates)` 方法用于批量插入数据。 
 * - **按标题检查是否存在**：`existByTitle(String title)` 方法用于判断某个标题是否已存在。 
 * 3. **注解使用**： 
 * - `@Param` 注解用于为方法参数指定名称，便于在 MyBatis XML 或注解方式中引用。 
 * ### 总结： 
 * 该接口提供了基本的 CRUD 功能以及一些扩展功能（如分页、批量操作和条件查询），是典型的 MyBatis-Plus 使用场景。 
**/
package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.dao.entity.MissionSchemeTemplate;

/**
 * 任务方案模板Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
@Repository
public interface MissionSchemeTemplateMapper extends BaseMapper<MissionSchemeTemplate> {

	/**
	 * 列表
	 *
	 * @param page    分页
	 * @param pattern 模式
	 * @return {@code IPage<MissionSchemeTemplate> }
	 */
	IPage<MissionSchemeTemplate> list(Page page, String pattern);

	/**
	 * 批量插入
	 *
	 * @param missionSchemeTemplates 任务计划模板
	 */
	void batchInsert(
			@Param("missionSchemeTemplates") List<MissionSchemeTemplate> missionSchemeTemplates);

	/**
	 * 按标题存在
	 *
	 * @param title 标题
	 * @return {@code Boolean }
	 */
	Boolean existByTitle(@Param("title") String title);

}
