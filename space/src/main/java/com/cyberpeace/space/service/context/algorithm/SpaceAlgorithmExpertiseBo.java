/**
 * 这段代码定义了一个名为 `SpaceAlgorithmExpertiseBo` 的 Java 类，用于表示“空间算法专门知识”的业务对象（BO）。以下是代码的简要分析： 
 * ### 功能概述 
 * - **类的作用**：该类继承自 `SpaceBoBase`，是用于描述空间算法专门知识的数据传输对象（DTO），并提供了一些与实体对象交互的功能。 
 * - **主要字段**： 
 * - `resourceId` 和 `name`：分别表示资源 ID 和名称。 
 * - `course` 和 `training`：分别表示课程和训练的列表，每个列表中的元素是 `IdAndName` 对象。 
 * - `coursePercent` 和 `trainingPercent`：分别表示课程和训练的百分比。 
 * ### 特性 
 * - **注解支持**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为 API 文档生成提供字段描述。 
 * - **方法**： 
 * - `bo2Entity()`：将当前 BO 对象转换为对应的实体对象 `SpaceAlgorithmExpertise`，并设置相关属性。具体逻辑包括： 
 * - 使用工具类 `BeanUtils.copy` 进行属性复制。 
 * - 将 `course` 列表的 ID 提取为字符串数组，并设置到实体对象中。 
 * - 将 `training` 列表序列化为 JSON 字符串。 
 * ### 总结 
 * 该类是一个典型的 DTO 类，主要用于在业务层和数据库层之间传递数据。它通过继承基类和使用工具类简化了对象之间的转换逻辑，同时借助注解提升了代码的可读性和开发效率。 
**/

package com.cyberpeace.space.service.context.algorithm;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceAlgorithmExpertise;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 空间算法专门知识Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceAlgorithmExpertiseBo extends SpaceBoBase {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "资源id")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 课程
	 */
	@ApiModelProperty(value = "课程列表")
	private List<IdAndName> course;
	/**
	 * 百分比
	 */
	@ApiModelProperty(value = "课程占比")
	private Integer coursePercent;
	/**
	 * 训练
	 */
	@ApiModelProperty(value = "训练列表")
	private List<IdAndName> training;

	/**
	 * 训练百分之
	 */
	@ApiModelProperty(value = "训练占比")
	private Integer trainingPercent;

	public SpaceAlgorithmExpertise bo2Entity() {
		SpaceAlgorithmExpertise entity = BeanUtils.copy(this, SpaceAlgorithmExpertise.class);
		entity.setCourse(this.getIds(this.course));
//		entity.setTraining(this.getIds(this.training));
		entity.setTraining(JsonUtil.arrayToString(this.training));
		return entity;
	}

}
    