/**
 * 这段代码定义了一个名为 `SpaceBaseTable` 的抽象类，是空间相关数据模型的基础类。以下是代码的主要功能和特点： 
 * 1. **注解与字段**： 
 * - 使用了 `@Getter` 和 `@Setter` 注解，为类中的所有字段提供自动的 getter 和 setter 方法。 
 * - `@ApiModelProperty` 注解用于描述字段的用途，便于接口文档生成。 
 * - `@JsonIgnore` 注解表明某些字段（如 `id`, `createTime`, `updateTime`）在序列化时应被忽略。 
 * - `@TableId(type = IdType.ASSIGN_UUID)` 指定 `resourceId` 字段使用 UUID 作为主键生成策略。 
 * 2. **字段说明**： 
 * - `id`: 自增 ID，用于唯一标识记录。 
 * - `createTime`: 记录创建时间。 
 * - `updateTime`: 记录最后更新时间。 
 * - `resourceId`: 资源 ID，使用 UUID 标识。 
 * - `name`: 名称。 
 * - `builtin`: 表示是否为内置项。 
 * 3. **方法**： 
 * - `entity2Bo`: 抽象方法，用于将实体对象转换为业务对象（`SpaceBoBase`），需要子类实现。 
 * - `getListByJson`: 根据 JSON 字符串和相关映射表（`relatedMap`），解析出对应的 `IdAndName` 对象列表。 
 * 4. **工具类与依赖**： 
 * - 使用了 `com.cyberpeace.common.util.JsonUtil` 来处理 JSON 数据。 
 * - `io.swagger.annotations.ApiModelProperty` 用于生成 API 文档。 
 * - `org.apache.commons.compress.utils.Lists` 提供了安全的列表初始化。 
 * 该类是一个基础模型类，适用于需要包含通用字段（如创建时间、更新时间等）的空间相关实体。通过抽象方法和工具方法的结合，提供了灵活的数据处理能力。 
**/
package com.cyberpeace.space.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.util.JsonUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.compress.utils.Lists;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Space 基桌
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public abstract class SpaceBaseTable {

    @ApiModelProperty("自增ID")
    @JsonIgnore
    private Integer id;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    @JsonIgnore
    private Date createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonIgnore
    private Date updateTime;

	@TableId(type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "资源id")
	private String resourceId;

    @ApiModelProperty(value = "名称")
	private String name;
	@ApiModelProperty(value = "内置")
	private Boolean builtin;
	public abstract SpaceBoBase entity2Bo(Map<String, IdAndName> relatedMap);

	public List<IdAndName> getListByJson(Map<String, IdAndName> relatedMap, String jsonStr) {
		List<IdAndName> res = Lists.newArrayList();
		if (jsonStr != null && !jsonStr.isEmpty()) {
			List<String> ids = JsonUtil.toJsonObjectArray(jsonStr, String.class);
			for (String id : ids) {
				if (relatedMap.containsKey(id)) {
					res.add(relatedMap.get(id));
				}
			}
		}
		return res;
	}
}
