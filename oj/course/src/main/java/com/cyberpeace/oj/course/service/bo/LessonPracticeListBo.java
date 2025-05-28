/**
 * 这段代码定义了一个名为 `LessonPracticeListBo` 的类，该类用于表示课程练习清单。以下是对其主要特性和功能的简要分析： 
 * ### 类名和包声明 
 * - **包名**：`com.cyberpeace.oj.course.service.bo` 
 * - **类名**：`LessonPracticeListBo` 
 * ### 类注释 
 * - 类描述了这是一个表示“课程练习清单”的业务对象（BO）。 
 * - 作者是“SN”，版本为1.0.0，日期为2024年11月8日。 
 * ### 导入的类 
 * - `Serializable`：标记此对象可以序列化。 
 * - `TheoryLessonBo`：一个理论课程业务对象。 
 * - `ApiModel` 和 `ApiModelProperty`：来自 Swagger 注解，用于生成 API 文档。 
 * ### 类的特性 
 * - **继承与实现**： 
 * - `LessonPracticeListBo` 实现了 `Serializable` 接口。 
 * - **属性**： 
 * - `count`：习题总数，类型为 `Integer`。 
 * - `totalScore`：总分，类型为 `Double`。 
 * - `practiceBos`：包含练习业务对象的列表，泛型为 `? extends TheoryLessonBo`，即列表中的元素必须是 `TheoryLessonBo` 或其子类的实例。 
 * ### 特性注释 
 * - 每个属性都有对应的注释，描述了它们的作用。 
 * - 使用 `@ApiModelProperty` 注解来描述每个属性在 API 文档中的显示信息。 
 * ### Getter 和 Setter 方法 
 * - 通过 `@Getter` 和 `@Setter` 注解自动生成 getter 和 setter 方法。 
 * ### 总结 
 * 这个类是一个简单的数据传输对象（DTO），用于封装课程练习清单的相关信息。它包括习题数量、总分以及一系列练习的业务对象。这个类的设计使得它可以方便地在不同服务之间传递数据，并且可以通过 Swagger 自动生成 API 文档。 
**/
package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.oj.practice.service.bo.TheoryLessonBo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 课程练习清单 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class LessonPracticeListBo implements Serializable {

	/**
	 * 计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("习题总数")
	private Integer count;

	/**
	 * 总分
	 *
	 * @see Double
	 */
	@ApiModelProperty("总分")
	private Double totalScore;

	/**
	 * 练习 BOS
	 *
	 * @see List<? extends TheoryLessonBo>
	 */
	private List<? extends TheoryLessonBo> practiceBos;

}
