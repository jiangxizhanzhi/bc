package com.cyberpeace.situation.controller.res;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 样式组件分页返回
 *
 * @Author xuxz
 * @Date 4/1/2023 下午2:32
 */
@Data
public class SituationDrawingPageRes extends PageReqBase implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("资源id")
	@TableId(type = IdType.ASSIGN_UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 图形名称
	 */
	@ApiModelProperty("图形名称")
	private String name;

	/**
	 * 图形类型
	 */
	@ApiModelProperty("图形类型")
	private String type;

	/**
	 * 图形url
	 */
	@ApiModelProperty("图形url")
	private String drawingUrl;

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
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date updateTime;
}
