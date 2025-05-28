/**
 * 这段代码定义了一个名为 `ClassRoomPageQuery` 的 Java 类，该类继承自 `PageReqBase`。这个类主要用于房间分页查询，并包含了一些与用户、训练计划和组织相关的属性。以下是详细的分析： 
 * ### 类定义 
 * - **包名**: `com.cyberpeace.oj.course.dao.context` 
 * - **类名**: `ClassRoomPageQuery` 
 * - **继承**: 从 `PageReqBase` 继承 
 * ### 注解 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动为所有字段生成 getter 和 setter 方法。 
 * ### 字段 
 * 1. **userId** 
 * - 类型: `String` 
 * - 用途: 用户ID 
 * - 备注: 可以通过 `@see String` 进行引用 
 * 2. **trainPlanId** 
 * - 类型: `String` 
 * - 用途: 训练计划ID 
 * - 备注: 可以通过 `@see String` 进行引用 
 * 3. **organizationId** 
 * - 类型: `String` 
 * - 用途: 组织ID 
 * - 备注: 可以通过 `@see String` 进行引用 
 * 4. **childrenOrg** 
 * - 类型: `List<String>` 
 * - 用途: 子级组织列表 
 * - 备注: 可以通过 `@see List<String>` 进行引用 
 * ### 构造器和方法 
 * - 由于使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，该类没有显式的构造器和 getter/setter 方法。 
 * ### 总结 
 * - **功能**: `ClassRoomPageQuery` 是一个用于房间分页查询的数据传输对象（DTO），主要包含了用户ID、训练计划ID、组织ID以及子级组织列表等信息。 
 * - **继承**: 继承自 `PageReqBase` 类，可能包含一些通用的分页查询参数。 
 * - **注解**: 使用 Lombok 简化了 getter 和 setter 方法的编写。 
 * 这个类的设计是为了方便进行房间分页查询时传递相关参数，同时也利用了 Lombok 提供的功能来减少样板代码。 
**/
package com.cyberpeace.oj.course.dao.context;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cyberpeace.common.model.PageReqBase;
import lombok.Getter;
import lombok.Setter;

/**
 * 类房间分页查询
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PageReqBase
 * @since 1.0.0
 */
@Getter
@Setter
public class ClassRoomPageQuery extends PageReqBase {


	/**
	 * 用户id
	 *
	 * @see String
	 */
	private String userId;

	/**
	 * 训练计划id
	 *
	 * @see String
	 */
	private String trainPlanId;

	/**
	 * 组织id
	 *
	 * @see String
	 */
	private String organizationId;

	/**
	 * 子级组织
	 *
	 * @see List<String>
	 */
	private List<String> childrenOrg;

}
