package com.cyberpeace.cr.evaluation.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估记录启动请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EvalRecordStartReq implements Serializable {
	/**
	 * 评估id
	 */
	@ApiModelProperty("评估id")
    private String resourceId;
}
