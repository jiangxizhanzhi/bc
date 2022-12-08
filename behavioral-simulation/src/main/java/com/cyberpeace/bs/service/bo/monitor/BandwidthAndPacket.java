package com.cyberpeace.bs.service.bo.monitor;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.io.Serializable;
import java.util.List;

@Data
public class BandwidthAndPacket implements Serializable {

    @ApiModelProperty(value = "实时带宽")
    private List<Integer> bandwidth = Lists.newArrayList();

    @ApiModelProperty(value = "报文数")
    private List<Integer> packet = Lists.newArrayList();

}
