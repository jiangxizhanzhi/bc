package com.cyberpeace.oj.person.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页笔记请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class PageNoteReq extends PageReqBase {

	/**
	 * 业务类型
	 */
	@ApiModelProperty("业务类型")
	private Integer businessType;

	/**
	 * 题型
	 */
	@ApiModelProperty("题型")
	private Integer practiceType;
}
