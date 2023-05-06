package com.cyberpeace.situation.controller.req;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 动画组件删除请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class SituationComponentsAnimationDeleteReq {

	/**
	 * 动画组件id列表
	 */
	@ApiModelProperty()
	@NotEmpty()
	private List<String> resourceIds;
}
