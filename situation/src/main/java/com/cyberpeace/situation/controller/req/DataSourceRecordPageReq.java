package com.cyberpeace.situation.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author xuxz
 * @Date 3/11/2022 上午10:30
 */
@Data
public class DataSourceRecordPageReq extends PageReqBase implements Serializable {

    @ApiModelProperty("资源值")
    private String resourceId;

    @ApiModelProperty("数据源名称")
    private String name;

    @ApiModelProperty("数据源类型")
    private String type;

    @ApiModelProperty("数据源连接地址")
    private String connectAddress;

    @ApiModelProperty("数据源连接端口")
    private String port;
}
