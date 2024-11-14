package com.cyberpeace.oj.practice.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 试卷更新请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class PaperUpdateReq extends PaperAddReq {

	/**
	 * 试卷编号
	 */
	@ApiModelProperty("试卷编号")
    private String resourceId;

}
