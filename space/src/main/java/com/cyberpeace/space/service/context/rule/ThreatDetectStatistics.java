package com.cyberpeace.space.service.context.rule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThreatDetectStatistics {

	@ApiModelProperty(value = "网络流量提取规则")
	private Integer trafficExtraction;

	@ApiModelProperty(value = "流量攻击检测规则")
	private Integer trafficDetect;

	@ApiModelProperty(value = "负载文件威胁规则")
	private Integer loadedFileThreat;

	@ApiModelProperty(value = " 特定APT威胁检测规则")
	private Integer aptThreat;

	@ApiModelProperty(value = "事件综合规则")
	private Integer eventIntegration;

	@ApiModelProperty(value = "威胁画像规则")
	private Integer threatImage;

	@ApiModelProperty(value = "攻击源分析规则")
	private Integer sourceAnalyze;

}
