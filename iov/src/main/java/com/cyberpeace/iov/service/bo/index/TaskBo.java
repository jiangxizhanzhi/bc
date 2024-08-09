package com.cyberpeace.iov.service.bo.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskBo {

	private String name;

	@ApiModelProperty(value = "状态，0=未开始，1=进行中，2=已完成")
	private Integer status;

}
