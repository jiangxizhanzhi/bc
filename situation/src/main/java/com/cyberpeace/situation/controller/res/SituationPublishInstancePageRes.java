package com.cyberpeace.situation.controller.res;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author xuxz
 * @Date 13/12/2022 下午8:55
 */
@Data
public class SituationPublishInstancePageRes implements Serializable {

    @ApiModelProperty(value = "资源id")
    private String resourceId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "类型")
    private String category;

    @ApiModelProperty(value = "大屏状态")
    private String status;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "科目名称")
    private String subjectName;

    @ApiModelProperty(value = "任务id")
    private String taskId;

    @ApiModelProperty(value = "科目id")
    private String subjectId;

    @ApiModelProperty(value = "可视化方案名称")
    private String schemeName;

    @ApiModelProperty(value = "模板id")
    private String templateId;

    @ApiModelProperty(value = "大屏属性ID")
    private String attributeId;

    @ApiModelProperty(value = "是否可编辑")
    private Boolean edit;

    @ApiModelProperty(value = "模板可编辑")
    private Boolean templateEdit;

    @ApiModelProperty(value = "任务状态")
    private String taskStatus;

    @ApiModelProperty(value = "科目状态")
    private String subjectStatus;

    @ApiModelProperty(value = "科目开始时间")
    private String startTime;

    @ApiModelProperty(value = "科目结束时间")
    private String endTime;

}
