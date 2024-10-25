package com.cyberpeace.space.service.context.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class IndexScene {

    @ApiModelProperty(value = "人机对抗场景")
    private Integer ai = 0;
    @ApiModelProperty(value = "极客闯关场景")
    private Integer geek = 0;
    @ApiModelProperty(value = "红蓝对抗场景")
    private Integer rvb = 0;
    @ApiModelProperty(value = "模拟推演场景")
    private Integer simulation = 0;
    @ApiModelProperty(value = "")
    private Integer sum = 0;

}
