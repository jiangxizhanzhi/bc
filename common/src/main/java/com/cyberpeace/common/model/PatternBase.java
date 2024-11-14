package com.cyberpeace.common.model;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.common.util.LocalStringUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class PatternBase implements Serializable {

	/**
	 * 模糊查询筛选参数
	 */
	@ApiModelProperty(value = "模糊查询筛选参数")
	private String pattern;

	public void setPattern(String pattern) {
		String newPattern = pattern;

		if (!StringUtils.isBlank(pattern)) {

			newPattern = LocalStringUtil.transfer(pattern);

		}

		this.pattern = newPattern;
	}
}
