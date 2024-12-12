package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 执业类别 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class PracticeCategoryBo implements Serializable {

    /**
     * 名称
     *
     * @see String
     */
    @ApiModelProperty("习题分类名称")
    private String name;

    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("习题分类编号")
    private String resourceId;

}
