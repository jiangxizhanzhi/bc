package com.cyberpeace.situation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author xuxz
 * @Date 16/11/2022 下午3:09
 */
@Data
public class ComponentsDataReq implements Serializable {

    @ApiModelProperty("配置资源id")
    private String resourceId;

    @ApiModelProperty("任务id")
    private String missionId;

    @ApiModelProperty("科目id")
    private String courseId;

    @ApiModelProperty("数据源配置")
    private String dataSourceComponent;

    @ApiModelProperty("维度")
    private String dimension;

    @ApiModelProperty("度量")
    private String measure;
}
