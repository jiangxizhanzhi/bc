package com.cyberpeace.space.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdAndName {

//	private Integer id;

	@ApiModelProperty(value = "id")
	private String resourceId;

	@ApiModelProperty(value = "名称, 前端不用传")
	private String name;

}
