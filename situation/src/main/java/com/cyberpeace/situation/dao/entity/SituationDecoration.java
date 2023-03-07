package com.cyberpeace.situation.dao.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author xuxz
 * @Date 4/1/2023 上午11:32
 */

@Data
@TableName("situation_components_decoration")
public class SituationDecoration extends BaseTable implements Serializable {

	@ApiModelProperty("资源id")
	@TableId(type = IdType.ASSIGN_UUID, value = "resource_id")
	private String resourceId;

	@ApiModelProperty("装饰名称")
	private String name;

	@ApiModelProperty("装饰类型")
	private String type;

	@ApiModelProperty("装饰url")
	private String decorationUrl;

	@ApiModelProperty("创建人")
	private String createUserId;

}
