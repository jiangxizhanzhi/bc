package com.cyberpeace.cr.singletrain.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 单训练模板更新请求体相对
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@ApiModel("单兵训练模板更新")
public class SingleTrainTemplateUpdateReqRel extends SingleTrainTemplateReqRel implements Serializable {

    /**
     * 资源id
     */
    @ApiModelProperty("模板业务id")
    private String resourceId;

}
