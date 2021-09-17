package com.cyberpeace.common.model.evaluation;

import java.io.Serializable;

import lombok.Data;

@Data
public class ToolActionParamModel implements Serializable {

    /**
     * 参数来源--工具地址
     */
    public static int INPUT_TYPE_ADDR = 1;

    /**
     * 参数来源--用户输入
     */
    public static int INPUT_TYPE_USER = 2;

    /**
     * 参数来源--动作响应
     */
    public static int INPUT_TYPE_TOOL = 3;

    /**
     * 参数名
     */
    private String paramName;

    /**
     * 参数说明
     */
    private String paramDesc;

    /**
     * 参数来源定义
     */
    private Integer inputType;

    /**
     * 针对动作响应类型，数据来自于哪个动作
     */
    private String actionId;

    /**
     * 参数在参数列表中的位置
     */
    private Integer index;
}
