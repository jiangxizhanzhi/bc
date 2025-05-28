package com.cyberpeace.space.service.context.rule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间流量萃取胜过Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceTrafficExtractionExcelBo {

    /**
     * 名称
     *
     * @see String
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 次序
     *
     * @see String
     */
    @ApiModelProperty(value = "顺序")
    private String order;

    /**
     * 内容
     *
     * @see String
     */
    @ApiModelProperty(value = "内容")
    private String content;

    /**
     * 工具
     *
     * @see String
     */
    @ApiModelProperty(value = "工具")
    private String tool;


}
