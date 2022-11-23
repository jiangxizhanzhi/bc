package com.cyberpeace.assess.controller.res;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AssessModelRsp implements Serializable {

	@ApiModelProperty("资源编号")
	private String resourceId;

	@ApiModelProperty(value = "名称", required = true)
	private String name;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty("版本号")
	private String visionId;

	@ApiModelProperty("创建者")
	private String createUser;

	@ApiModelProperty("标签")
	private List<String> tag;

	@ApiModelProperty("封面")
	private String logo;

	@ApiModelProperty("接口类型：1：超链接，2：数据库，3：可执行文件，4：其他类型")
	private Integer interfaceType;

	@ApiModelProperty("上传文件地址")
	private String attachmentUrl;

	@ApiModelProperty("超链接域名")
	private String urlDomain;

	@ApiModelProperty("参数列表")
	private List<JSONObject> params;
}
