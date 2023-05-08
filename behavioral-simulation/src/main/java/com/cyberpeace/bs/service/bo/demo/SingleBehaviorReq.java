package com.cyberpeace.bs.service.bo.demo;

import com.cyberpeace.bs.service.bo.simulator.VariableBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * 测试单个行为用的请求体
 *
 * @author SN
 * @date 2023/05/06
 */
@Data
public class SingleBehaviorReq implements Serializable {

    /**
     * 仪表id
     */
    @ApiModelProperty(value = "仪表id")
    private String simulatorId;

    /**
     * 行为id
     */
    @ApiModelProperty(value = "行为id")
    private String behaviorId;

    /**
     * 变量
     */
    @ApiModelProperty(value = "行为变量")
    private List<VariableBo> variables = Lists.newArrayList();
}
