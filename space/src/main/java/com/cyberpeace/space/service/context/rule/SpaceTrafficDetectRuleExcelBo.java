package com.cyberpeace.space.service.context.rule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间流量检测统治胜过Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceTrafficDetectRuleExcelBo {

    /**
     * 规则内容
     *
     * @see String
     */
    @ApiModelProperty(value = "规则内容")
    private String ruleContent;

    /**
     * 统治id
     *
     * @see String
     */
    @ApiModelProperty(value = "规则编号")
    private String ruleId;

    /**
     * 规则信息
     *
     * @see String
     */
    @ApiModelProperty(value = "规则信息")
    private String ruleInfo;

    /**
     * 威胁层级
     *
     * @see String
     */
    @ApiModelProperty(value = "威胁等级")
    private String threatLevel;

    /**
     * 工具
     *
     * @see String
     */
    @ApiModelProperty(value = "工具")
    private String tool;
}
