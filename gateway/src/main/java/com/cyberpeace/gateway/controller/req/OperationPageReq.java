package com.cyberpeace.gateway.controller.req;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 实操分页请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class OperationPageReq extends PageReqBase {

    /**
     * 类别
     */
    @ApiModelProperty("所属分类查询")
    private String category;

    /**
     * 所属习题集查询
     */
    @ApiModelProperty("所属习题集查询")
    private String practiceSet;

    /**
     * 难度
     */
    @ApiModelProperty("难度查询(0-初级, 1-中级, 2-高级)")
    private Integer difficulty;

    /**
     * 知识点
     */
    @ApiModelProperty("知识点，用逗号隔开多个知识点")
    private List<String> knowledge;

    /**
     * 用户id
     */
    @ApiModelProperty("用户编号")
    @NotBlank(message = ErrorCode.USER_INFO_MISSING)
    private String userId;
}
