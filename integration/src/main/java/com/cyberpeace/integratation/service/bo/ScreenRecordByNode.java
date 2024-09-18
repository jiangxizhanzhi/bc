package com.cyberpeace.integratation.service.bo;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ScreenRecordByNode {

	@ApiModelProperty(value = "节点id")
	private String nodeId;

	@ApiModelProperty(value = "节点名称")
	private String nodeName;

	private List<ScreenRecordListRsp> recordList;
}
