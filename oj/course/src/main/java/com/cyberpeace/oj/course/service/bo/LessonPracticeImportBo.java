package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cyberpeace.oj.practice.dao.entity.Paper;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperationRef;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheoryRef;
import com.cyberpeace.oj.practice.service.bo.PaperPracticeContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 课程练习导入 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `LessonPracticeImportBo` 的 Java 类，该类实现了 `Serializable` 接口。这个类主要用于表示课程练习导入的相关业务对象（BO, Business Object）。下面是对代码的详细分析： 
 * ### 类定义与继承 
 * - **类名**：`LessonPracticeImportBo` 
 * - **实现接口**：`Serializable` 
 * - **注解**： 
 * - `@Getter` 和 `@Setter`：Lombok 注解，用于自动生成属性的 getter 和 setter 方法。 
 * - `@ApiModelProperty`：Swagger 注解，用于在 API 文档中描述属性。 
 * ### 属性 
 * - **paper**：类型为 `Paper`，表示试卷信息。使用 `@ApiModelProperty` 注解描述为“试卷信息”。 
 * - **practiceTheory**：类型为 `List<PracticeTheoryRef>`，表示理论题信息。默认初始化为一个空的 `ArrayList`。使用 `@ApiModelProperty` 注解描述为“理论题信息”。 
 * - **practiceOperation**：类型为 `List<PracticeOperationRef>`，表示实操题信息。默认初始化为一个空的 `ArrayList`。使用 `@ApiModelProperty` 注解描述为“实操题信息”。 
 * - **paperPracticeContext**：类型为 `List<PaperPracticeContext>`，表示试卷习题分数信息。默认初始化为一个空的 `ArrayList`。使用 `@ApiModelProperty` 注解描述为“试卷习题分数信息”。 
 * ### 依赖类 
 * - **Paper**：试卷实体类。 
 * - **PracticeTheoryRef**：理论题关联类。 
 * - **PracticeOperationRef**：实操题关联类。 
 * - **PaperPracticeContext**：试卷习题分数上下文类。 
 * ### 总结 
 * `LessonPracticeImportBo` 类是一个数据传输对象（DTO），用于封装课程练习导入所需的各种信息。它包含了试卷信息、理论题信息、实操题信息以及试卷习题分数信息。这些信息可以通过 Swagger 自动生成的 API 文档进行查看和操作。通过 Lombok 注解，减少了样板代码，使代码更加简洁易读。 
**/
@Getter
@Setter
public class LessonPracticeImportBo implements Serializable {

	/**
	 * 纸
	 *
	 * @see Paper
	 */
	@ApiModelProperty("试卷信息")
	private Paper paper;

	/**
	 * 实践理论
	 *
	 * @see List<PracticeTheoryRef>
	 */
	@ApiModelProperty("理论题信息")
	private List<PracticeTheoryRef> practiceTheory = new ArrayList<>();

	/**
	 * 实践实操
	 *
	 * @see List<PracticeOperationRef>
	 */
	@ApiModelProperty("实操题信息")
	private List<PracticeOperationRef> practiceOperation = new ArrayList<>();

	/**
	 * 纸质实践背景
	 *
	 * @see List<PaperPracticeContext>
	 */
	@ApiModelProperty("试卷习题分数信息")
	private List<PaperPracticeContext> paperPracticeContext = new ArrayList<>();
}

