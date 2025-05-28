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
@Getter
@Setter
public class ThreatDetectStatistics {

	@ApiModelProperty(value = "网络流量提取规则")
	private Integer trafficExtraction = 0;

	@ApiModelProperty(value = "流量攻击检测规则")
	private Integer trafficDetect = 0;

	@ApiModelProperty(value = "负载文件威胁规则")
	private Integer loadedFileThreat = 0;

	@ApiModelProperty(value = " 特定APT威胁检测规则")
	private Integer aptThreat = 0;

	@ApiModelProperty(value = "事件综合规则")
	private Integer eventIntegration = 0;

	@ApiModelProperty(value = "威胁画像规则")
	private Integer threatImage = 0;

	@ApiModelProperty(value = "攻击源分析规则")
	private Integer sourceAnalyze = 0;

}
