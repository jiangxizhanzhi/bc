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
 * 单人房列表 bo
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@ApiModel("单兵训练分页业务对象")
public class SingleRoomListBo extends SingleRoomBo implements Serializable {
    /**
     * 过去 7 天
     */
    @ApiModelProperty("向前7个自然日日期, f: mm-dd")
    private List<String> lastSevenDays;

    /**
     * 模板用计数
     */
    @ApiModelProperty("模板使用次数")
    private List<Integer> templateUseCount;

    /**
     * 节点计数数据
     */
    @ApiModelProperty("单兵训练场景节点统计")
    private Integer nodesCountData;

    /**
     * 资源占用数
     */
    @ApiModelProperty(value = "拓扑中占用的授权节点数量")
    private Integer resourceOccupiedNums;

    /**
     * 任务计划
     */// 分页接口不需要, 忽略
    @JsonIgnore
    private JSONObject missionsScheme;

    /**
     * 与攻击有关
     */
    @ApiModelProperty("是否关联 ATT&CK")
	private Boolean isAttackRelated;

    /**
     * 攻击计数
     */
    @ApiModelProperty(value = "关联 ATT&CK 计数")
    private List<NameCount> attackCount;

    /**
     * 是否与 IC 相关
     */
    @ApiModelProperty("是否关联 ATT&CK ICS")
    private Boolean isIcsRelated;

    /**
     * 集成电路计数
     */
    @ApiModelProperty(value = "关联 ATT&CK ICS 计数")
    private List<NameCount> icsCount;

    /**
     * 与 engage 相关
     */
    @ApiModelProperty("是否关联 Engage")
	private Boolean isEngageRelated;

    /**
     * 聘计数
     */
    @ApiModelProperty(value = "关联 Engage 计数")
    private List<NameCount> engageCount;
}
