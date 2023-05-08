package com.cyberpeace.situation.controller.res;

import com.cyberpeace.situation.controller.req.SituationComponentsAnimationAddReq;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 态势动画组件返回
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class SituationComponentsAnimationRes extends SituationComponentsAnimationAddReq {

	/**
	 * 资源id
	 */
	@ApiModelProperty()
	private String resourceId;

	/**
	 * 组件名称
	 */
	@ApiModelProperty("组件名称")
	private String chartName;

	/**
	 * 组件类型名称
	 */
	@ApiModelProperty("组件类型名称")
	private String chartCategoryName;

	/**
	 * 创建时间
	 */
	@ApiModelProperty("创建时间")
	private String creatTime;

	/**
	 * 创建者
	 */
	@ApiModelProperty("创建者")
	private String creator;

}
