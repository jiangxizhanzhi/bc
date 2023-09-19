package com.cyberpeace.cr.crbase.controller.rsp.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author myy
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuideStatisticsDetailRsp implements Serializable {

    @ApiModelProperty("导调名称")
    private String guideName;

    @ApiModelProperty("导调类型数量")
    private Integer count;

}
