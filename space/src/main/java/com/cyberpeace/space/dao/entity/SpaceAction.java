/**
 * 这段代码定义了一个名为 `SpaceAction` 的实体类，用于表示与数据库表 `space_action` 对应的数据模型。以下是代码的主要分析和总结： 
 * --- 
 * ### **1. 类的基本信息** 
 * - **包名**: `com.cyberpeace.space.dao.entity` 
 * - **继承关系**: 继承自 `SpaceFrameBaseTable`（父类未展示，但可以推测是一个通用的基类）。 
 * - **注解**: 
 * - `@Getter` 和 `@Setter`: 自动生成字段的 getter 和 setter 方法。 
 * - `@TableName("space_action")`: 指定该类对应的数据库表名为 `space_action`。 
 * --- 
 * ### **2. 字段说明** 
 * - `type`: 动作类型，整型。 
 * - `description`: 动作描述，字符串。 
 * - `idNumber`: 动作的唯一标识号，字符串。 
 * - `parentNumber`: 父级动作的标识号，字符串。 
 * - `platform`: 动作所涉及的平台，字符串。 
 * - `environment`: 动作运行的环境，字符串。 
 * - `action`: 具体的动作名称，字符串。 
 * - `analyze`: 分析内容，字符串（使用了 `@TableField("`analyze`")` 注解，避免关键字冲突）。 
 * - `tool`: 工具名称，字符串。 
 * - `offlineTool`: 离线工具信息，字符串。 
 * - `strategy`: 策略信息，字符串。 
 * --- 
 * ### **3. 方法说明** 
 * - **`entity2Bo` 方法**: 
 * - 参数: `Map<String, List<IdAndName>> relatedMap`，表示与当前实体相关的映射数据。 
 * - 功能: 
 * 1. 使用 `BeanUtils.copy` 将当前实体 (`this`) 转换为 `SpaceActionBo` 对象。 
 * 2. 设置 `method` 属性，通过 `relatedMap` 获取对应资源 ID 的方法列表。 
 * 3. 将 `platform` 字符串转换为 JSON 对象数组。 
 * 4. 将 `offlineTool` 字符串转换为 `SpaceActionOfflineTool` 对象数组。 
 * - 返回值: 转换后的 `SpaceActionBo` 对象。 
 * --- 
 * ### **4. 关联类说明** 
 * - **`SpaceActionBo`**: 表示业务对象（BO），用于封装业务逻辑所需的数据。 
 * - **`SpaceFrameBaseTable`**: 基础表类，可能包含通用的字段或方法。 
 * - **`IdAndName`**: 表示带有 ID 和名称的对象。 
 * - **`JsonUtil`**: 提供 JSON 数据处理工具。 
 * - **`SpaceActionOfflineTool`**: 离线工具类，用于解析 `offlineTool` 字段。 
 * --- 
 * ### **5. 总结** 
 * - 该类是一个典型的持久化实体类，主要用于数据库操作。 
 * - 它实现了从实体到业务对象的转换（`entity2Bo` 方法），便于业务层使用。 
 * - 使用了 Lombok 注解简化代码，同时通过 MyBatis-Plus 的注解与数据库表进行映射。 
 * - 需要注意的是，`analyze` 字段使用反引号包裹，避免与 SQL 关键字冲突。 
 * 如果需要进一步优化或扩展功能，可以根据具体需求调整字段、方法或关联类的设计。 
**/
package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceFrameBaseTable;
import com.cyberpeace.space.service.context.frame.SpaceActionBo;
import com.cyberpeace.space.service.context.frame.SpaceActionOfflineTool;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * 空间作
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceFrameBaseTable
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("space_action")
public class SpaceAction extends SpaceFrameBaseTable {

	private Integer type;

	private String description;

	private String idNumber;

	private String parentNumber;

	private String platform;

	private String environment;

	private String action;

	@TableField("`analyze`")
	private String analyze;

	private String tool;

	private String offlineTool;

	private String strategy;

	public SpaceActionBo entity2Bo(Map<String, List<IdAndName>> relatedMap) {
		SpaceActionBo bo = BeanUtils.copy(this, SpaceActionBo.class);
		bo.setMethod(relatedMap.get(bo.getResourceId()));
		bo.setPlatform(JsonUtil.toJsonObjectArray(this.platform, String.class));
		bo.setOfflineTool(
				JsonUtil.toJsonObjectArray(this.offlineTool, SpaceActionOfflineTool.class));
		return bo;
	}
}