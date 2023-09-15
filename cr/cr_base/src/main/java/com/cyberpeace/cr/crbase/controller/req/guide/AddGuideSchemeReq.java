package com.cyberpeace.cr.crbase.controller.req.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class AddGuideSchemeReq implements Serializable {

    @ApiModelProperty("房间id")
    @NotBlank
    private String roomId;

    @ApiModelProperty("训练类型 3:团队 4:红蓝")
    @NotNull
    private Integer businessType;

    @ApiModelProperty("导调方案名称")
    @NotBlank
    private String eventName;

    @ApiModelProperty("类型 1:任务导调 2:手动导调")
    @NotNull
    private Integer type;

    @ApiModelProperty("事件类型 1:攻击 2:防御 3:阻断 4:归属")
    @NotNull
    private Integer eventType;

    @ApiModelProperty("攻防类型")
    private Integer category;

    @ApiModelProperty("攻击源")
    private String source;

    @ApiModelProperty("攻击目标")
    private String target;

    @ApiModelProperty("攻击结果 1:成功 2失败")
    private Integer result;

    @ApiModelProperty("归属角色 4:红队 5蓝队")
    private Integer role;

    @ApiModelProperty("任务id")
    private String missionId;

    @ApiModelProperty("触发条件1:任务提交 2:任务判断成功")
    private Integer triggerCondition;

}
