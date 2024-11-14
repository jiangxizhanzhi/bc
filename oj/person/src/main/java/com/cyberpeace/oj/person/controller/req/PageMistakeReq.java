package com.cyberpeace.oj.person.controller.req;


import com.cyberpeace.common.model.PageReqBase;
import lombok.Data;

/**
 * 分页错误请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class PageMistakeReq extends PageReqBase {
    /**
     * 按错误排序
     */
    private Integer sortByIncorrect = 0;
    /**
     * 按正确排序
     */
    private Integer sortByCorrect = 0;

}
