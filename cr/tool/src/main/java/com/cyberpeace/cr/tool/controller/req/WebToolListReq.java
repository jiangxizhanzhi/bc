package com.cyberpeace.cr.tool.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 蹼工具列表请求体
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@ApiModel
public class WebToolListReq extends PageReqBase {

    /**
     * 类别
     */
    @ApiModelProperty("分类")
    private String category;

    /**
     * 平台
     */
    @ApiModelProperty("平台")
    private String platforms;

    /**
     * 破获
     */
    @ApiModelProperty("授权")
    private String cracked;
}
