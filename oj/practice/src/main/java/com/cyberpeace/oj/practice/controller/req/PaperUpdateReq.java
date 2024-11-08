package com.cyberpeace.oj.practice.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 纸更新请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@ApiModel
public class PaperUpdateReq extends PaperAddReq {

    /**
     * 资源id
     */
    @ApiModelProperty("试卷编号")
    private String resourceId;

}
