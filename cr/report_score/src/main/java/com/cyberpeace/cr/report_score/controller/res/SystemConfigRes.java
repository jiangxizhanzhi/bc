package com.cyberpeace.cr.report_score.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("定制配置")
public class SystemConfigRes implements Serializable {

	@ApiModelProperty("数据大屏名称")
	private String name;

	@ApiModelProperty("启动对应的场景id，多个id以,符号分割")
	private String scene;

	@ApiModelProperty("场景拓扑结构id，态势获取拓扑使用")
	private String topo;
}
