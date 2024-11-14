package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估房间int双响应
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EvalRoomIntPairRsp {
	/**
	 * ID
	 */
	@ApiModelProperty("ID")
    private Integer id;

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
    private String label;
}
