package com.cyberpeace.cr.redvsblue.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DownloadFlowFilePathRsp implements Serializable {
    @ApiModelProperty(value = "文件路径")
    private String filePath;
}
