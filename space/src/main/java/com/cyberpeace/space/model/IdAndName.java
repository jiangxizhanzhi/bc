package com.cyberpeace.space.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * id和名称
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IdAndName {

//	private Integer id;

	@ApiModelProperty(value = "id")
	private String resourceId;

	@ApiModelProperty(value = "名称, 前端不用传")
	private String name;

}
