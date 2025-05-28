package com.cyberpeace.space.service.context.frame;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Space Action 脱机工具
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceActionOfflineTool {
    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "简述")
    private String introduction;
}
