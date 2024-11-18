package com.cyberpeace.cr.singletrain.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 单训练模板更新请求体相对
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see SingleTrainTemplateReqRel
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel("单兵训练模板更新")
public class SingleTrainTemplateUpdateReqRel extends SingleTrainTemplateReqRel implements Serializable {

    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("模板业务id")
    private String resourceId;

}
