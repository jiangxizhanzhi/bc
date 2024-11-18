package com.cyberpeace.cr.singletrain.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 单训练列表请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see PageReqBase
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel("前台单兵训练分页查询")
public class SingleTrainListReq extends PageReqBase implements Serializable {
    /**
     * 类别
     *
     * @see Integer
     */
    @ApiModelProperty("训练类别")
    private Integer category;

    /**
     * 难
     *
     * @see Integer
     */
    @ApiModelProperty("训练难度")
    private Integer difficult;

    /**
     * with 实例
     *
     * @see Integer
     */
    private Integer withInstance;
}
