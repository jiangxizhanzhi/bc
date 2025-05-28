/**
 * 这段代码定义了一个名为 `TrainingSummary` 的 Java 类，用于表示训练的概况。以下是对其功能的简要分析： 
 * 1. **类的功能**： 
 * - 该类主要用于记录和管理训练过程中的各种评分、时间成本等信息。 
 * - 它包含多个字段，分别用于存储客观得分、主观得分、总分、时间成本等。 
 * 2. **字段说明**： 
 * - `getScoreObjective` 和 `getScoreSubjective`：分别记录客观得分和主观得分，默认值为 0。 
 * - `sumScoreObjective` 和 `sumScoreSubjective`：分别记录客观总分和主观总分，默认值为 0。 
 * - `timeCost`：记录训练用时，类型为字符串。 
 * - `duration`：记录推荐用时，整数值，默认为 0。 
 * 3. **方法说明**： 
 * - `addScore`：根据任务类型（客观或主观）增加相应的得分。 
 * - `addTotal`：根据任务类型（客观或主观）增加相应的总分。 
 * 4. **注解使用**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段提供描述信息。 
 * 5. **依赖**： 
 * - 该类依赖于 `Mission` 类的静态方法 `isObjective`，用于判断任务类型是否为客观任务。 
 * 总体而言，这个类设计简洁，主要负责管理和计算训练过程中的评分和时间相关信息。 
**/
package com.cyberpeace.cr.crbase.service.context;

import com.cyberpeace.cr.crbase.dao.entity.Mission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 训练概况
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class TrainingSummary {

	/**
	 * 获取得分目
	 */
	@ApiModelProperty(value = "客观得分")
	private Integer getScoreObjective = 0;

	/**
	 * 获取得分主观
	 */
	@ApiModelProperty(value = "主观得分")
	private Integer getScoreSubjective = 0;

	/**
	 * SUM SCORE 目标
	 */
	@ApiModelProperty(value = "客观总分")
	private Integer sumScoreObjective = 0;

	/**
	 * 总和分数 主观
	 */
	@ApiModelProperty(value = "主观总分")
	private Integer sumScoreSubjective = 0;

	/**
	 * 时间成本
	 */
	@ApiModelProperty(value = "训练用时")
	private String timeCost;

	/**
	 * 期间
	 */
	@ApiModelProperty(value = "推荐用时")
	private Integer duration = 0;

	public void addScore(Integer addend, Integer missionType) {
		if (Mission.isObjective(missionType)) {
			this.getScoreObjective += addend;
		} else {
			this.getScoreSubjective += addend;
		}
	}

	public void addTotal(Integer addend, Integer missionType) {
		if (Mission.isObjective(missionType)) {
			this.sumScoreObjective += addend;
		} else {
			this.sumScoreSubjective += addend;
		}
	}

}
