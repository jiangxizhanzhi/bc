package com.cyberpeace.bs.service.bo.monitor;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * 连接数统计
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class ConnectionBo implements Serializable {

    /**
     * 连接总数
     */
    @ApiModelProperty(value = "连接总数")
    private List<Integer> connection = Lists.newArrayList();

    /**
     * 成功建立的连接数
     */
    @ApiModelProperty(value = "建立的连接数")
    private List<Integer> connected = Lists.newArrayList();
}
