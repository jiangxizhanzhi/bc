package com.cyberpeace.cr.singletrain.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("单兵训练分页业务对象")
public class SingleRoomListBo extends SingleRoomBo implements Serializable {
    @ApiModelProperty("向前7个自然日日期, f: mm-dd")
    private List<String> lastSevenDays;

    @ApiModelProperty("模板使用次数")
    private List<Integer> templateUseCount;

    @ApiModelProperty("单兵训练场景节点统计")
    private Integer nodesCountData;
}
