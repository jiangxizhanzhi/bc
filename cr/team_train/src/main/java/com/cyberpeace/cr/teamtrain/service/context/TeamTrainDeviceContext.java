package com.cyberpeace.cr.teamtrain.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 团队训练装置上下文
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class TeamTrainDeviceContext {
	/**
	 * 自增id
	 */
	@ApiModelProperty(value = "自增id")
    private String id;

	/**
	 * 团队id
	 */
	@ApiModelProperty(value = "团队id")
    private String roomId;

	/**
	 * 主机id
	 */
	@ApiModelProperty(value = "主机id")
    private String deviceId;

	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "用户id")
    private String createUserId;

	/**
	 * 用户姓名
	 */
	@ApiModelProperty(value = "用户姓名")
    private String createUser;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
    private Date createTime;
}
