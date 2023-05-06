package com.cyberpeace.bs.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 带类型的分页请求
 * 分页请求体，包含用于过滤的类型字段
 *
 * @author SN
 * @date 2023/05/06
 */
@ApiModel
public class PageReqWithType extends PageReqBase {

    /**
     * 类型
     */
    @ApiModelProperty(value = "用户类型")
    @Getter
    @Setter
    private String type;

    /**
     * 操作系统
     */
    @ApiModelProperty(value = "操作系统类型")
    @Getter
    @Setter
    private Integer access_os;
}
