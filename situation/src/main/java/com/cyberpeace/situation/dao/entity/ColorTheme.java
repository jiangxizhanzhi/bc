package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author xuxz
 * @Date 8/12/2022 上午9:04
 */

@Data
@TableName("situation_color_theme")
public class ColorTheme extends BaseTable implements Serializable {

    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    @NotBlank(groups = {Create.class, Update.class})
    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("颜色内容")
    private String option;

    @NotBlank(groups = {Create.class, Update.class})
    @ApiModelProperty("创建人")
    private String createUserId;
}
