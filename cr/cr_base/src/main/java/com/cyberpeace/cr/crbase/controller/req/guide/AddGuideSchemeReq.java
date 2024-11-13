package com.cyberpeace.cr.crbase.controller.req.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 新增指导方案请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class AddGuideSchemeReq implements Serializable {

    /**
     * 房间id
     */
    @ApiModelProperty("房间id")
    @NotBlank
    private String roomId;

    /**
     * 业务类型
     */
    @ApiModelProperty("训练类型 3:团队 4:红蓝")
    @NotNull
    private Integer businessType;

    /**
     * 事件名称
     */
    @ApiModelProperty("导调方案名称")
    @NotBlank
    private String eventName;

    /**
     * 类型
     */
    @ApiModelProperty("类型 1:任务导调 2:手动导调")
    @NotNull
    private Integer type;

    /**
     * 事件类型
     */
    @ApiModelProperty("事件类型 1:攻击 2:防御 3:阻断 4:归属")
    @NotNull
    private Integer eventType;

    /**
     * 类别
     */
    @ApiModelProperty("攻防类型")
    private Integer category;

    /**
     * 源
     */
    @ApiModelProperty("靶标源")
    private String source;

    /**
     * 目标
     */
    @ApiModelProperty("靶标目标")
    private String target;

    /**
     * 结果
     */
    @ApiModelProperty("攻击结果 1:成功 2失败")
    private Integer result;

    /**
     * 岗位
     */
    @ApiModelProperty("归属角色 4:红队 5蓝队")
    private Integer role;

    /**
     * 任务id
     */
    @ApiModelProperty("任务id")
    private String missionId;

    /**
     * 触发条件
     */
    @ApiModelProperty("触发条件1:任务提交 2:任务判断成功")
    private Integer triggerCondition;

}
