package com.cyberpeace.space.service.context.tactic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TacticDefenseCategoryBo {
    /**
     * 资源id
     */
    private String resourceId;

    /**
     * 名称
     */
    private String name;

    /**
     * 阶段
     */
    @ApiModelProperty(value = "")
    private Integer stage;

    /**
     * 封面
     */
    private String cover;
}
