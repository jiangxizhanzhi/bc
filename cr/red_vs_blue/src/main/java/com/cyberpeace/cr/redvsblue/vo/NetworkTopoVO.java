package com.cyberpeace.cr.redvsblue.vo;

import com.cyberpeace.cr.redvsblue.dao.entity.CrUniteBusinessLink;
import com.cyberpeace.cr.redvsblue.dao.entity.CrUniteBusinessNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 网络攻防任务信息VO
 *
 * @author liuyah
 * @date 2025-06-21 10:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "网络攻防拓扑信息VO")
public class NetworkTopoVO implements Serializable {

    private static final Long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "任务id", name = "id")
    private String task_id;

    @ApiModelProperty(value = "设备信息", name = "nodes")
    private List<CrUniteBusinessNode> nodes;

    @ApiModelProperty(value = "链路信息", name = "links")
    private List<CrUniteBusinessLink> links;



}
