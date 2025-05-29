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
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalTemplateMapper` 的接口，该接口继承了 `BaseMapper<EvalTemplate>`，并扩展了一些自定义的方法。以下是详细的分析： 
 * ### 包声明和导入 
 * - **包声明**：`com.cyberpeace.cr.evaluation.dao.mapper` 
 * - **导入的类和接口**： 
 * - `org.apache.ibatis.annotations.Mapper`：用于标识这是一个 MyBatis 的 Mapper。 
 * - `org.apache.ibatis.annotations.Param`：用于在方法参数上标记参数名。 
 * - `org.springframework.stereotype.Repository`：用于标识这是一个 Spring 的数据访问层组件。 
 * - `com.baomidou.mybatisplus.core.mapper.BaseMapper`：MyBatis-Plus 提供的基类，简化了基本的 CRUD 操作。 
 * - `com.baomidou.mybatisplus.core.metadata.IPage`：MyBatis-Plus 分页接口。 
 * - `com.baomidou.mybatisplus.extension.plugins.pagination.Page`：MyBatis-Plus 分页实现类。 
 * - `com.cyberpeace.cr.evaluation.dao.entity.EvalTemplate`：实体类，表示评估模板。 
 * - `com.cyberpeace.cr.evaluation.service.context.EvalTemplateListQuery`：查询条件对象。 
 * ### 接口定义 
 * - **注解**： 
 * - `@Mapper`：表明这是一个 MyBatis 的 Mapper。 
 * - `@Repository`：表明这是一个 Spring 的数据访问层组件。 
 * - **泛型**：`EvalTemplateMapper` 继承自 `BaseMapper<EvalTemplate>`，表示该接口处理的是 `EvalTemplate` 类型的实体。 
 * ### 方法定义 
 * 1. **`listEvalTemplates` 方法** 
 * - **描述**：列出评估模板。 
 * - **参数**： 
 * - `page`：分页对象。 
 * - `evalTemplateListQuery`：查询条件对象。 
 * - `userId`：用户ID。 
 * - **返回值**：`IPage<EvalTemplate>`，即分页后的评估模板列表。 
 * 2. **`existByNameAndUserIdAndId` 方法** 
 * - **描述**：校验模板名称是否存在。 
 * - **参数**： 
 * - `templateName`：模板名称。 
 * - `userId`：用户ID。 
 * - `templateId`：模板ID。 
 * - **返回值**：`Boolean`，表示模板名称是否存在。 
 * ### 总结 
 * - **功能**：该接口主要用于管理和查询评估模板。 
 * - **技术栈**：使用了 MyBatis 和 MyBatis-Plus 进行数据库操作，并且结合了 Spring 的 `@Repository` 注解来管理数据访问层。 
 * - **特点**：通过继承 `BaseMapper`，简化了基本的 CRUD 操作，并提供了自定义的查询方法（如分页查询和模板名称校验）。 
 * 这段代码展示了如何通过 MyBatis 和 MyBatis-Plus 来实现一个简单的数据访问层，并且通过自定义方法增强了功能。 
**/
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

