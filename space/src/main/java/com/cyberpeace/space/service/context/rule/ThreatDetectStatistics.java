package com.cyberpeace.space.service.context.rule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 威胁检测统计信息
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 该代码定义了一个名为 `ThreatDetectStatistics` 的 Java 类，位于 `com.cyberpeace.space.service.context.rule` 包中。以下是代码的简要分析： 
 * ### 主要功能： 
 * 该类用于存储与威胁检测相关的统计信息，包含多个字段，每个字段代表一种威胁检测或分析规则的计数。 
 * ### 关键特性： 
 * 1. **注解使用**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段提供了描述性注释，便于生成 API 文档。 
 * 2. **字段说明**： 
 * - 每个字段都以 `Integer` 类型定义，并初始化为默认值 `0`。 
 * - 字段名和注释清晰地描述了其用途，例如： 
 * - `trafficExtraction`：网络流量提取规则。 
 * - `trafficDetect`：流量攻击检测规则。 
 * - `loadedFileThreat`：负载文件威胁规则。 
 * - `aptThreat`：特定 APT（高级持续性威胁）检测规则。 
 * - `eventIntegration`：事件综合规则。 
 * - `threatImage`：威胁画像规则。 
 * - `sourceAnalyze`：攻击源分析规则。 
 * 3. **设计目的**： 
 * - 该类是一个数据传输对象（DTO），主要用于存储威胁检测相关的统计数据。 
 * - 适用于需要将这些统计数据作为 API 返回值或传递给其他模块的场景。 
 * ### 总结： 
 * 该类是一个简单的、结构化的数据容器，用于表示威胁检测中的多种统计信息。通过注解的使用，增强了代码的可读性和可维护性，适合在网络安全相关系统中使用。 
**/
@Getter
@Setter
public class ThreatDetectStatistics {

	/**
	 * 流量萃取
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "网络流量提取规则")
	private Integer trafficExtraction = 0;

	/**
	 * 流量检测
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "流量攻击检测规则")
	private Integer trafficDetect = 0;

	/**
	 * 加载文件威胁
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "负载文件威胁规则")
	private Integer loadedFileThreat = 0;

	/**
	 * APT 威胁
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = " 特定APT威胁检测规则")
	private Integer aptThreat = 0;

	/**
	 * 事件集成
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "事件综合规则")
	private Integer eventIntegration = 0;

	/**
	 * 威胁图像
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "威胁画像规则")
	private Integer threatImage = 0;

	/**
	 * 来源分析
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "攻击源分析规则")
	private Integer sourceAnalyze = 0;

}

