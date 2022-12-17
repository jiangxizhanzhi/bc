package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author xuxz
 * @Date 17/12/2022 下午5:04
 */

@Data
@TableName("situation_plot_pixel")
public class SituationPlotPixel extends BaseTable implements Serializable {

    @ApiModelProperty("资源id")
    @TableId(type = IdType.ASSIGN_UUID, value = "resource_id")
    private String resourceId;

    @ApiModelProperty("图标名称")
    private String name;

    @NotBlank(groups = Create.class)
    @ApiModelProperty("图标url")
    private String plotUrl;

    @ApiModelProperty("背景色")
    private String color;

    @ApiModelProperty("创建人")
    private String createUserId;
}
