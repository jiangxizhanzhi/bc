package com.cyberpeace.cr.redvsblue.controller.req;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * RB时间管理请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class RbTimeManagementReq implements Serializable {

    /**
     * 房间id
     *
     * @see String
     */
    @ApiModelProperty(required = true, value = "房间id")
	@NotNull
	private String roomId;

    /**
     * 天文时间
     *
     * @see Date
     */
    @ApiModelProperty(value = "天文时间")
	private Date astronomicalTime;

    /**
     * 战斗时间
     *
     * @see Date
     */
    @ApiModelProperty(value = "作战时间")
	private Date combatTime;

    /**
     * 率
     *
     * @see Integer
     */
    @ApiModelProperty(value = "速率")
	private Integer rate;
}