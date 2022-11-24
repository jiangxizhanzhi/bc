package com.cyberpeace.situation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author xuxz
 * @Date 16/11/2022 下午3:09
 */
@Data
public class ComponentsDataReq implements Serializable {

    @ApiModelProperty("数据源配置")
    private String dataSourceComponent;

    @ApiModelProperty("维度")
    private String dimension;

    @ApiModelProperty("度量")
    private String measure;

    @ApiModelProperty("分组")
    private String groups;

    @ApiModelProperty("自定义参数列表")
    private String customParams;
}
