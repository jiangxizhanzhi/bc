package com.cyberpeace.oj.practice.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Web理论列表请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see AdminTheoryListReq
 * @since 1.0.0
 */
@Data
@ApiModel
public class WebTheoryListReq extends AdminTheoryListReq {

    /**
     * 解决
     *
     * @see Integer
     */
    @ApiModelProperty("解决状态")
	private Integer solved;

}
