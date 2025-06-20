package com.cyberpeace.cr.crbase.service.context.evaluation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 评估列表请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@ApiModel
@Getter
@Setter
public class EvaluationListReq implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
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
     * 岗位id
     */
    @ApiModelProperty(value = "角色id")
    private String role_id;

    /**
     * 状态
     */
    @ApiModelProperty(value = "评估状态")
    private Integer status;
}
