package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.situation.constant.SituationErrorCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 数据库工具新增请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class SituationDbToolAddReq implements Serializable {

	/**
	 * 分组
	 */
	@ApiModelProperty("分组")
	@NotBlank(groups = { Create.class, Update.class },
			message = SituationErrorCode.NAME_EMPTY_ERROR)
	String name;

	/**
	 * 工具描述
	 */
	@ApiModelProperty(value = "工具描述")
	private String description;

	/**
	 * 工具类型
	 */
	@ApiModelProperty("工具类型")
	String toolCategory;

	/**
	 * 工具附件
	 */
	@ApiModelProperty(value = "工具附件")
	private String attachment;

}
