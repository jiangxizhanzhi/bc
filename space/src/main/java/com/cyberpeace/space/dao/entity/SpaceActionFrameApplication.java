package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.frame.ActionFrameArrangeBo;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameApplicationBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * 空间行动框架行为依赖
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBaseTable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceActionFrameApplication` 的实体类，用于描述空间行动框架的行为依赖。以下是对代码的简要分析： 
 * 1. **类的基本信息**： 
 * - 类名：`SpaceActionFrameApplication` 
 * - 包名：`com.cyberpeace.space.dao.entity` 
 * - 注解：`@Data` 和 `@TableName("space_action_frame_application")` 分别表示该类自动生成 getter、setter 方法，并且与数据库表 `space_action_frame_application` 对应。 
 * 2. **继承关系**： 
 * - 该类继承了 `SpaceBaseTable`，表明它可能包含一些基础的表结构字段。 
 * 3. **字段说明**： 
 * - 各个字段通过 `@ApiModelProperty` 注解描述了其含义，例如 `rvbName` 表示红蓝对抗业务名称，`frameId` 表示框架ID等。 
 * - 特殊字段如 `@TableField(value = "`analyze`")` 使用反引号包裹，可能是为了处理特殊字符或保留关键字作为字段名。 
 * 4. **方法功能**： 
 * - `entity2Bo` 方法将当前实体对象转换为业务对象 `SpaceActionFrameApplicationBo`，并处理了某些字段的特殊格式化（如 `tag` 转为 JSON 数组）。 
 * - `getArrangeBo` 方法将字符串形式的框架数据反序列化为 `ActionFrameArrangeBo` 对象，如果反序列化失败，则创建一个新的实例并设置资源ID。 
 * 5. **依赖库**： 
 * - 使用了 MyBatis-Plus 的注解 (`@TableName`, `@TableField`) 来映射数据库表和字段。 
 * - 利用了 `BeanUtils` 和 `JsonUtil` 工具类来进行对象复制和 JSON 操作。 
 * 6. **设计意图**： 
 * - 这是一个典型的持久层实体类，旨在封装数据库中的 `space_action_frame_application` 表的数据结构及其业务逻辑。 
 * - 提供了从实体到业务对象的转换机制，便于在不同层次之间传递数据。 
 * 总体来说，这段代码实现了数据模型的设计与部分业务逻辑的封装，适合于需要频繁操作此表的应用场景。 
**/
@Data
@TableName("space_action_frame_application")
public class SpaceActionFrameApplication extends SpaceBaseTable {

	@ApiModelProperty(value = "红蓝对抗业务名称")
	private String rvbName;

	@ApiModelProperty(value = "红蓝对抗业务id")
	private String rvbId;

	@ApiModelProperty(value = "框架id")
	private String frameId;

	@ApiModelProperty(value = "框架名称")
	private String name;

	@ApiModelProperty(value = "分类，面向流程，面向目标")
	private Integer type;

	@ApiModelProperty(value = "类型id")
	private String categoryId;

	@ApiModelProperty(value = "应用场景")
	private String tag;

	@ApiModelProperty(value = "使用说明")
	private String description;

	@TableField(value = "`analyze`")
	private String analyze;

	@ApiModelProperty(value = "框架编排")
	private String frame;

	public SpaceActionFrameApplicationBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceActionFrameApplicationBo bo = BeanUtils.copy(this,
				SpaceActionFrameApplicationBo.class);
		bo.setTag(JsonUtil.toJsonObjectArray(this.getTag(), String.class));
		bo.setFrame(this.getArrangeBo());
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
}

