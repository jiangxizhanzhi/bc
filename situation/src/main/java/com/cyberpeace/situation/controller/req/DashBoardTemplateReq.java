package com.cyberpeace.situation.controller.req;

import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.ErrorCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @Author xuxz
 * @Date 11/11/2022 下午3:29
 */
@Data
public class DashBoardTemplateReq implements Serializable {

    @ApiModelProperty("模板资源id")
    @NotBlank(groups = Update.class,
            message = ErrorCode.METHOD_ARG_NOT_VALID)
    private String resourceId;

    @ApiModelProperty("模板名称")
    private String name;

    @ApiModelProperty("模板类型")
    private String type;

    @ApiModelProperty("模板级别")
    private Integer level;

    @ApiModelProperty("缩略图")
    private String thumbnail;

    @ApiModelProperty("画布高度")
    private String height;

    @ApiModelProperty("画布宽度")
    private String width;

    @ApiModelProperty("填充色")
    private String bgColor;

    @ApiModelProperty("刷新频率")
    private Integer refresh;

    @ApiModelProperty("组件配置信息")
    private List<DashboardComponentsReq> componentsSetting;
}
