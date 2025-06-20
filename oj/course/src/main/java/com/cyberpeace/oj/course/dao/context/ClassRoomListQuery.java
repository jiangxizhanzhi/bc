package com.cyberpeace.oj.course.dao.context;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 教室列表查询
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `ClassRoomListQuery` 的 Java 类，用于表示教室列表查询条件。以下是对该类的简要分析： 
 * ### 类概述 
 * - **包名**: `com.cyberpeace.oj.course.dao.context` 
 * - **类名**: `ClassRoomListQuery` 
 * - **功能**: 用于封装查询教室列表时所需的参数。 
 * ### 类属性 
 * - **startTime** (Date): 查询的开始时间。 
 * - **endTime** (Date): 查询的结束时间。 
 * - **orgId** (String): 组织ID。 
 * - **userId** (String): 用户ID。 
 * - **childrenOrg** (List<String>): 子级组织列表。 
 * - **trainPlanId** (String): 训练计划ID。 
 * ### 特性 
 * - 使用了 Lombok 注解 `@Getter` 和 `@Setter` 来自动生成 getter 和 setter 方法。 
 * - 实现了 `Serializable` 接口，表明该类的对象可以被序列化。 
 * ### 总结 
 * `ClassRoomListQuery` 类是一个数据传输对象（DTO），用于在查询教室列表时传递各种查询条件。通过该类，可以方便地管理和传递多个查询参数。 
**/
@Getter
@Setter
public class ClassRoomListQuery implements Serializable {
		
	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 开始时间
	 *
	 * @see Date
	 */
	private Date startTime;

	/**
	 * 结束时间
	 *
	 * @see Date
	 */
	private Date endTime;

	/**
	 * 组织id
	 *
	 * @see String
	 */
	private String orgId;

	/**
	 * 用户id
	 *
	 * @see String
	 */
	private String userId;

	/**
	 * 子级组织
	 *
	 * @see List<String>
	 */
	private List<String> childrenOrg;

	/**
	 * 训练计划id
	 *
	 * @see String
	 */
	private String trainPlanId;

}

