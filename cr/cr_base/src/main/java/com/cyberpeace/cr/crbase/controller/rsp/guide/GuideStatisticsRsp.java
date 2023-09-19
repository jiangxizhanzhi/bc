package com.cyberpeace.cr.crbase.controller.rsp.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author myy
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuideStatisticsRsp implements Serializable {


    @ApiModelProperty("导调总数量")
    private Integer totalCount;

    @ApiModelProperty("导调类型")
    List<GuideStatisticsDetailRsp> list;
}
