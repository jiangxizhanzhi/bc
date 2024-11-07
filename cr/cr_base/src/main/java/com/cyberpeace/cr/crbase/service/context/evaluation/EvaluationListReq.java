package com.cyberpeace.cr.crbase.service.context.evaluation;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估列表请求体
 *
 * @author SN
 * @date 2024/11/07
 */
@ApiModel
@Data
public class EvaluationListReq implements Serializable {

    /**
     * 房间id
     */
    @NotNull
    @ApiModelProperty(value = "训练ID")
    private String room_id;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "学员ID")
    private String user_id;

    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色id")
    private String role_id;

    /**
     * 状态
     */
    @ApiModelProperty(value = "评估状态")
    private Integer status;
}
