package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 牌号分布 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `GradeDistributionBo` 的 Java 类，该类实现了 `Serializable` 接口。这个类的主要目的是存储和表示不同等级的分布情况。以下是详细的分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.oj.course.service.bo` 
 * - **类名**：`GradeDistributionBo` 
 * - **实现接口**：`Serializable` 
 * - **注解**： 
 * - `@ApiModel`：用于描述 API 模型，通常与 Swagger 文档一起使用。 
 * - `@Getter` 和 `@Setter`：Lombok 注解，自动生成 getter 和 setter 方法。 
 * ### 字段 
 * - **excellent**：表示“非常好”的数量，默认值为 0。 
 * - **fullScore**：表示“满分”的数量，默认值为 0。 
 * - **good**：表示“好”的数量，默认值为 0。 
 * - **notPass**：表示“未通过”的数量，默认值为 0。 
 * - **pass**：表示“通过”的数量，默认值为 0。 
 * ### 注释 
 * - 每个字段都有详细的注释，说明了字段的含义。 
 * - 类的文档注释（Javadoc）也提供了关于类用途的描述。 
 * ### 总结 
 * 这个类主要用于存储和表示某个课程或考试中不同成绩等级的数量分布。它通过五个字段来记录不同的成绩等级，并且每个字段都有默认值 0。这个类可以方便地与其他系统（如 Swagger 文档生成工具）集成，以便生成 API 文档。 
**/
@Getter
@Setter
@ApiModel
public class GradeDistributionBo implements Serializable {

	/**
	 * 非常好
	 *
	 * @see Integer
	 */
	@ApiModelProperty("良好")
	private Integer excellent = 0;

	/**
	 * 满分
	 *
	 * @see Integer
	 */
	@ApiModelProperty("满分")
	private Integer fullScore = 0;

	/**
	 * 好
	 *
	 * @see Integer
	 */
	@ApiModelProperty("优秀")
	private Integer good = 0;

	/**
	 * 未通过
	 *
	 * @see Integer
	 */
	@ApiModelProperty("未通过")
	private Integer notPass = 0;

	/**
	 * 通过
	 *
	 * @see Integer
	 */
	@ApiModelProperty("及格")
	private Integer pass = 0;

}

