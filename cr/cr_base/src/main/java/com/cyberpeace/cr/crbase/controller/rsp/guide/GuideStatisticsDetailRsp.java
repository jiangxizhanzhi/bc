package com.cyberpeace.cr.crbase.controller.rsp.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 导调统计学详情响应
 *
 * @author myy@date 2024/11/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuideStatisticsDetailRsp implements Serializable {

    /**
     * 导调名称
     */
    @ApiModelProperty("导调名称")
    private String guideName;

    /**
     * 计数
     */
    @ApiModelProperty("导调类型数量")
    private Integer count;

}
