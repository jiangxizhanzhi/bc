package com.cyberpeace.scene.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public  class BaseSceneElementPortBO {
    @ApiModelProperty(value = "服务端id")
    private String eid;
    @ApiModelProperty(value = "端口id")
    private String id;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "类型")
    private Integer type;
    @ApiModelProperty(value = "mac地址")
    private String macAddr;
    @ApiModelProperty(value = "ip地址")
    private String ip;
    @ApiModelProperty(value = "子网掩码")
    private String netmask;
    @ApiModelProperty(value = "网关")
    private String gateway;
    @ApiModelProperty(value = "是否开启dhcp")
    private Boolean dhcp;
    @ApiModelProperty(value = "dns")
    private String dns;
    @ApiModelProperty(value = "上行带宽")
    private Integer egress;
    @ApiModelProperty(value = "下行带宽")
    private Integer ingress;
    @ApiModelProperty(value = "延迟")
    private Integer delay;
    @ApiModelProperty(value = "丢包率")
    private Integer loss;
    @ApiModelProperty(value = "【即将废弃】接入方式")
    private List<AccessMode> accessModes;
    @ApiModelProperty(value = "端口顺序")
    private Integer order;
    @ApiModelProperty(value = "是否辅助端口")
    private Boolean isAuxiliary;

    @JsonIgnore
    private BaseSceneElementBO element;



}
