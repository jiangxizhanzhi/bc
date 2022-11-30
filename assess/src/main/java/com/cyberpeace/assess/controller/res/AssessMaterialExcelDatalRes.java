package com.cyberpeace.assess.controller.res;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.assess.dao.entity.AssessMaterial;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("课程目录配置信息")
public class AssessMaterialExcelDatalRes extends AssessMaterial {

	@ApiModelProperty("数据内容")
	private JSONObject data;

	@ApiModelProperty("附件三方工具url")
	private String attachmentUrl;

}
