package com.cyberpeace.oj.ojbase.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 收集请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel("收藏请求")
public class CollectionReq implements Serializable {

    /**
     * 业务id
     *
     * @see String
     */
    @ApiModelProperty("收藏的资源编号 课程、课时")
    @NotBlank
    private String businessId;

    /**
     * is 集合
     *
     * @see Boolean
     */
    @ApiModelProperty("是否进行收藏 true进行收藏，false取消收藏")
    @NotNull
    private Boolean isCollection;

    /**
     * 业务类型
     *
     * @see Integer
     */
    @ApiModelProperty("资源类型 0=理论题 1=漏洞题(暂时无用) 2=实操题 3=课程 4=课时")
    @NotNull
    private Integer businessType;

}
