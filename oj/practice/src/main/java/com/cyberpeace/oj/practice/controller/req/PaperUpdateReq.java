package com.cyberpeace.oj.practice.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 纸更新请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PaperAddReq
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class PaperUpdateReq extends PaperAddReq {

    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("试卷编号")
    private String resourceId;

}
