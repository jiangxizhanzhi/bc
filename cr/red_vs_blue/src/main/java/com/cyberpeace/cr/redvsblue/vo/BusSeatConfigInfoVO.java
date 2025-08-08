package com.cyberpeace.cr.redvsblue.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * 席位配置信息VO
 *
 * @author xiaomaiyun
 * @version 1.2.3
 * @date 2025/05/06 10:58:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "席位配置信息VO")
public class BusSeatConfigInfoVO implements Serializable {

    private static final Long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", name = "id")
    private String id;
    /**
     * 父级ID
     */
    @ApiModelProperty(value = "父级ID", name = "parentId")
    private String parentId;
    /**
     * 任务关联ID
     */
    @ApiModelProperty(value = "任务关联ID", name = "taskId")
    @NotBlank(message = "任务关联ID不能为空!")
    private String taskId;
    /**
     * 席位名称
     */
    @ApiModelProperty(value = "席位名称", name = "seatName")
    @NotBlank(message = "席位名称不能为空!")
    private String seatName;
    /**
     * 席位阶段[1：准备阶段、2：实施阶段、3评估阶段]
     */
    @ApiModelProperty(value = "席位阶段[1：准备阶段、2：实施阶段、3评估阶段]", name = "seatProcessType")
    @NotBlank(message = "席位阶段[1：准备阶段、2：实施阶段、3评估阶段]不能为空!")
    private String seatProcessType;
    /**
     * 席位场地[1:内场、2：外场、3：全场]
     */
    @ApiModelProperty(value = "席位场地[1:内场、2：外场、3：全场]", name = "seatSiteType")
    @NotBlank(message = "席位场地[1:内场、2：外场、3：全场]不能为空!")
    private String seatSiteType;
    /**
     * 席位人员ID
     */
    @ApiModelProperty(value = "席位人员ID", name = "seatUserId")
    private String seatUserId;
    /**
     * 席位人员姓名
     */
    @ApiModelProperty(value = "席位人员姓名", name = "seatUserName")
    private String seatUserName;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述", name = "description")
    private String description;

    @ApiModelProperty(value = "子节点", name = "childrens")
    private List<BusSeatConfigInfoVO> childrens;

}
