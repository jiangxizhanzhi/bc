/**
 * 这段代码定义了一个名为 `SpaceAlgorithmStatistics` 的 Java 类，用于表示与空间算法相关的统计信息。以下是对其的简要分析： 
 * 1. **类的功能**： 
 * - 该类主要用于存储和管理与空间算法相关的统计数据，包括个人、团队、岗位及专业知识的评估算法。 
 * 2. **注解使用**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法，减少了冗余代码。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段提供了描述性注释，便于接口文档生成。 
 * 3. **字段说明**： 
 * - `expertise`：表示专业知识的能力评估算法。 
 * - `role`：表示岗位能力评估算法。 
 * - `individual`：表示个人能力评估算法。 
 * - `team`：表示团队能力评估算法。 
 * 4. **设计目的**： 
 * - 该类的设计目的是作为一个数据传输对象（DTO），在系统中传递关于空间算法的统计信息。 
 * 5. **适用场景**： 
 * - 可能用于前端展示或后端逻辑处理中，需要对这些能力评估算法进行统一管理和操作的场景。 
 * 总体而言，这是一个简单但功能明确的数据结构类，适合用于封装和传递特定领域的统计信息。 
**/
package com.cyberpeace.space.service.context.algorithm;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Space 算法统计
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceAlgorithmStatistics {

	/**
	 * 专门知识
	 */
	@ApiModelProperty(value = "专业能力评估算法")
	private Integer expertise;

	/**
	 * 岗位
	 */
	@ApiModelProperty(value = "岗位能力评估算法")
	private Integer role;

	/**
	 * 个人
	 */
	@ApiModelProperty(value = "个人能力评估算法")
	private Integer individual;

	/**
	 * 团队
	 */
	@ApiModelProperty(value = "团队能力评估算法")
	private Integer team;

}
