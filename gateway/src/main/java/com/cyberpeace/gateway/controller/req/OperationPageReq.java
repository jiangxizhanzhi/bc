package com.cyberpeace.gateway.controller.req;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 实操分页请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class OperationPageReq extends PageReqBase {

    /**
     * 类别
     */
    @ApiModelProperty("所属分类查询")
    private String category;

    /**
     * 实践设置
     */
    @ApiModelProperty("所属习题集查询")
    private String practiceSet;

    /**
     * 难度
     */
    @ApiModelProperty("难度查询(0-初级, 1-中级, 2-高级)")
    private Integer difficulty;

    /**
     * 用户id
     */
    @ApiModelProperty("用户编号")
    @NotBlank(message = ErrorCode.USER_INFO_MISSING)
    private String userId;
}
