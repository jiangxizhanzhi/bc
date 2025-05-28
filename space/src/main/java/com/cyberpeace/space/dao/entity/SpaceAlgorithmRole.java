/**
 * 这段代码定义了一个名为 `SpaceAlgorithmRole` 的 Java 类，用于表示“空间算法岗位”的实体类。以下是代码的主要分析： 
 * ### 1. **类的基本信息** 
 * - **包名**：`com.cyberpeace.space.dao.entity` 
 * - **继承关系**：该类继承自 `SpaceBaseTable`，表明它是一个数据表对应的实体类。 
 * - **注解**： 
 * - `@TableName("space_algorithm_role")`：指定该类对应的数据库表名为 `space_algorithm_role`。 
 * - `@Getter` 和 `@Setter`：通过 Lombok 自动生成 getter 和 setter 方法。 
 * ### 2. **字段说明** 
 * - `course`：表示课程名称，类型为 `String`。 
 * - `coursePercent`：表示课程的百分比，类型为 `Integer`。 
 * - `training`：表示训练内容，类型为 `String`。 
 * - `trainingPercent`：表示训练的百分比，类型为 `Integer`。 
 * ### 3. **方法说明** 
 * - `entity2Bo(Map<String, IdAndName> relatedMap)`：将当前实体对象转换为业务对象 `SpaceAlgorithmRoleBo`。 
 * - 使用 `BeanUtils.copy` 方法将当前对象的属性复制到目标对象中。 
 * - 调用 `getListByJson` 方法处理 `course` 和 `training` 字段，可能涉及 JSON 数据的解析或处理。 
 * ### 4. **依赖的其他类** 
 * - `IdAndName`、`SpaceBaseTable`、`SpaceAlgorithmRoleBo`：这些类或接口可能是项目的其他模块定义的，用于支持当前类的功能。 
 * - `BeanUtils`：一个工具类，可能提供对象之间的属性拷贝功能。 
 * - `Map<String, IdAndName>`：用于存储与相关 ID 和名称映射的数据结构。 
 * ### 总结 
 * 该类主要用于数据库表 `space_algorithm_role` 的实体映射，并提供了将实体对象转换为业务对象的方法。代码结构清晰，使用了 Lombok 注解简化了代码量，同时依赖外部工具类和接口来完成具体逻辑。 
**/
package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.algorithm.SpaceAlgorithmRoleBo;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;


/**
 * 空间算法岗位
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBaseTable
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("space_algorithm_role")
public class SpaceAlgorithmRole extends SpaceBaseTable {

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
	public SpaceAlgorithmRoleBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceAlgorithmRoleBo bo = BeanUtils.copy(this, SpaceAlgorithmRoleBo.class);
		bo.setCourse(this.getListByJson(relatedMap, this.course));
		bo.setTraining(this.getListByJson(relatedMap, this.training));
		return bo;
    }
}
    