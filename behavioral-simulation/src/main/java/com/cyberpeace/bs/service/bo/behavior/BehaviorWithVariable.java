package com.cyberpeace.bs.service.bo.behavior;

import com.cyberpeace.bs.service.bo.variable.VariableSimple;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * 带行为变量的行为
 *
 * @author SN
 * @date 2023/05/06
 */
@Data
public class BehaviorWithVariable implements Serializable {

    /**
     * id
     */
    @ApiModelProperty(value = "行为id")
    private String id;

    /**
     * 名称
     */
    @ApiModelProperty(value = "行为名称")
    private String name;

    /**
     * 标签
     */
    @ApiModelProperty(value = "标签")
    private List<String> tag = Lists.newArrayList();

    /**
     * 步数
     */
    @ApiModelProperty(value = "行为步骤")
    private Integer stepCount;

    /**
     * 行为依赖的客户端
     */
    @ApiModelProperty(value = "行为依赖")
    private List<String> application = Lists.newArrayList();

    /**
     * 行为变量
     */
    @ApiModelProperty(value = "行为变量（默认值）")
    private List<VariableSimple> variables = Lists.newArrayList();
}
