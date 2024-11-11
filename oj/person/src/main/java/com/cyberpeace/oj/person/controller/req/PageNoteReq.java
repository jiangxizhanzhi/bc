package com.cyberpeace.oj.person.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页注意请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class PageNoteReq extends PageReqBase {

	/**
	 * 业务类型
	 */
	@ApiModelProperty("业务类型")
	private Integer businessType;

	/**
	 * 练习类型
	 */
	@ApiModelProperty("题型")
	private Integer practiceType;
}
