package com.cyberpeace.cr.crbase.controller.req.guide;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 导调记录请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@ApiModel
@Data
public class GuideRecordReq extends PageReqBase {

    /**
     * 房间id
     */
    @ApiModelProperty("房间id")
    @NotBlank
    private String roomId;

    /**
     * 业务类型
     */
    @ApiModelProperty("训练类型 3:团队 4:红蓝")
    @NotNull
    private Integer businessType;

}
