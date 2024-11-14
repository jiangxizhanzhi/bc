package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估房间双响应
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EvalRoomPairRsp implements Serializable {
	/**
	 * ID
	 */
	@ApiModelProperty("ID")
    private String id;

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
    private String label;
}
