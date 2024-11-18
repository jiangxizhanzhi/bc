package com.cyberpeace.cr.singletrain.service.bo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 单人房 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see SingleTemplateBo
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel("单兵训练详情")
public class SingleRoomBo extends SingleTemplateBo implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("训练模板id")
	private String resourceId;

	/**
	 * 客房信息
	 *
	 * @see SingleRoomInfoBo
	 */
	@ApiModelProperty("用户使用训练")
	private SingleRoomInfoBo roomInfo;

	/**
	 * 当地场景id
	 *
	 * @see String
	 */
	@ApiModelProperty("本地场景id")
	private String localSceneId;

	/**
	 * 显示提交 btn
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("训练是否可提交")
	private Boolean showSubmitBtn;

	/**
	 * 复制拓扑id
	 *
	 * @see String
	 */
	@ApiModelProperty("拓扑id")
	private String copiedTopoId;

}
