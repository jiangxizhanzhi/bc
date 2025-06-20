package com.cyberpeace.oj.course.dao.context;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 特殊课程背景
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpecialCourseContext` 的 Java 类，用于表示特殊的课程背景信息。以下是对该类的简要分析： 
 * ### 类概述 
 * - **包名**：`com.cyberpeace.oj.course.dao.context` 
 * - **类名**：`SpecialCourseContext` 
 * - **实现接口**：`Serializable`（表明这个类的对象可以被序列化） 
 * ### 类成员变量 
 * - **myCourse**：布尔类型，表示是否为我的课程，默认值为 `true`。 
 * - **systemCourse**：布尔类型，表示是否为系统课程，默认值为 `true`。 
 * - **shareCourse**：布尔类型，表示是否为分享课程，默认值为 `true`。 
 * - **userId**：字符串类型，表示用户ID。 
 * ### 方法 
 * - **getters 和 setters**： 
 * - 每个布尔类型的成员变量都有一个对应的 getter 方法，并且在 getter 方法中检查成员变量是否为 `null`，如果是则返回 `true`。 
 * ### 总结 
 * - 这个类主要用于存储关于课程的一些布尔属性，如是否为我的课程、系统课程或分享课程。 
 * - 类中的所有布尔属性都有默认值 `true`，并且通过 getter 方法确保即使这些属性为 `null`，也会返回 `true`。 
 * - 由于实现了 `Serializable` 接口，这个类的对象可以在需要时进行序列化操作。 
 * ### 建议 
 * - 如果布尔属性的默认值为 `true`，可以考虑将它们初始化为 `true`，而不是在 getter 中处理 `null` 情况。 
 * - 可以增加一些文档注释来说明每个布尔属性的具体含义以及在什么情况下会为 `false`。 
 * - 如果有其他相关的信息需要存储，可以考虑添加更多的字段和方法。 
**/
@Getter
@Setter
public class SpecialCourseContext implements Serializable {
		
	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 我课程
	 *
	 * @see Boolean
	 */
	private Boolean myCourse;

	/**
	 * 系统课程
	 *
	 * @see Boolean
	 */
	private Boolean systemCourse;

	/**
	 * 分享课程
	 *
	 * @see Boolean
	 */
	private Boolean shareCourse;

	/**
	 * 用户id
	 *
	 * @see String
	 */
	private String userId;

	public Boolean getMyCourse() {
		return null == myCourse ? true : myCourse;
	}

	public Boolean getSystemCourse() {
		return null == systemCourse ? true : systemCourse;
	}

	public Boolean getShareCourse() {
		return null == shareCourse ? true : shareCourse;
	}
}

