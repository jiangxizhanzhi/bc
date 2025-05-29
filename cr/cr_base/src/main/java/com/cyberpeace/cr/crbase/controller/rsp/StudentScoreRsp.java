package com.cyberpeace.cr.crbase.controller.rsp;

import java.util.List;

import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.service.context.TrainingSummary;
import com.cyberpeace.cr.crbase.service.context.score.TaskStatistics;
import com.cyberpeace.cr.crbase.service.context.score.TrainingScore;
import com.cyberpeace.cr.crbase.utils.CrCommonUtil;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 学员得分响应
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `StudentScoreRsp` 的 Java 类，用于表示学员得分的响应数据模型。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - 该类主要用于封装学员在某项训练或任务中的得分及相关信息。 
 * - 它包含了多个字段来描述学员的能力、训练总结、任务统计等信息。 
 * 2. **主要字段**： 
 * - `abilityPercentage`：表示能力的百分比。 
 * - `abilityScore`：存储能力分数的列表，默认使用零列表初始化。 
 * - `abilityScoreResult`：表示能力分数的总和。 
 * - `comment`：提供关于学员表现的评语。 
 * - `finalScore`：最终得分。 
 * - `trainingPercentage`：训练的百分比。 
 * - `trainingSummary`：训练总结对象。 
 * - `taskStatistics`：任务统计对象。 
 * - `trainingScore`：训练得分对象。 
 * 3. **方法**： 
 * - `setAbilityScore`：设置能力分数列表，并计算能力分数的总和赋值给 `abilityScoreResult`。 
 * 4. **注解**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为字段添加了 API 文档描述。 
 * 5. **依赖**： 
 * - 依赖于 `CrCommonConstants` 和 `CrCommonUtil` 类，分别提供了常量和实用工具方法。 
 * 6. **设计特点**： 
 * - 通过组合其他对象（如 `TrainingSummary`, `TaskStatistics`, `TrainingScore`）来丰富数据结构。 
 * - 提供了灵活的方式来设置和获取能力分数及其结果。 
 * 总体来说，这个类是一个数据传输对象（DTO），用于在系统中传递学员得分的相关信息。 
**/
@Getter
@Setter
public class StudentScoreRsp {

	/**
	 * 能力百分比
	 */
	@ApiModelProperty(value = "能力占比")
	private String abilityPercentage = "";

	/**
	 * 能力分数
	 */
	@ApiModelProperty(value = "能力分")
	private List<Integer> abilityScore = CrCommonUtil
			.getZeroList(CrCommonConstants.SCORE_ABILITY_SIZE);

	/**
	 * 能力分数结果
	 */
	@ApiModelProperty(value = "能力分值")
	private Integer abilityScoreResult = 0;

	/**
	 * 评论
	 */
	@ApiModelProperty(value = "评语")
	private String comment = "";

	/**
	 * 最终比分
	 */
	@ApiModelProperty(value = "最终分数")
	private Integer finalScore = 0;

	/**
	 * 训练百分比
	 */
	@ApiModelProperty(value = "训练占比")
	private String trainingPercentage = "";

	/**
	 * 训练总结
	 */
	@ApiModelProperty(value = "训练分数")
	private TrainingSummary trainingSummary;

	/**
	 * 任务统计
	 */
	@ApiModelProperty(value = "任务统计")
	private TaskStatistics taskStatistics;

	/**
	 * 训练得分
	 */
	@ApiModelProperty(value = "阶段统计")
	private TrainingScore trainingScore;

	public void setAbilityScore(List<Integer> scoreList) {
		this.abilityScore = scoreList;
		int result = 0;
		for (Integer integer : scoreList) {
			result += integer * CrCommonConstants.SCORE_PER_STAR;
		}
		this.abilityScoreResult = result;
	}

}
