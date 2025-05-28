/**
 * 该代码定义了一个名为 `RaceStatistics` 的 Java 类，用于表示比赛中的统计数据。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - `RaceStatistics` 是一个数据传输对象（DTO），用于封装比赛相关的统计信息。 
 * 2. **字段说明**： 
 * - `scoreRanking`：表示分数排名，使用整数类型，默认值为 0。 
 * - `roundMechanism`：表示圆形机构或轮次机制规则，使用整数类型，默认值为 0。 
 * - `violationJudge`：表示违规法官或违规判定规则，使用整数类型，默认值为 0。 
 * 3. **注解说明**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为字段提供元数据描述，便于生成 API 文档。 
 * 4. **设计意图**： 
 * - 该类旨在集中管理比赛统计相关的数据，方便在系统中传递和使用这些信息。 
 * 总结：这是一个简单的 DTO 类，用于存储和传递比赛的统计数据，具有良好的可读性和扩展性。 
**/
package com.cyberpeace.space.service.context.rule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 比赛统计数据
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class RaceStatistics {

	/**
	 * 分数排名
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "计分排行规则")
	private Integer scoreRanking = 0;

	/**
	 * 圆形机构
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "轮次机制规则")
	private Integer roundMechanism = 0;

	/**
	 * 违规法官
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "违规判定规则")
	private Integer violationJudge = 0;

}
