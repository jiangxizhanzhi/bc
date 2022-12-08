package com.cyberpeace.bs.service.bo.monitor;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.io.Serializable;
import java.util.List;

@Data
public class ConnectionBo implements Serializable {

    @ApiModelProperty(value = "连接总数")
    private List<Integer> connection = Lists.newArrayList();

    @ApiModelProperty(value = "建立的连接数")
    private List<Integer> connected = Lists.newArrayList();
}
