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
 * 样式组件请求
 *
 * @Author xuxz
 * @Date 4/1/2023 上午11:38
 */

@Data
public class SituationDrawingReq extends BaseTable implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("资源id")
	@TableId(type = IdType.ASSIGN_UUID, value = "resource_id")
	@NotBlank(groups = Update.class)
	private String resourceId;

	/**
	 * 图形名称
	 */
	@ApiModelProperty("图形名称")
	@NotBlank(groups = { Update.class, Create.class })
	private String name;

	/**
	 * 图形类型
	 */
	@ApiModelProperty("图形类型")
	@NotBlank(groups = { Update.class, Create.class })
	private String type;

	/**
	 * 图形url
	 */
	@ApiModelProperty("图形url")
	@NotBlank(groups = { Update.class, Create.class })
	private String drawingUrl;
}
