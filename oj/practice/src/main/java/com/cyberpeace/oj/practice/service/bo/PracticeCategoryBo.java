package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 练习类别 bo
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class PracticeCategoryBo implements Serializable {

    /**
     * 名称
     */
    @ApiModelProperty("习题分类名称")
    private String name;

    /**
     * 资源id
     */
    @ApiModelProperty("习题分类编号")
    private String resourceId;

}
