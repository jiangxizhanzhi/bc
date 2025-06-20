package com.cyberpeace.integratation.service.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 日历竞赛 bo
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `CalendarCompetitionBo` 的 Java 类，该类用于表示日历竞赛的业务对象（BO, Business Object）。以下是对该代码的简要分析： 
 * ### 包声明 
 * - `package com.cyberpeace.integratation.service.bo;` 
 * - 这行代码声明了该类所在的包为 `com.cyberpeace.integratation.service.bo`。 
 * ### 导入语句 
 * - `import lombok.Getter;` 
 * - `import lombok.Setter;` 
 * - 这两行导入了 Lombok 库中的 `Getter` 和 `Setter` 注解，用于自动生成 getter 和 setter 方法。 
 * - `import java.io.Serializable;` 
 * - 导入了 `Serializable` 接口，表明该类实现了序列化接口，可以在需要时将对象转换为字节流进行存储或传输。 
 * - `import java.util.Date;` 
 * - 导入了 `Date` 类，用于处理日期相关的字段。 
 * ### 类声明 
 * - `@Getter` 
 * - `@Setter` 
 * - 使用 Lombok 提供的注解自动生成 getter 和 setter 方法。 
 * - `public class CalendarCompetitionBo implements Serializable {` 
 * - 定义了一个公共类 `CalendarCompetitionBo`，并实现 `Serializable` 接口。 
 * ### 类成员变量 
 * - `private String resourceId;` 
 * - 赛事编号，用于唯一标识一个赛事。 
 * - `private String createUserId;` 
 * - 赛事创建人的用户ID。 
 * - `private String name;` 
 * - 赛事的名称。 
 * - `private Integer status;` 
 * - 赛事的状态，可能包括不同的状态值如“进行中”、“已结束”等。 
 * - `private Integer type;` 
 * - 赛事的类型，例如“线上比赛”、“线下比赛”等。 
 * - `private Date startTime;` 
 * - 赛事的开始时间。 
 * - `private Date endTime;` 
 * - 赛事的结束时间。 
 * - `private Integer classify;` 
 * - 赛事的分类，可能用于进一步细分赛事类别。 
 * ### 总结 
 * - **用途**：`CalendarCompetitionBo` 类是一个业务对象，用于封装与日历竞赛相关的数据信息。 
 * - **功能**：通过定义各种属性来描述一个赛事的基本信息，如赛事编号、名称、状态、类型等，并提供了自动生成的 getter 和 setter 方法。 
 * - **可序列化**：实现了 `Serializable` 接口，便于对象在不同场景下的存储和传输。 
**/
@Getter
@Setter
public class CalendarCompetitionBo implements Serializable {
		
	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 赛事编号
	 */
	private String resourceId;

	/**
	 * 赛事创建人
	 */
	private String createUserId;

	/**
	 * 赛事名称
	 */
	private String name;

	/**
	 * 赛事状态
	 */
	private Integer status;

	/**
	 * 赛事类型
	 */
	private Integer type;

	/**
	 * 开始时间
	 */
	private Date startTime;

	/**
	 * 结束时间
	 */
	private Date endTime;

	/**
	 * 比赛分类
	 */
	private Integer classify;

}

