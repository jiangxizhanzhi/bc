package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 时间安排 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `TimeScheduleBo` 的 Java 类，它实现了 `Serializable` 接口。这个类主要用于表示时间安排的信息。下面是该类的详细分析： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.oj.course.service.bo` 
 * - **导入的类**： 
 * - `java.io.Serializable`：接口，表示该类可以序列化。 
 * - `java.util.List`：泛型接口，用于表示教室列表。 
 * - `io.swagger.annotations.ApiModel`：注解，用于描述这个类。 
 * - `io.swagger.annotations.ApiModelProperty`：注解，用于描述类中的属性。 
 * - `lombok.Getter` 和 `lombok.Setter`：注解，用于自动生成 getter 和 setter 方法。 
 * ### 类注释 
 * - 作者：SN 
 * - 版本：1.0.0 
 * - 创建日期：2024年11月8日 
 * ### 类属性 
 * - **classrooms**（教室列表）： 
 * - 类型：`List<ClassroomBo>` 
 * - 注解：`@ApiModelProperty("课堂信息")` 
 * - 说明：表示课程安排中涉及的教室列表。 
 * - **date**（日期）： 
 * - 类型：`String` 
 * - 注解：`@ApiModelProperty("时间")` 
 * - 说明：表示课程安排的具体日期。 
 * ### 总结 
 * `TimeScheduleBo` 类是一个数据传输对象（DTO），用于在系统内部或外部传递关于课程时间安排的信息。它包含两个主要属性：一个教室列表和一个日期字符串。这些属性通过 Lombok 的注解自动生成了 getter 和 setter 方法，并且通过 Swagger 注解来描述这些属性的意义，方便文档生成工具使用。 
**/
@Getter
@Setter
@ApiModel
public class TimeScheduleBo implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
    /**
     * 教室
     *
     * @see List<ClassroomBo>
     */
    @ApiModelProperty("课堂信息")
	private List<ClassroomBo> classrooms;

    /**
     * 日期
     *
     * @see String
     */
    @ApiModelProperty("时间")
	private String date;

}

