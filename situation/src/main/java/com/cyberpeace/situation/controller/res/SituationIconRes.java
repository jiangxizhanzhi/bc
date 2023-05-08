package com.cyberpeace.situation.controller.res;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 图标返回
 *
 * @Author xuxz
 * @Date 15/12/2022 上午10:47
 */
@Data
public class SituationIconRes extends BaseTable implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("资源id")
	@TableId(type = IdType.ASSIGN_UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 图标名称
	 */
	@ApiModelProperty("图标名称")
	private String name;

	/**
	 * 类型
	 */
	@ApiModelProperty("类型")
	private String type;

	/**
	 * 图标url
	 */
	@ApiModelProperty("图标url")
	private String iconUrl;

	/**
	 * 创建人
	 */
	@ApiModelProperty("创建人")
	private String createUserId;

	/**
	 * 创建用户名称
	 */
	private String createUserName;

	/**
	 * 类型名称
	 */
	@ApiModelProperty("类型名称")
	private String typeName;

	/**
	 * 背景色
	 */
	@ApiModelProperty("背景色")
	private String color;
}
