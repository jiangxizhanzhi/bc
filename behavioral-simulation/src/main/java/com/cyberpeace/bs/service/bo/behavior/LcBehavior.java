package com.cyberpeace.bs.service.bo.behavior;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * 远程行为模型
 *
 * @author SN
 * @date 2023/05/06
 */
@Data
public class LcBehavior extends Behavior implements Serializable {

    /**
     * 行为依赖
     */
    @ApiModelProperty(value = "行为依赖")
    private List<String> application = Lists.newArrayList();

    /**
     * 是否实体设备
     */
    @ApiModelProperty(value = "是否实体")
    private Integer isActual;

    /**
     * 客户端类型
     */
    @ApiModelProperty(value = "客户端类型，type=45")
    private Integer appType;

}
