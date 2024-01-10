package com.cyberpeace.scene.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public abstract class BaseScenePieceBO
        extends BaseSceneNodeBO{
    @ApiModelProperty(value = "碎片类型")
    private String type;
}
