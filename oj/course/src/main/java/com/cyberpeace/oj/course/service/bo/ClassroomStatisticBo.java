package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.oj.ojbase.service.bo.UserNoteScoreBo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 课堂统计 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `ClassroomStatisticBo` 的 Java 类，用于表示课堂统计信息。以下是该类的详细分析： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.oj.course.service.bo` 
 * - **导入语句**：导入了 `Serializable` 接口、`UserNoteScoreBo` 类以及 Swagger 注解。 
 * - **注解**： 
 * - `@ApiModel`：用于描述 API 模型。 
 * - `@Getter` 和 `@Setter`：Lombok 注解，自动生成属性的 getter 和 setter 方法。 
 * - `@Builder`：Lombok 注解，提供构建器模式来创建对象。 
 * - `@AllArgsConstructor` 和 `@NoArgsConstructor`：Lombok 注解，分别生成所有参数构造器和无参构造器。 
 * ### 类成员变量 
 * 1. **gradeDistribution** (`GradeDistributionBo`)： 
 * - **注释**：表示等级分布。 
 * - **注解**：`@ApiModelProperty(value = "成绩统计")`，用于 Swagger 文档生成。 
 * 2. **signInfo** (`SignInfoBo`)： 
 * - **注释**：表示标志信息。 
 * - **注解**：`@ApiModelProperty(value = "签到统计")`，用于 Swagger 文档生成。 
 * 3. **top20** (`List<UserNoteScoreBo>`)： 
 * - **注释**：表示前 20 名用户的成绩。 
 * - **注解**：`@ApiModelProperty(value = "top20")`，用于 Swagger 文档生成。 
 * ### 总结 
 * - **功能**：`ClassroomStatisticBo` 类用于封装课堂统计信息，包括等级分布、签到统计和前 20 名用户的分数。 
 * - **特性**：使用 Lombok 注解简化了代码，提供了自动化的 getter 和 setter 方法、构造器以及构建器模式。 
 * - **文档**：通过 Swagger 注解生成 API 文档，便于前端或其他服务理解该类的用途和结构。 
 * 这个类的设计使得课堂统计信息的获取和展示变得简洁明了，并且易于扩展和维护。 
**/
@Getter
@Setter
@ApiModel
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomStatisticBo implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 等级分布
	 *
	 * @see GradeDistributionBo
	 */
	@ApiModelProperty("成绩统计")
	private GradeDistributionBo gradeDistribution;

	/**
	 * 标志信息
	 *
	 * @see SignInfoBo
	 */
	@ApiModelProperty("签到统计")
	private SignInfoBo signInfo;

	/**
	 * 前 20 名
	 *
	 * @see List<UserNoteScoreBo>
	 */
	@ApiModelProperty("top20")
	private List<UserNoteScoreBo> top20;

}

