package com.cyberpeace.bs.service.bo.simulator;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 仪表分页请求体
 *
 * @author SN
 * @date 2023/05/08
 */
public class MeterPageReq extends PageReqBase implements Serializable {

    /**
     * 场景id
     */
    @Getter
    @Setter
    @ApiModelProperty(value = "场景id")
    private String scene_id;
}
