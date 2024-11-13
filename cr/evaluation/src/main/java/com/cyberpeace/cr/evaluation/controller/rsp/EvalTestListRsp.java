package com.cyberpeace.cr.evaluation.controller.rsp;

import com.cyberpeace.common.model.user.UserInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EvalTestListRsp implements Serializable {

	@ApiModelProperty("试验id")
	private String testId;

	@ApiModelProperty("试验名称")
	private String name;

	@ApiModelProperty("时长")
	private Integer time;

	@ApiModelProperty("节点数量")
	private Integer nodeCount;

	@ApiModelProperty("试验类型")
	private Integer type;

	@ApiModelProperty("难度")
	private Integer difficulty;

	@ApiModelProperty("模板名称")
	private String templateName;

	@ApiModelProperty("服务类型")
	private List<Integer> cloudServices;

	@ApiModelProperty("工具数量")
	private Integer toolCount;

	@ApiModelProperty("试验工具列表")
	private List<String> toolList;

	@ApiModelProperty("试验目标")
	private String target;

	@ApiModelProperty("参与人员")
	private List<UserInfo> userInfoList;

	@ApiModelProperty("试验状态:0=未开始，1=进行中")
	private Integer status;

	@ApiModelProperty("评估次数")
	private Integer count;

	@ApiModelProperty("最高得分")
	private Integer score;

}
