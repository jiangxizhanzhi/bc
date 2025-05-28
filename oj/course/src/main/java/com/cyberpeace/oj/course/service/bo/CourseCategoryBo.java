/**
 * 这段代码定义了一个名为 `CourseCategoryBo` 的 Java 类，该类实现了 `Serializable` 接口。这个类主要用于表示课程类别，并包含了一些属性和注解来描述这些属性。以下是对代码的简要分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.oj.course.service.bo` 
 * - **类名**：`CourseCategoryBo` 
 * - **实现接口**：`Serializable` 
 * ### 类注释 
 * - 作者：SN 
 * - 版本：1.0.0 
 * - 创建日期：2024年11月8日 
 * ### 属性 
 * 1. **children** 
 * - 类型：`List<CourseCategoryBo>` 
 * - 注释：子级类别 
 * - 注解： 
 * - `@ApiModelProperty("子信息")`：用于 Swagger 文档生成，描述属性为“子信息” 
 * 2. **parentId** 
 * - 类型：`String` 
 * - 注释：父类 ID 
 * - 注解： 
 * - `@JsonIgnore`：忽略该属性，不进行序列化 
 * 3. **resourceId** 
 * - 类型：`String` 
 * - 注释：资源 ID 
 * - 注解： 
 * - `@ApiModelProperty("资源编号")`：用于 Swagger 文档生成，描述属性为“资源编号” 
 * 4. **name** 
 * - 类型：`String` 
 * - 注释：类别名称 
 * - 注解： 
 * - `@ApiModelProperty("分类名称")`：用于 Swagger 文档生成，描述属性为“分类名称” 
 * 5. **courseType** 
 * - 类型：`Integer` 
 * - 注释：课程类型（基础、攻击、防御） 
 * - 注解： 
 * - `@ApiModelProperty("课程类型 0=基础 2=攻击 3=防御")`：用于 Swagger 文档生成，描述属性为“课程类型 0=基础 2=攻击 3=防御” 
 * 6. **builtin** 
 * - 类型：`Boolean` 
 * - 注释：是否为内置类别 
 * ### Getter 和 Setter 
 * - 类使用了 Lombok 注解 `@Getter` 和 `@Setter` 自动生成了所有属性的 getter 和 setter 方法。 
 * ### 总结 
 * `CourseCategoryBo` 类主要用于表示课程类别，包含了类别名称、父类 ID、资源 ID、子类别列表以及课程类型等属性。通过 Swagger 注解，可以方便地生成 API 文档。此外，`@JsonIgnore` 注解用于忽略 `parentId` 属性的序列化。 
**/
package com.cyberpeace.oj.course.service.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 课程类别 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class CourseCategoryBo implements Serializable {

	/**
	 * 子级
	 *
	 * @see List<CourseCategoryBo>
	 */
	@ApiModelProperty("子信息")
	private List<CourseCategoryBo> children;

	/**
	 * 父母id
	 *
	 * @see String
	 */
	@JsonIgnore
	private String parentId;

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("分类名称")
	private String name;

	/**
	 * 课程类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程类型 0=基础 2=攻击 3=防御")
	private Integer courseType;

	private Boolean builtin;

}
