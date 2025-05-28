/**
 * 这段代码定义了一个名为 `BaseTable` 的 Java 类，它是 `com.cyberpeace.common.model` 包中的一个基类。以下是代码的简要分析： 
 * 1. **继承与接口**： 
 * - `BaseTable` 实现了 `Serializable` 接口，这意味着它可以被序列化，支持在不同介质间传递或持久化存储。 
 * 2. **依赖的库**： 
 * - 使用了 `lombok` 的 `@Data` 注解，它自动生成了类的 getter、setter、toString、equals 和 hashCode 方法。 
 * - 引入了 `com.baomidou.mybatisplus` 提供的注解 `@TableField`，用于数据库字段的映射和自动填充规则。 
 * - 使用了 `io.swagger.annotations.ApiModelProperty` 来为字段提供 API 文档描述。 
 * 3. **字段说明**： 
 * - `id`：表示记录的唯一标识符，类型为 `Integer`，并有 Swagger 文档注释说明其用途。 
 * - `createTime`：记录创建时间，使用 `@TableField(fill = FieldFill.INSERT)` 注解，表示该字段会在插入数据时自动填充当前时间。 
 * - `updateTime`：记录最后更新时间，使用 `@TableField(fill = FieldFill.INSERT_UPDATE)` 注解，表示该字段会在插入或更新数据时自动填充当前时间。 
 * 4. **功能**： 
 * - 这是一个通用的基础类，适用于需要记录创建时间和更新时间的实体类，减少了重复代码的编写。 
 * 总结：`BaseTable` 是一个基础模型类，提供了基本的字段（如 ID、创建时间和更新时间）及其自动填充逻辑，便于快速构建其他业务实体类。 
**/
package com.cyberpeace.common.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 基表
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class BaseTable implements Serializable {

	/**
	 * id
	 */
	@ApiModelProperty("自增ID")
	private Integer id;

	/**
	 * 创建时间
	 */
	@ApiModelProperty("创建时间")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	/**
	 * 更新时间
	 */
	@ApiModelProperty("更新时间")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;
}
