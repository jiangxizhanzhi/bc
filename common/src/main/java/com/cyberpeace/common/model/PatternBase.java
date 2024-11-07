package com.cyberpeace.common.model;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.common.util.LocalStringUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 图案基底
 *
 * @author SN
 * @date 2024/11/07
 */
@ApiModel
@Data
public class PatternBase implements Serializable {

	@ApiModelProperty(value = "模糊查询筛选参数")
	private String pattern;

	/**
	 * 设置模式
	 *
	 * @param pattern 模式
	 */
	public void setPattern(String pattern) {
		String newPattern = pattern;

		if (!StringUtils.isBlank(pattern)) {

			newPattern = LocalStringUtil.transfer(pattern);

		}

		this.pattern = newPattern;
	}
}
