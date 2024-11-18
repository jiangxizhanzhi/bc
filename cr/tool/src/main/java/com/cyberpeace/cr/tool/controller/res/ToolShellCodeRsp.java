package com.cyberpeace.cr.tool.controller.res;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 工具壳代号响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class ToolShellCodeRsp {

    /**
     * 作者
     *
     * @see String
     */
    @ApiModelProperty(value = "作者")
	private String author;

    /**
     * 平台
     *
     * @see String
     */
    @ApiModelProperty(value = "平台")
	private String platform;

    /**
     * EDB 验证
     *
     * @see Boolean
     */
    @ApiModelProperty(name = "edb_verified", value = "是否验证")
	private Boolean edbVerified;
}
