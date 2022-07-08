package com.cyberpeace.situation.controller.res;

import com.cyberpeace.situation.controller.req.AttackMessageReq;
import com.cyberpeace.situation.dao.entity.AttackMessage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DynamicStageRes implements Serializable {

    @ApiModelProperty("阶段名称")
    private String name;

    @ApiModelProperty("动效列表")
    private List<AttackMessageReq> attackMessageList;

}
