package com.cyberpeace.cr.tool.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Web工具列表请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see PageReqBase
 * @since 1.0.0
 */
@Data
@ApiModel
public class WebToolListReq extends PageReqBase {

    /**
     * 类别
     *
     * @see String
     */
    @ApiModelProperty("分类")
    private String category;

    /**
     * 平台
     *
     * @see String
     */
    @ApiModelProperty("平台")
    private String platforms;

    /**
     * 破获
     *
     * @see String
     */
    @ApiModelProperty("授权")
    private String cracked;
}
