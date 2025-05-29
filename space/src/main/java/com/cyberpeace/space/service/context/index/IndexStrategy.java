package com.cyberpeace.space.service.context.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 编号策略
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `IndexStrategy` 的 Java 类，用于表示某种编号或策略相关的上下文信息。以下是对其的简要分析： 
 * ### 主要功能 
 * - **属性定义**：类中包含多个整型字段，每个字段通过 `@ApiModelProperty` 注解描述其用途，如操作系统、数据库、中间件等。这些字段主要用于存储不同系统的计数或状态。 
 * - **Getter 和 Setter 方法**：通过 Lombok 的 `@Getter` 和 `@Setter` 注解自动生成了所有字段的 getter 和 setter 方法，便于外部访问和修改这些属性。 
 * - **`calSum` 方法**：该方法计算并设置 `sum` 字段的值，它是其他字段（如 `os`, `database` 等）之和。 
 * ### 设计特点 
 * - **注解支持**：使用了 Swagger 的 `@ApiModelProperty` 注解来为每个字段提供元数据，这通常用于生成 API 文档。 
 * - **封装性**：通过 getter 和 setter 方法隐藏了内部实现细节，提供了良好的封装性。 
 * - **可扩展性**：虽然目前只包含了特定类型的字段，但如果需要增加新的类型，只需添加相应的字段即可。 
 * ### 总结 
 * 这个类是一个简单的数据传输对象（DTO），主要用于存储和操作与编号策略相关的信息。它具有良好的结构化设计，适合在需要处理复杂数据集合的应用场景中使用。 
**/
@Getter
@Setter
public class IndexStrategy {

	/**
	 * 操作系统
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "操作系统")
	private Integer os = 0;

	/**
	 * 数据库
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "数据库")
	private Integer database = 0;

	/**
	 * 中间件
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "Web中间件")
	private Integer middleware = 0;

	/**
	 * 交换机
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "交换机")
	private Integer switchboard = 0;

	/**
	 * 路由器
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "路由器")
	private Integer router = 0;

	/**
	 * 大数据
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "大数据平台")
	private Integer bigData = 0;

	/**
	 * 防病毒
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "防病毒系统")
	private Integer antiVirus = 0;

	/**
	 * 终端
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "终端安全系统")
	private Integer terminal = 0;

	/**
	 * 边境
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "边界防护设备")
	private Integer border = 0;

	/**
	 * 侵入检查
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "入侵检测系统:")
	private Integer invasionCheck = 0;

	/**
	 * 使用
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "策略库使用次数")
	private Integer use = 0;

	/**
	 * 和
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "总数")
	private Integer sum = 0;

	/**
	 * 今天添加
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "今日新增策略库")
	private Integer addedToday = 0;

	/**
	 * 今天更新
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "今日修改策略库")
	private Integer updatedToday = 0;

	/**
	 * Cal Sum
	 *
	 * @throws
	 */
	public void calSum() {
		this.sum = this.os + this.database + this.middleware + this.switchboard + this.router
				+ this.bigData + this.antiVirus + this.terminal + this.border + this.invasionCheck;
	}
}

