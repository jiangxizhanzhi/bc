package com.cyberpeace.space.service.context.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 编号理论
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * ### 分析与总结 
 * #### 1. **类的功能** 
 * - `IndexTheory` 是一个 Java 类，用于表示与编号理论相关的数据模型。 
 * - 它主要用于存储与理论课程、在线实验、理论考试、EXP 考试等相关的统计数据。 
 * #### 2. **属性说明** 
 * - 类中定义了多个整型属性，并通过 `@ApiModelProperty` 注解为每个字段提供了描述性注释，便于 API 文档生成。 
 * - 主要字段包括： 
 * - `theoryCourse`: 理论课程数量。 
 * - `onlineExp`: 在线实验资源数量。 
 * - `theoryExam`: 理论考试题目资源数量。 
 * - `expExam`: 实验考试题目资源数量。 
 * - `use`: 理论知识库使用次数。 
 * - `addedToday`: 当天新增理论知识库数量。 
 * - `updatedToday`: 当天修改理论知识库数量。 
 * - `videoCount`: 课程视频数量。 
 * - `videoDuration`: 课程视频总时长。 
 * - `sum`: 所有相关资源的总数（由 `calSum` 方法计算）。 
 * #### 3. **方法说明** 
 * - `calSum()`: 计算并设置所有资源的总数 (`sum`)。具体逻辑是将 `theoryCourse`、`onlineExp`、`theoryExam` 和 `expExam` 的值相加后赋值给 `sum`。 
 * #### 4. **框架与技术点** 
 * - **Lombok**: 使用了 `@Getter` 和 `@Setter` 注解，自动生成 getter 和 setter 方法，简化了代码量。 
 * - **Swagger**: 使用了 `@ApiModelProperty` 注解，方便生成 API 文档。 
 * - **设计意图**: 该类的设计目的是作为一个数据容器，用于存储和操作与编号理论相关的统计数据。 
 * #### 5. **潜在改进** 
 * - **空值检查**: 在 `calSum()` 方法中，可以增加对属性是否为 null 的检查，避免可能的空指针异常。 
 * - **扩展性**: 如果未来需要扩展更多的统计字段，可以通过继承或组合模式来增强类的功能。 
 * - **日志记录**: 如果某些操作（如 `calSum()`）需要记录日志，可以在方法中加入日志输出。 
 * #### 6. **适用场景** 
 * - 该类适用于需要统计和管理理论课程、在线实验、考试题目等相关数据的场景，例如教育平台的数据统计模块。 
 * 总结：`IndexTheory` 是一个简洁且功能明确的数据模型类，适合用于存储与编号理论相关的统计数据，并提供了基本的计算功能。 
**/
@Getter
@Setter
public class IndexTheory {

	/**
	 * 理论课程
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "理论课程")
	private Integer theoryCourse = 0;

	/**
	 * 在线经验
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "在线试验资源")
	private Integer onlineExp = 0;

	/**
	 * 理论考试
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "理论考题资源")
	private Integer theoryExam = 0;

	/**
	 * EXP 考试
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "试验考题资源")
	private Integer expExam = 0;

	/**
	 * 使用
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "理论知识库使用次数")
	private Integer use = 0;

	/**
	 * 今天添加
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "今日新增理论知识库")
	private Integer addedToday = 0;

	/**
	 * 今天更新
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "今日修改理论知识库")
	private Integer updatedToday = 0;

	/**
	 * 视频计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "课程视频数")
	private Integer videoCount = 0;

	/**
	 * 视频时长
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "课程视频时长")
	private Integer videoDuration = 0;

	/**
	 * 和
	 *
	 * @see Integer
	 */
	private Integer sum;

	/**
	 * Cal Sum
	 *
	 * @throws
	 */
	public void calSum() {
		this.sum = this.theoryCourse + this.onlineExp + this.theoryExam + this.expExam;
	}
}

