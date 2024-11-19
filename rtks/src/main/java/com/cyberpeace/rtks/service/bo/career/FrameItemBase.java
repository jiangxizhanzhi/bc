package com.cyberpeace.rtks.service.bo.career;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 框架项目基础
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class FrameItemBase implements Serializable {

    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("资源id")
	private String resourceId;

    /**
     * 名称
     *
     * @see String
     */
    @ApiModelProperty("名称")
	private String name;

}
