package com.cyberpeace.scene.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public  class BaseCloudPluginSceneBO {
    @ApiModelProperty(value = "是否开启录屏")
    private Boolean enableRecording;
    @ApiModelProperty(value = "是否开启环境总速率统计")
    private Boolean enableRate;
    @ApiModelProperty(value = "dnsNameservers")
    private List<DnsPool> dnsNameservers;
    private BaseSceneBO srcScene;

}
