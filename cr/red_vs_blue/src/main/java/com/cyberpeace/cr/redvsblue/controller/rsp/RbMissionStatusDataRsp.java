package com.cyberpeace.cr.redvsblue.controller.rsp;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.redvsblue.dao.entity.RbAttackDefenseEvent;
import com.cyberpeace.cr.redvsblue.dao.entity.RbAttackDefenseStage;
import com.cyberpeace.cr.redvsblue.dao.entity.RbAttackEvent;
import com.cyberpeace.cr.redvsblue.dao.entity.RbLostHost;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RbMissionStatusDataRsp implements Serializable {


    @ApiModelProperty(value = "红方攻击信息")
    private List<RbMissionStatusGroupDataRsp> redRole;

    @ApiModelProperty(value = "蓝方攻击信息")
    private List<RbMissionStatusGroupDataRsp> blueRole;

}
