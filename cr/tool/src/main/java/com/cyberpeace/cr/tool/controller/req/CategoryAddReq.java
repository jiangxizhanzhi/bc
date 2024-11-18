package com.cyberpeace.cr.tool.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 类别新增请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class CategoryAddReq implements Serializable {

    /**
     * 中文名称
     *
     * @see String
     */
    @ApiModelProperty("中文名称")
	@NotBlank
	private String cnName;

    /**
     * 英文名称
     *
     * @see String
     */
    @ApiModelProperty("英文名称")
	@NotBlank
	private String enName;
}
