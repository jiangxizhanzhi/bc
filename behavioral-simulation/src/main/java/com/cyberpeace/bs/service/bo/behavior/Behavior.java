package com.cyberpeace.bs.service.bo.behavior;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 网络行为
 */
@Data
public class Behavior extends BehaviorSimple implements Serializable {

    /**
     * 名称
     */
    @ApiModelProperty(value = "行为名称")
    private String name;

    /**
     * 标签
     */
    @ApiModelProperty(value = "标签")
    private List<String> tag = Lists.newArrayList();

    /**
     * 访问操作系统，设备操作系统类型
     */
    @ApiModelProperty(value = "设备类型")
    private Integer accessOs;

    /**
     * 网络类型
     */
    @ApiModelProperty(value = "网络模型")
    private Integer networkType;

    /**
     * 步骤数
     */
    @ApiModelProperty(value = "行为步骤")
    private Integer stepCount;

    /**
     * 行为耗时
     */
    @ApiModelProperty(value = "行为耗时（min）")
    private Integer timeCost;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 创建者id
     */
    @ApiModelProperty(value = "创建者id")
    private String creatorId;

    /**
     * 创建者名称
     */
    @ApiModelProperty(value = "创建者名称")
    private String creatorName;

    /**
     * 客户端类型
     */
    @ApiModelProperty(value = "客户端类型")
    private Integer appType;

    /**
     * 是否实体
     */
    @ApiModelProperty(value = "是否实体")
    private Integer isActual;
}
