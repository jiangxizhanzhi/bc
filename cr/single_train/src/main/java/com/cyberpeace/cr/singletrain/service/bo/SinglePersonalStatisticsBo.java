package com.cyberpeace.cr.singletrain.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 单个个人统计 bo
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@ApiModel("单兵训练个人排名统计信息")
public class SinglePersonalStatisticsBo implements Serializable {

    /**
     * 用户id
     */
    @ApiModelProperty("用户resource_id")
    private String userId;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;

    /**
     * 名称
     */
    @ApiModelProperty("用户姓名")
    private String name;

    /**
     * 总分
     */
    @ApiModelProperty("累计得分")
    private Integer totalScore;

}
