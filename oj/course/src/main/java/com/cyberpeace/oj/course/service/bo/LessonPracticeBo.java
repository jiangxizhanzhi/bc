package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.Map;

import com.cyberpeace.oj.practice.service.bo.EvaluateResult;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 课程练习 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `LessonPracticeBo` 的 Java 类，该类实现了 `Serializable` 接口。这个类的主要目的是封装课程练习的相关信息。以下是详细的分析： 
 * ### 类名与包 
 * - **包名**: `com.cyberpeace.oj.course.service.bo` 
 * - **类名**: `LessonPracticeBo` 
 * ### 注解 
 * - `@Getter` 和 `@Setter`: Lombok 注解，用于自动生成属性的 getter 和 setter 方法。 
 * - `@ApiModel`: Swagger 注解，用于描述这个类在 API 文档中的模型信息。 
 * - `@ApiModelProperty`: Swagger 注解，用于描述类中的各个属性。 
 * ### 属性 
 * - **lessonInfo** (类型: `LessonBriefBo`) 
 * - 描述: 课时简略信息。 
 * - 注释: 使用了 `@ApiModelProperty` 注解，说明这个字段在 API 中的名称为 "课时简略信息"。 
 * - **practiceList** (类型: `Map<String, LessonPracticeListBo>`) 
 * - 描述: 课后练习题信息。 
 * - 注释: 使用了 `@ApiModelProperty` 注解，说明这个字段在 API 中的名称为 "课后练习题信息"。 
 * - **evaluateResult** (类型: `EvaluateResult`) 
 * - 描述: 评估结果。 
 * - 注释: 使用了 `@ApiModelProperty` 注解，说明这个字段在 API 中的名称为 "评估结果"。 
 * - **practiceInfo** (类型: `PracticeBriefBo`) 
 * - 描述: 课时简略信息。 
 * - 注释: 使用了 `@ApiModelProperty` 注解，说明这个字段在 API 中的名称为 "课时简略信息"。 
 * ### 总结 
 * - **用途**: `LessonPracticeBo` 类用于封装课程练习的信息，包括课时简略信息、课后练习题信息以及评估结果等。 
 * - **结构**: 这个类包含了四个主要属性，并且每个属性都使用了 Swagger 注解来描述其在 API 中的表现形式。 
 * - **设计**: 通过实现 `Serializable` 接口，这个类可以被序列化，方便在网络传输或持久化存储中使用。 
 * 这个类的设计是为了方便管理和传递课程练习的相关信息，适用于教育或在线学习平台的应用场景。 
**/
@Getter
@Setter
@ApiModel
public class LessonPracticeBo implements Serializable {

	/**
	 * 课程信息
	 *
	 * @see LessonBriefBo
	 */
	@ApiModelProperty("课时简略信息")
	private LessonBriefBo lessonInfo;

	/**
	 * 执业名单
	 *
	 * @see Map<String, LessonPracticeListBo>
	 */
	@ApiModelProperty("课后练习题信息")
	private Map<String, LessonPracticeListBo> practiceList;

	/**
	 * 评估结果
	 *
	 * @see EvaluateResult
	 */
	@ApiModelProperty("评估结果")
	private EvaluateResult evaluateResult;

	/**
	 * 练习信息
	 *
	 * @see PracticeBriefBo
	 */
	@ApiModelProperty("课时简略信息")
	private PracticeBriefBo practiceInfo;

}

