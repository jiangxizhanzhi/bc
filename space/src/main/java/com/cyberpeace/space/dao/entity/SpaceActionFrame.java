package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceFrameBaseTable;
import com.cyberpeace.space.service.context.frame.ActionFrameArrangeBo;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameBo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * 空间行动框架
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceFrameBaseTable
 * @since 1.0.0
 */
/**
 * 该代码定义了一个名为 `SpaceActionFrame` 的实体类，用于表示与空间行动框架相关的数据模型。以下是代码的主要功能和结构分析： 
 * 1. **类定义**： 
 * - 类 `SpaceActionFrame` 继承自 `SpaceFrameBaseTable`。 
 * - 使用了 Lombok 注解 `@Getter` 和 `@Setter` 来简化属性的 getter 和 setter 方法的生成。 
 * 2. **注解**： 
 * - `@TableName("space_action_frame")`：表明该类对应的数据库表名为 `space_action_frame`。 
 * - `@TableField(value = "`analyze`")`：指定字段 `analyze` 的数据库列名为 `analyze`（需要使用反引号包裹）。 
 * 3. **属性**： 
 * - 定义了一系列属性（如 `type`, `role`, `attack`, `defense` 等），这些属性分别对应数据库中的字段。 
 * 4. **方法**： 
 * - `entity2Bo(Map<String, List<IdAndName>> relatedMap)`：将当前实体对象转换为 `SpaceActionFrameBo` 对象，并处理 `tag` 字段的 JSON 转换。 
 * - `getArrangeBo()`：将 `frame` 字段解析为 `ActionFrameArrangeBo` 对象，如果解析失败则创建一个新的实例并设置资源 ID。 
 * - `Type` 枚举类：定义了 `SpaceActionFrame` 中的 `type` 属性可能的取值，实现了 `GenericEnum` 接口。 
 * 5. **依赖**： 
 * - 使用了多个工具类（如 `BeanUtils`, `JsonUtil`）来辅助对象转换和 JSON 操作。 
 * - 引用了其他模型类（如 `SpaceActionFrameBo`, `ActionFrameArrangeBo`, `IdAndName`）以及服务接口（如 `SpaceActionFrameBo`）。 
 * 6. **用途**： 
 * - 该类主要用于表示空间行动框架的数据模型，并提供了从实体到业务对象的转换逻辑。 
 * 总体而言，这是一个典型的基于 MyBatis-Plus 的数据实体类，结合了枚举类型和 JSON 处理，适用于复杂业务场景下的数据操作和传输。 
**/
@Getter
@Setter
@TableName("space_action_frame")
public class SpaceActionFrame extends SpaceFrameBaseTable {

	private Integer type;

	private Integer role;

	private String attack;

	private String defense;

	private String tag;

	private String topo;

	private String description;

	private String frame;

	private String categoryId;

	private String keyWords;

	@TableField(value = "`analyze`")
	private String analyze;

	private Boolean builtin;

	public SpaceActionFrameBo entity2Bo(Map<String, List<IdAndName>> relatedMap) {
		SpaceActionFrameBo bo = BeanUtils.copy(this, SpaceActionFrameBo.class);
		bo.setTag(JsonUtil.toJsonObjectArray(this.tag, String.class));
		return bo;
	}

	public ActionFrameArrangeBo getArrangeBo() {
		ActionFrameArrangeBo bo = JsonUtil.toJsonObject(this.frame, ActionFrameArrangeBo.class);
		if (null == bo) {
			bo = new ActionFrameArrangeBo();
			bo.setResourceId(this.getResourceId());
		}
		return bo;
	}

	public enum Type implements GenericEnum {

		PROCESS(0, "面向流程", "process"), TARGET(1, "面向目标", "target");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		@Getter
		private String name;

		Type(Integer code, String desc, String name) {
			this.code = code;
			this.desc = desc;
			this.name = name;
		}
	}
}
