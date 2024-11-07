package com.cyberpeace.cr.crbase.controller.rsp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 靶场模板公共信息类
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class BusinessTemplateRsp implements Serializable {

	/**
	 * id
	 */
	@ApiModelProperty(value = "冗余字段，resource_id")
	private String id;

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "唯一标识符")
	private String resourceId;

	/**
	 * 场景经理id
	 */
	@ApiModelProperty(value = "拓扑id，前端兼容")
	private String sceneManagerId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "模板名称")
	private String name;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "模板描述")
	private String description;

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "训练类型")
	private Integer type;

	/**
	 * 难
	 */
	@ApiModelProperty(value = "训练难度")
	private Integer difficult;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	/**
	 * 创建用户
	 */
	@ApiModelProperty(value = "创建人名字")
	private String createUser;

	/**
	 * 共享
	 */
	@ApiModelProperty(value = "授权操作类型")
	private Integer share;

	/**
	 * 分享教师
	 */
	@ApiModelProperty(value = "授权操作用户列表")
	private List<String> shareTeachers;

	/**
	 * 场景配置信息
	 */
	@ApiModelProperty(value = "场景编排")
	private SceneConfigRsp sceneConfigInfo;

}
