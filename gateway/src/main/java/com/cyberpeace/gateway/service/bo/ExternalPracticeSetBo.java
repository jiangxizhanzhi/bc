package com.cyberpeace.gateway.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 外部实践设置博
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@ApiModel
public class ExternalPracticeSetBo implements Serializable {

    /**
     * 资源id
     */
    @ApiModelProperty("习题集编号")
    private String resourceId;

    /**
     * 名称
     */
    @ApiModelProperty("习题集名称")
    private String name;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private String createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private String updateTime;

}
