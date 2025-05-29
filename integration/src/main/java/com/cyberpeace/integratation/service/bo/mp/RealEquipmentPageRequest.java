package com.cyberpeace.integratation.service.bo.mp;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * 这段代码定义了一个名为 `RealEquipmentPageRequest` 的 Java 类，该类用于表示查询真实设备分页请求的参数。以下是对该代码的简要分析： 
 * 1. **包声明**： 
 * - 代码位于 `com.cyberpeace.integratation.service.bo.mp` 包中。 
 * 2. **依赖引入**： 
 * - 引入了 Lombok 库中的 `@Getter`, `@Setter`, `@NoArgsConstructor`, 和 `@AllArgsConstructor` 注解。 
 * - 引入了 Swagger 注解 `@ApiModelProperty`，用于生成 API 文档。 
 * 3. **类定义**： 
 * - 类名：`RealEquipmentPageRequest` 
 * - 访问修饰符：默认（包私有） 
 * - 继承：无 
 * - 实现：无 
 * 4. **Lombok 注解**： 
 * - `@Getter` 和 `@Setter`：自动生成所有字段的 getter 和 setter 方法。 
 * - `@NoArgsConstructor`：自动生成无参构造函数。 
 * - `@AllArgsConstructor`：自动生成包含所有字段的全参构造函数。 
 * 5. **字段及注释**： 
 * - `category` (Integer)：系统主类型，用于表示一级类型。 
 * - `second_category` (Integer)：系统子类型，用于表示二级类型。 
 * - `thirdCategory` (Integer)：系统子类型，用于表示三级类型。 
 * - `name` (String)：设备名称。 
 * - `inPosition` (Integer)：在位状态。 
 * - `useStatus` (Integer)：使用状态。 
 * - `exactName` (String)：精确查找名称。 
 * - `page` (Integer)：分页请求中的当前页码。 
 * - `per_page` (Integer)：每页显示的记录数。 
 * 6. **功能**： 
 * - 这个类主要用于封装分页查询设备时所需的参数，包括设备的分类、状态、名称等信息。 
 * - 通过 Swagger 注解，这些字段的信息会被自动添加到生成的 API 文档中，方便前端或客户端进行调用。 
 * 7. **优点**： 
 * - 使用 Lombok 减少了样板代码的编写。 
 * - 字段注释清晰明了，有助于理解字段的用途。 
 * - Swagger 注解使 API 文档生成更加便捷。 
 * 8. **潜在改进**： 
 * - 可以考虑为某些字段添加更多的约束（如非空检查）。 
 * - 如果需要更复杂的逻辑，可以考虑将一些字段的校验逻辑移到对应的 setter 方法中。 
 * - 可以考虑使用枚举类型来替代整型字段，提高代码的可读性和健壮性。 
 * 总的来说，这个类的设计简洁明了，符合现代 Java 开发的最佳实践，能够很好地满足分页查询设备的需求。 
**/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RealEquipmentPageRequest {

	/**
	 * 系统主类型
	 */
	@ApiModelProperty(value = "一级类型")
	private Integer category;

	/**
	 * 系统子类型
	 */
	@ApiModelProperty(value = "二级类型")
	private Integer second_category;

	/**
	 * 系统子类型
	 */
	@ApiModelProperty(value = "三级类型")
	private Integer thirdCategory;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 在位状态
	 */
	@ApiModelProperty(value = "在位状态")
	private Integer inPosition;

	/**
	 * 使用状态
	 */
	@ApiModelProperty(value = "使用状态")
	private Integer useStatus;

	/**
	 * 精确查找名称
	 */
	@ApiModelProperty(value = "精确查找名称")
	private String exactName;

	private Integer page;

	private Integer per_page;
}

