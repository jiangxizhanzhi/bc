/**
 * ### 代码分析 
 * #### 1. **类的基本信息** 
 * - 类名：`SpaceActionFrameTargetType` 
 * - 包路径：`com.cyberpeace.space.dao.entity` 
 * - 继承关系：继承自 `SpaceBaseTable`。 
 * - 注解： 
 * - `@Data`：来自 Lombok，自动生成 getter、setter、toString、equals 和 hashCode 方法。 
 * - `@TableName("space_action_frame_target_type")`：MyBatis-Plus 注解，用于映射数据库表名。 
 * #### 2. **字段说明** 
 * - `keyWords`: 字符串类型，可能表示某种关键词或标识符。 
 * - `keyWordsPrimary`: 字符串类型，可能是主关键词或其他关键字段。 
 * #### 3. **方法说明** 
 * - `entity2Bo(Map<String, IdAndName> relatedMap)`: 
 * - 功能：将当前实体对象转换为 `SpaceActionFrameTargetTypeBo` 对象。 
 * - 实现方式：通过 `BeanUtils.copy` 方法实现对象的拷贝。 
 * - 输入参数： 
 * - `relatedMap`: 一个 `Map<String, IdAndName>` 类型的参数，可能用于关联数据处理。 
 * - 输出结果：返回一个 `SpaceActionFrameTargetTypeBo` 对象。 
 * #### 4. **依赖的其他类** 
 * - `com.baomidou.mybatisplus.annotation.TableName`: MyBatis-Plus 的注解，用于指定数据库表名。 
 * - `com.cyberpeace.common.util.BeanUtils`: 提供对象拷贝功能的工具类。 
 * - `com.cyberpeace.space.model.IdAndName`: 表示某种包含 ID 和名称的对象。 
 * - `com.cyberpeace.space.model.SpaceBaseTable`: 当前类的父类，可能是一个基础表类。 
 * - `com.cyberpeace.space.service.context.frame.SpaceActionFrameTargetTypeBo`: 表示业务逻辑层的目标类型对象。 
 * #### 5. **总结** 
 * - 该类是一个数据访问层（DAO）的实体类，用于表示“空间行动框架目标类型”的数据模型。 
 * - 它的主要功能是封装与数据库表 `space_action_frame_target_type` 相关的数据字段，并提供对象转换的方法。 
 * - 方法 `entity2Bo` 是核心功能之一，用于将实体对象转换为业务逻辑层所需的目标对象。 
**/
package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameTargetTypeBo;
import lombok.Data;

import java.util.Map;


/**
 * 空间行动框架目标类型
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBaseTable
 * @since 1.0.0
 */
@Data
@TableName("space_action_frame_target_type")
public class SpaceActionFrameTargetType extends SpaceBaseTable {

	private String keyWords;

	private String keyWordsPrimary;

	public SpaceActionFrameTargetTypeBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceActionFrameTargetTypeBo bo = BeanUtils.copy(this, SpaceActionFrameTargetTypeBo.class);
		return bo;
    }
}
    