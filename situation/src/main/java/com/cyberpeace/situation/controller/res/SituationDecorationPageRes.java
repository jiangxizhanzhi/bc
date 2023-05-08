package com.cyberpeace.situation.controller.res;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 装饰组件分页返回
 *
 * @Author xuxz
 * @Date 4/1/2023 下午2:32
 */
@Data
public class SituationDecorationPageRes extends PageReqBase implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("资源id")
	@TableId(type = IdType.ASSIGN_UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 装饰类名称
	 */
	@ApiModelProperty("装饰类名称")
	private String name;

	/**
	 * 装饰类类型
	 */
	@ApiModelProperty("装饰类类型")
	private String type;

	/**
	 * 装饰类url
	 */
	@ApiModelProperty("装饰类url")
	private String decorationUrl;

	/**
	 * 创建人
	 */
	@ApiModelProperty("创建人")
	private String createUserId;

	/**
	 * 创建用户
	 */
	private String createUserName;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date updateTime;
}
