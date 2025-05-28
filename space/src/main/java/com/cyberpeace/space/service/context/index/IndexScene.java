package com.cyberpeace.space.service.context.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 编号场景
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class IndexScene {

    /**
     * 人工智能
     *
     * @see Integer
     */
    @ApiModelProperty(value = "人机对抗场景")
    private Integer ai = 0;
    /**
     * 怪胎
     *
     * @see Integer
     */
    @ApiModelProperty(value = "极客闯关场景")
    private Integer geek = 0;
    /**
     * RVB
     *
     * @see Integer
     */
    @ApiModelProperty(value = "红蓝对抗场景")
    private Integer rvb = 0;
    /**
     * 模拟
     *
     * @see Integer
     */
    @ApiModelProperty(value = "模拟推演场景")
    private Integer simulation = 0;
    /**
     * 和
     *
     * @see Integer
     */
    @ApiModelProperty(value = "")
    private Integer sum = 0;

}
