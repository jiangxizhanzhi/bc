package com.cyberpeace.cr.singletrain.service.bo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 单模板 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel("单兵训练模板")
public class SingleTemplateBo implements Serializable {

    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("业务模板id")
    private String resourceId;

    /**
     * 事件数据
     *
     * @see EventDataBo
     */
    @ApiModelProperty("基础信息")
    private EventDataBo eventData;

    /**
     * 任务计划
     *
     * @see JSONObject
     */
    @ApiModelProperty("任务方案")
    private JSONObject missionsScheme;

    /**
     * 复制拓扑id
     *
     * @see String
     */
    @ApiModelProperty("拓扑id")
    private String copiedTopoId;

}
