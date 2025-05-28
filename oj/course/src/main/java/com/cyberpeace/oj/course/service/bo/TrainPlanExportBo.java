/**
 * 这段代码定义了一个名为 `TrainPlanExportBo` 的 Java 类，该类实现了 `Serializable` 接口。这个类的主要目的是用于表示与训练计划导出相关的数据模型。以下是对该类的简要分析： 
 * ### 类定义 
 * - **包名**: `com.cyberpeace.oj.course.service.bo` 
 * - **类名**: `TrainPlanExportBo` 
 * - **实现接口**: `Serializable` 
 * ### 注解 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * ### 字段（属性） 
 * - **trainPLanName** (String): 训练计划名称。 
 * - **courseName** (String): 课程名称。 
 * - **lessonName** (String): 课名称。 
 * - **startTime** (Date): 开始时间。 
 * - **endTime** (Date): 结束时间。 
 * - **orgPath** (String): 组织路径。 
 * ### 特殊说明 
 * - 注释中提到“不能增加和删除字段 也不能改变字段顺序”，这表明该类在设计上是固定的，任何修改都可能影响现有的使用场景。 
 * ### 总结 
 * `TrainPlanExportBo` 是一个简单的数据传输对象（DTO），用于封装与训练计划导出相关的信息。由于该类实现了 `Serializable` 接口，可以被序列化为字节流，便于在网络上传输或存储到文件系统中。 
**/
package com.cyberpeace.oj.course.service.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 不能增加和删除字段 也不能改变字段顺序
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class TrainPlanExportBo implements Serializable {

	/**
	 * 训练计划名称
	 *
	 * @see String
	 */
	private String trainPLanName;

	/**
	 * 课程名称
	 *
	 * @see String
	 */
	private String courseName;

	/**
	 * 课名称
	 *
	 * @see String
	 */
	private String lessonName;

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
	 * 组织路径
	 *
	 * @see String
	 */
	private String orgPath;

}
