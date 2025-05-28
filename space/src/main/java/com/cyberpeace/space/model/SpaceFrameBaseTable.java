/**
 * 这段代码定义了一个抽象类 `SpaceFrameBaseTable`，它是空间框架的基础表格类。以下是对代码的简要分析： 
 * 1. **类的基本信息**： 
 * - 类名为 `SpaceFrameBaseTable`，位于包 `com.cyberpeace.space.model` 中。 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解来自动生成 getter 和 setter 方法。 
 * 2. **字段说明**： 
 * - `id`: 私有整型变量，使用了 `@JsonIgnore` 注解，表示该字段在序列化时会被忽略。 
 * - `createTime`: 公共日期类型变量，用于记录创建时间，使用了 `@ApiModelProperty` 和 `@TableField` 注解，表示该字段会在 API 文档中显示，并且在插入数据时自动填充。 
 * - `updateTime`: 公共日期类型变量，用于记录更新时间，同样使用了 `@ApiModelProperty` 和 `@TableField` 注解，表示该字段会在插入或更新数据时自动填充。 
 * - `resourceId`: 唯一标识符，使用了 `@TableId` 注解，指定其生成策略为 UUID。 
 * - `name`: 公共字符串变量，用于存储名称。 
 * 3. **方法说明**： 
 * - `entity2Bo`: 抽象方法，用于将实体对象转换为业务对象，具体实现由子类提供。 
 * - `getIdAndNameList`: 提供两种重载方法，根据不同的参数列表返回对应的 `IdAndName` 列表。第一种方法接受一个 JSON 格式的 ID 列表并将其转换为字符串列表后调用第二种方法；第二种方法遍历给定的 ID 列表，从 `relatedMap` 中获取对应的 `IdAndName` 对象并添加到结果列表中。 
 * - `getIdAndName`: 根据给定的 ID 从 `relatedMap` 中获取对应的 `IdAndName` 对象。 
 * 4. **依赖与工具**： 
 * - 使用了 `com.baomidou.mybatisplus` 提供的注解来处理数据库字段的自动填充等功能。 
 * - 使用了 `com.fasterxml.jackson` 的 `@JsonIgnore` 注解来控制 JSON 序列化行为。 
 * - 使用了 `io.swagger.annotations.ApiModelProperty` 来生成 API 文档中的描述信息。 
 * - 使用了 `org.apache.commons.compress.utils.Lists` 来创建空的列表。 
 * 总体而言，这个类是一个通用的基础类，旨在提供一些常用的功能如自动填充时间戳、生成唯一标识符等，并且通过抽象方法和实用方法提供了灵活的操作接口。 
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
 * 空间框架基础桌子
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public abstract class SpaceFrameBaseTable {

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
	private String resourceId;

	private String name;

	public abstract SpaceFrameBoBase entity2Bo(Map<String, List<IdAndName>> relatedMap);

	public List<IdAndName> getIdAndNameList(Map<String, IdAndName> relatedMap, String idListJson){
		List<String> ids = JsonUtil.toJsonObjectArray(idListJson, String.class);
		return getIdAndNameList(relatedMap, ids);
	}

	public List<IdAndName> getIdAndNameList(Map<String, IdAndName> relatedMap, List<String> ids){
		List<IdAndName> res = Lists.newArrayList();
		for (String id : ids) {
			if(relatedMap.containsKey(id)){
				res.add(relatedMap.get(id));
			}
		}
		return res;
	}
	public IdAndName getIdAndName(Map<String, IdAndName> relatedMap, String id){
		return relatedMap.get(id);
	}
}
