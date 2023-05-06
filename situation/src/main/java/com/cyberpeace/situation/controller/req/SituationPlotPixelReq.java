package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 标绘图元请求
 *
 * @Author xuxz
 * @Date 17/12/2022 下午5:15
 */

@Data
public class SituationPlotPixelReq extends BaseTable implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("资源id")
	@TableId(type = IdType.ASSIGN_UUID, value = "resource_id")
	@NotBlank(groups = Update.class)
	private String resourceId;

	/**
	 * 图标名称
	 */
	@ApiModelProperty("图标名称")
	@NotBlank(groups = Create.class)
	private String name;

	/**
	 * 图标url
	 */
	@NotBlank(groups = Create.class)
	@ApiModelProperty("图标url")
	private String plotUrl;

	/**
	 * 背景色
	 */
	@ApiModelProperty("背景色")
	private String color;
}
