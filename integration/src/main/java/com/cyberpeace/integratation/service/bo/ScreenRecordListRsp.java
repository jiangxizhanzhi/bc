package com.cyberpeace.integratation.service.bo;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ScreenRecordListRsp {

	@ApiModelProperty(value = "录屏时长")
	private Double duration;

	@ApiModelProperty(value = "录屏文件地址")
	private String fileUrl;

	@ApiModelProperty(value = "录屏文件名称")
	private String filename;

	@ApiModelProperty(value = "录屏大小")
	private Integer filesize;

	@ApiModelProperty(value = "录屏名称")
	private String name;

	@ApiModelProperty(value = "节点id")
	private String nodeId;

	@ApiModelProperty(value = "节点名称")
	private String nodeName;

	@ApiModelProperty(value = "组织名称")
	private String orgName;

	@ApiModelProperty(value = "录屏播放地址")
	private String playUrl;

	@ApiModelProperty(value = "会话协议")
	private String protocol;

	@ApiModelProperty(value = "场景id")
	private String sceneId;

	@ApiModelProperty(value = "场景名称")
	private String sceneName;

	@ApiModelProperty(value = "录屏时间")
	private Date time;

	@ApiModelProperty(value = "会话用户ID")
	private String user;

	@ApiModelProperty(value = "会话用户名")
	private String username;
}
