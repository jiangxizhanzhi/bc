package com.cyberpeace.cr.crbase.controller.req.guide;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 列表导调方案请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@ApiModel
@Data
public class ListGuideSchemeReq extends PageReqBase {

    /**
     * 房间id
     */
    @ApiModelProperty("房间id")
    @NotBlank
    private String roomId;

}
