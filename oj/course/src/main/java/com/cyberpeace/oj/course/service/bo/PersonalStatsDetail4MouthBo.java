package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 个人数据详情4口无双
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `PersonalStatsDetail4MouthBo` 的 Java 类，该类实现了 `Serializable` 接口。这个类的主要目的是封装用户在近30天内的各种学习统计数据。以下是对代码的简要分析： 
 * ### 类名和包 
 * - **包名**: `com.cyberpeace.oj.course.service.bo` 
 * - **类名**: `PersonalStatsDetail4MouthBo` 
 * ### 类的注释 
 * - 该类用于表示用户的个人数据详情。 
 * - 作者是 SN，版本是 1.0.0。 
 * ### 类的属性 
 * - `thirtyDays`: 一个 `List<String>` 类型的属性，表示近30天的日期。每个日期可能是一个字符串形式。 
 * - `lessonLearnRecord`: 一个 `List<Integer>` 类型的属性，表示用户学习的课程记录（实际数据为课时）。 
 * - `reportFinishRecord`: 一个 `List<Integer>` 类型的属性，表示用户完成的报告数量。 
 * - `averageScore`: 一个 `List<Integer>` 类型的属性，表示用户报告的平均分。 
 * ### 注解 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解来自动生成 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解来描述每个属性的作用。 
 * ### 总结 
 * - 这个类主要用于存储和传输用户在近30天内的学习统计数据。 
 * - 数据包括日期、学习记录、报告完成记录和平均分。 
 * - 通过 Lombok 和 Swagger 注解，减少了样板代码，提高了代码的可读性和维护性。 
 * 这个类可以作为一个数据传输对象（DTO），用于在不同层之间传递数据。 
**/
@Getter
@Setter
public class PersonalStatsDetail4MouthBo implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
    /**
     * 三十天
     *
     * @see List<String>
     */
    @ApiModelProperty("近30天日期")
    private List<String> thirtyDays;

    /**
     * 课程学习记录
     *
     * @see List<Integer>
     */
    @ApiModelProperty("学习课程(实际数据为课时)")
    private List<Integer> lessonLearnRecord;

    /**
     * 报告完成记录
     *
     * @see List<Integer>
     */
    @ApiModelProperty("完成报告")
    private List<Integer> reportFinishRecord;

    /**
     * 平均分
     *
     * @see List<Integer>
     */
    @ApiModelProperty("报告平均分")
    private List<Integer> averageScore;

}

