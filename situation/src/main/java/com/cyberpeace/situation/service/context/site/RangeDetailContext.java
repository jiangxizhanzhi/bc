package com.cyberpeace.situation.service.context.site;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RangeDetailContext extends RangeContext{

    @ApiModelProperty("场站数量")
    private Integer stationCount = 0;

    @ApiModelProperty("资源数量")
    private Integer resourceCount = 0;

    @ApiModelProperty("区域编号")
    private Integer locationType;

    @ApiModelProperty("执行任务数量")
    private Integer currentTaskCount = 0;

}
