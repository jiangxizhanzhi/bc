package com.cyberpeace.common.model;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.common.util.LocalStringUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class PatternReq implements Serializable {

	@ApiModelProperty(value = "模糊查询筛选参数")
	private String pattern;

	public String getPattern() {
		if (!StringUtils.isBlank(this.pattern)) {
			return LocalStringUtil.transfer(this.pattern);
		} else {
			return this.pattern;
		}
	}

	public String getOriginPattern() {
		return this.pattern;
	}
}
