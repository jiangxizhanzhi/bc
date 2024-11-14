package com.cyberpeace.cr.singletrain.service.bo;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.NameCount;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 单房间列表Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel("单兵训练分页业务对象")
public class SingleRoomListBo extends SingleRoomBo implements Serializable {
    /**
     * 向前7个自然日日期, f: mm-dd
     */
    @ApiModelProperty("向前7个自然日日期, f: mm-dd")
    private List<String> lastSevenDays;

    /**
     * 模板使用次数
     */
    @ApiModelProperty("模板使用次数")
    private List<Integer> templateUseCount;

    /**
     * 单兵训练场景节点统计
     */
    @ApiModelProperty("单兵训练场景节点统计")
    private Integer nodesCountData;

    /**
     * 拓扑中占用的授权节点数量
     */
    @ApiModelProperty(value = "拓扑中占用的授权节点数量")
    private Integer resourceOccupiedNums;

    /**
     * 任务计划
     */// 分页接口不需要, 忽略
    @JsonIgnore
    private JSONObject missionsScheme;

    /**
     * 是否关联 ATT&CK
     */
    @ApiModelProperty("是否关联 ATT&CK")
	private Boolean isAttackRelated;

    /**
     * 关联 ATT&CK 计数
     */
    @ApiModelProperty(value = "关联 ATT&CK 计数")
    private List<NameCount> attackCount;

    /**
     * 是否关联 ATT&CK ICS
     */
    @ApiModelProperty("是否关联 ATT&CK ICS")
    private Boolean isIcsRelated;

    /**
     * 关联 ATT&CK ICS 计数
     */
    @ApiModelProperty(value = "关联 ATT&CK ICS 计数")
    private List<NameCount> icsCount;

    /**
     * 是否关联 Engage
     */
    @ApiModelProperty("是否关联 Engage")
	private Boolean isEngageRelated;

    /**
     * 关联 Engage 计数
     */
    @ApiModelProperty(value = "关联 Engage 计数")
    private List<NameCount> engageCount;
}
