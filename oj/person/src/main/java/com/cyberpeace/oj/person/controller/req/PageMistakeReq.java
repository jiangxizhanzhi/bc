package com.cyberpeace.oj.person.controller.req;


import com.cyberpeace.common.model.PageReqBase;

import lombok.Data;

/**
 * 分页错误请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class PageMistakeReq extends PageReqBase {

	/**
	 * 按 Incorrect 排序
	 */
    private Integer sortByIncorrect = 0;

	/**
	 * 按正确排序
	 */
    private Integer sortByCorrect = 0;

}
