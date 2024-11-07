package com.cyberpeace.rtks.service.bo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * RTKS 雷达 bo
 *
 * @author yinqingrong@date 2024/11/07
 */
@Data
@ApiModel("六棱图数据")
public class RtksRadarBo implements Serializable {

    /**
     * 第一树id
     */
    @ApiModelProperty("节点编号")
	private String firstTreeId;

    /**
     * 树名称
     */
    @ApiModelProperty("节点名称")
	private String treeName;

    /**
     * 匹配百分比
     */
    @ApiModelProperty("节点值")
	private Integer matchPercent;
}
