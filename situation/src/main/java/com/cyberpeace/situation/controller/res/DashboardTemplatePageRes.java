package com.cyberpeace.situation.controller.res;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author xuxz
 * @Date 11/11/2022 下午5:51
 */
@Data
public class DashboardTemplatePageRes extends PageReqBase implements Serializable {
    @ApiModelProperty("模板资源id")
    private String resourceId;

    @ApiModelProperty("模板名称")
    private String name;

    @ApiModelProperty("模板类型")
    private String type;

    @ApiModelProperty("模板级别")
    private Integer level;

    @ApiModelProperty("缩略图")
    private String thumbnail;

    @ApiModelProperty("定制类型")
    private Integer customCode;

    @ApiModelProperty("编辑状态")
    private Boolean editStatus;
}
