package com.cyberpeace.situation.dao.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author xuxz
 * @Date 30/12/2022 上午9:37
 */
@Data
@TableName("situation_dashboard_layer")
public class ComponentLayer extends BaseTable implements Serializable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	@NotBlank(groups = Create.class)
	@ApiModelProperty("组件id")
	private String componentId;

	@NotBlank(groups = Create.class)
	@ApiModelProperty("模板id")
	private String templateId;

	@NotBlank(groups = Create.class)
	@ApiModelProperty("图层名称")
	private String name;

	@NotNull(groups = Create.class)
	@ApiModelProperty("图层序号")
	private Integer order;

	@ApiModelProperty("组合图层")
	private String combination;
}
