package com.cyberpeace.situation.controller.res;

import com.cyberpeace.situation.dao.entity.ComponentLayer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * @Author xuxz
 * @Date 30/12/2022 下午1:52
 */
@Data
public class ComponentLayerRes implements Serializable {

    private String resourceId;

    @ApiModelProperty("组件id")
    private String componentId;

    @ApiModelProperty("模板id")
    private String templateId;

    @ApiModelProperty("图层名称")
    private String name;

    @ApiModelProperty("图层序号")
    private Integer order;

    @ApiModelProperty("组合图层")
    private List<ComponentLayer> componentLayerList;
}
