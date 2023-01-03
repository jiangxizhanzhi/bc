package com.cyberpeace.situation.controller.req;

import com.cyberpeace.common.baseinterface.Create;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @Author xuxz
 * @Date 30/12/2022 上午11:57
 */
@Data
public class ComponentLayerReq implements Serializable {

    @ApiModelProperty("资源id")
    private String resourceId;

    @NotBlank(groups = Create.class)
    @ApiModelProperty("组件id")
    private String componentId;

    @ApiModelProperty("模板id")
    private String templateId;

    @NotBlank(groups = Create.class)
    @ApiModelProperty("图层名称")
    private String name;

    @NotNull(groups = Create.class)
    @ApiModelProperty("图层序号")
    private Integer order;

    @ApiModelProperty("组合图层")
    private List<ComponentLayerReq> combination;
}
