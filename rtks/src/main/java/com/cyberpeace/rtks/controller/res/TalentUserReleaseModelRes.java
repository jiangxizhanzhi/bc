package com.cyberpeace.rtks.controller.res;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TalentUserReleaseModelRes implements Serializable {

	@ApiModelProperty("岗位名称")
	private String name;

	@ApiModelProperty("匹配度")
	private Integer percent;



}