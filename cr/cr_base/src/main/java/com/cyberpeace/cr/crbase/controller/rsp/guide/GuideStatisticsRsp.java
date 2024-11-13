package com.cyberpeace.cr.crbase.controller.rsp.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 导调统计学响应
 *
 * @author myy@date 2024/11/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuideStatisticsRsp implements Serializable {


    /**
     * 总计数
     */
    @ApiModelProperty("导调总数量")
    private Integer totalCount;

    /**
     * 列表
     */
    @ApiModelProperty("导调类型")
    private List<GuideStatisticsDetailRsp> list;
}
