package com.cyberpeace.situation.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 端口信息
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class PortInfo implements Serializable {

    /**
     * 端口id
     *
     * @see String
     */
    @ApiModelProperty("端口id")
	private String portId;

    /**
     * 端口名称
     *
     * @see String
     */
    @ApiModelProperty("端口名称")
	private String name;

    /**
     * 端口IP
     *
     * @see String
     */
    @ApiModelProperty("端口IP")
	private String ip;
}
