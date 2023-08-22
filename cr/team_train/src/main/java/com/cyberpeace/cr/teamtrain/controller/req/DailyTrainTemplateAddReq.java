package com.cyberpeace.cr.teamtrain.controller.req;

import com.cyberpeace.cr.crbase.service.impl.bo.BasicTemplate;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class DailyTrainTemplateAddReq extends BasicTemplate {
    @ApiModelProperty("红蓝队训练, 1=红;2=蓝;3=日常")
    private Integer trainCategory;

    @ApiModelProperty(value = "角色列表", required = true)
    @NotNull
    private List<CrDailyRoleConfig> roleList;

    private Integer peopleNumber;

    @ApiModelProperty(value = "时间轴配置", required = true)
    @NotNull
    private Object timeLineConfig = "{}";
}
