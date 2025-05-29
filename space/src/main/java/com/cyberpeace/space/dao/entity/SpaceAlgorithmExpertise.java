package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.algorithm.SpaceAlgorithmExpertiseBo;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 空间算法专业知识
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBaseTable
 * @since 1.0.0
 */
/**
 * ### 分析与总结 
 * #### 代码功能概述 
 * 该代码定义了一个名为 `SpaceAlgorithmExpertise` 的实体类，用于表示空间算法相关的专业知识。它继承自 `SpaceBaseTable`，并使用 MyBatis-Plus 的 `@TableName` 注解映射到数据库表 `space_algorithm_expertise`。 
 * #### 主要功能模块 
 * 1. **字段定义**： 
 * - 定义了多个字段（如 `course`、`coursePercent`、`training`、`trainingPercent`），分别表示课程名称、课程百分比、训练内容以及训练百分比。 
 * - 字段通过注释描述其含义。 
 * 2. **继承与注解**： 
 * - 继承自 `SpaceBaseTable`，可能包含一些通用的基础属性或方法。 
 * - 使用 Lombok 的 `@Getter` 和 `@Setter` 提供自动化的 getter 和 setter 方法。 
 * - 使用 `@TableName` 映射数据库表名。 
 * 3. **业务逻辑方法**： 
 * - 提供了一个 `entity2Bo` 方法，将当前实体对象转换为 `SpaceAlgorithmExpertiseBo` 对象。 
 * - 该方法利用工具类 `BeanUtils.copy` 进行对象复制，并通过 `getListByJson` 或 `JsonUtil.toJsonObjectArray` 将 JSON 格式的字符串（如 `course` 和 `training`）解析为具体的对象列表。 
 * 4. **依赖的工具类**： 
 * - 使用了 `com.cyberpeace.common.util.BeanUtils` 和 `com.cyberpeace.common.util.JsonUtil`，分别用于对象复制和 JSON 解析。 
 * #### 总结 
 * 该类是一个典型的实体类，主要用于数据持久化和业务逻辑处理。它的核心功能是将数据库中的数据映射为 Java 对象，并提供方法将实体对象转换为业务逻辑对象（BO）。代码结构清晰，依赖工具类合理，适合作为服务层的数据传输模型使用。 
**/
@Getter
@Setter
@TableName("space_algorithm_expertise")
public class SpaceAlgorithmExpertise extends SpaceBaseTable {

	/**
	 * 课程
	 */
	private String course;

	/**
	 * 百分比
	 */
	private Integer coursePercent;

	/**
	 * 训练
	 */
	private String training;

	/**
	 * 训练百分之
	 */
	private Integer trainingPercent;

	public SpaceAlgorithmExpertiseBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceAlgorithmExpertiseBo bo = BeanUtils.copy(this, SpaceAlgorithmExpertiseBo.class);
		bo.setCourse(this.getListByJson(relatedMap, this.course));
//		bo.setTraining(this.getListByJson(relatedMap, this.training));
		bo.setTraining(JsonUtil.toJsonObjectArray(this.training, IdAndName.class));
		return bo;
	}
}

