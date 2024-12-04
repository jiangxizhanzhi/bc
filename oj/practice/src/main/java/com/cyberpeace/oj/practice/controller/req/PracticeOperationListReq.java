package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.oj.practice.controller.req.external.PracticeTheoryListReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 同理论基础查询条件, 关闭`questionType`参数
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PracticeTheoryListReq
 * @since 1.0.0
 */
@Data
@ApiModel
public class PracticeOperationListReq extends PracticeTheoryListReq {

    /**
     * 问题类型
     *
     * @see Integer
     */
    @ApiModelProperty(hidden = true)
    private final Integer questionType;
}
