package com.cyberpeace.bs.service.bo.behavior;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 行为实例
 *
 * @author SN
 * @date 2023/05/06
 */
@Data
public class BehaviorInstance extends LcBehavior implements Serializable {

    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "用户模型id")
    private String userModelId;
}
