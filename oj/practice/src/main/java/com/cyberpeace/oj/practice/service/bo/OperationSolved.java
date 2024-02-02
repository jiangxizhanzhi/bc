package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationSolved implements Serializable {

	@ApiModelProperty("题型")
	private Integer category;

	@ApiModelProperty("解决情况")
	private Integer solved;

	@ApiModelProperty("总数")
	private Integer sum;

	@ApiModelProperty("对应题型的id列表，使用逗号分隔")
	private String resourceIds;

	public List<String> getIds() {
		String[] ids = StringUtils.split(resourceIds, ",");
		return Arrays.asList(ids);
	}
}
