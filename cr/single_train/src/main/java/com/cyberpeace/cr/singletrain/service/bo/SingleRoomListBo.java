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
 * @version 1.0.0
 * @date 2024/11/07
 * @see SingleRoomBo
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel("单兵训练分页业务对象")
public class SingleRoomListBo extends SingleRoomBo implements Serializable {
    /**
     * 过去 7 天
     *
     * @see List<String>
     */
    @ApiModelProperty("向前7个自然日日期, f: mm-dd")
    private List<String> lastSevenDays;

    /**
     * 模板用计数
     *
     * @see List<Integer>
     */
    @ApiModelProperty("模板使用次数")
    private List<Integer> templateUseCount;

    /**
     * 节点计数数据
     *
     * @see Integer
     */
    @ApiModelProperty("单兵训练场景节点统计")
    private Integer nodesCountData;

    /**
     * 资源占用数
     *
     * @see Integer
     */
    @ApiModelProperty(value = "拓扑中占用的授权节点数量")
    private Integer resourceOccupiedNums;

    /**
     * 任务计划
     *
     * @see JSONObject
     */ // 分页接口不需要, 忽略
    @JsonIgnore
    private JSONObject missionsScheme;

    /**
     * 与攻击有关
     *
     * @see Boolean
     */
    @ApiModelProperty("是否关联 ATT&CK")
	private Boolean isAttackRelated;

    /**
     * 攻击计数
     *
     * @see List<NameCount>
     */
    @ApiModelProperty(value = "关联 ATT&CK 计数")
    private List<NameCount> attackCount;

    /**
     * 是否与 IC 相关
     *
     * @see Boolean
     */
    @ApiModelProperty("是否关联 ATT&CK ICS")
    private Boolean isIcsRelated;

    /**
     * 集成电路计数
     *
     * @see List<NameCount>
     */
    @ApiModelProperty(value = "关联 ATT&CK ICS 计数")
    private List<NameCount> icsCount;

    /**
     * 与 engage 相关
     *
     * @see Boolean
     */
    @ApiModelProperty("是否关联 Engage")
	private Boolean isEngageRelated;

    /**
     * 聘计数
     *
     * @see List<NameCount>
     */
    @ApiModelProperty(value = "关联 Engage 计数")
    private List<NameCount> engageCount;
}
