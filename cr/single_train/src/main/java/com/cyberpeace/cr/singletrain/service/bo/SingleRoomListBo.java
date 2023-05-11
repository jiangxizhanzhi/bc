package com.cyberpeace.cr.singletrain.service.bo;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.NameCount;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("单兵训练分页业务对象")
public class SingleRoomListBo extends SingleRoomBo implements Serializable {
    @ApiModelProperty("向前7个自然日日期, f: mm-dd")
    private List<String> lastSevenDays;

    @ApiModelProperty("模板使用次数")
    private List<Integer> templateUseCount;

    @ApiModelProperty("单兵训练场景节点统计")
    private Integer nodesCountData;

    @ApiModelProperty(value = "拓扑中占用的授权节点数量")
    private Integer resourceOccupiedNums;

    // 分页接口不需要, 忽略
    @JsonIgnore
    private JSONObject missionsScheme;

	@ApiModelProperty("是否关联 ATT&CK")
	private Boolean isAttackRelated;

    @ApiModelProperty(value = "关联 ATT&CK 计数")
    private List<NameCount> attackCount;

	@ApiModelProperty("是否关联 Engage")
	private Boolean isEngageRelated;

    @ApiModelProperty(value = "关联 Engage 计数")
    private List<NameCount> engageCount;
}
