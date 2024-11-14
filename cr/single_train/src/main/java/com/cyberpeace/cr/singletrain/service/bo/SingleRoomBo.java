package com.cyberpeace.cr.singletrain.service.bo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 单房间Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel("单兵训练详情")
public class SingleRoomBo extends SingleTemplateBo implements Serializable {

	/**
	 * 训练模板id
	 */
	@ApiModelProperty("训练模板id")
	private String resourceId;

	/**
	 * 用户使用训练
	 */
	@ApiModelProperty("用户使用训练")
	private SingleRoomInfoBo roomInfo;

	/**
	 * 本地场景id
	 */
	@ApiModelProperty("本地场景id")
	private String localSceneId;

	/**
	 * 训练是否可提交
	 */
	@ApiModelProperty("训练是否可提交")
	private Boolean showSubmitBtn;

	/**
	 * 拓扑id
	 */
	@ApiModelProperty("拓扑id")
	private String copiedTopoId;

}
