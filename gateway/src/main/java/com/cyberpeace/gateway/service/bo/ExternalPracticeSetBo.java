package com.cyberpeace.gateway.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 外部练习设置Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class ExternalPracticeSetBo implements Serializable {

    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("习题集编号")
    private String resourceId;

    /**
     * 名称
     *
     * @see String
     */
    @ApiModelProperty("习题集名称")
    private String name;

    /**
     * 创建时间
     *
     * @see String
     */
    @ApiModelProperty("创建时间")
    private String createTime;

    /**
     * 更新时间
     *
     * @see String
     */
    @ApiModelProperty("更新时间")
    private String updateTime;

}
