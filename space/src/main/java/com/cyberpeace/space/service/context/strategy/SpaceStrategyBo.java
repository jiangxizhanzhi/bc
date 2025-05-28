package com.cyberpeace.space.service.context.strategy;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 空间策略Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceStrategyBo {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "")
	private String name;

	/**
	 * 用户id
	 *
	 * @see String
	 */
	private String userId;

	/**
	 * 用户
	 *
	 * @see String
	 */
	private String user;

	/**
	 * 用户头像
	 *
	 * @see String
	 */
	private String userAvatar;

	/**
	 * 内容
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "策略内容")
	private String content;

	/**
	 * 步骤
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "操作步骤")
	private String step;

	/**
	 * 例
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "配置举例")
	private String example;

	/**
	 * 脚本
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "执行脚本")
	private String script;

	/**
	 * 脚本类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "脚本类型，常量type=44")
	private Integer scriptType;

	/**
	 * 框架id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "框架id")
	private String frameId;

	/**
	 * 视图
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "浏览次数")
	private Integer view;

	/**
	 * 创建时间
	 *
	 * @see Date
	 */
	private Date createTime;

	/**
	 * 更新时间
	 *
	 * @see Date
	 */
	private Date updateTime;

	/**
	 * 方法
	 *
	 * @see String
	 */
	@ApiModelProperty("策略执行成功判定方法")
	private String method;

	/**
	 * 路线
	 *
	 * @see String
	 */
	private String route;

	/**
	 * 建议
	 *
	 * @see String
	 */
	@ApiModelProperty("处置建议")
	private String suggest;

	/**
	 * 轨距
	 *
	 * @see String
	 */
	@ApiModelProperty("策略依据")
	private String gauge;
	/**
	 * 内置
	 *
	 * @see Boolean
	 */
	private Boolean builtin;
}
