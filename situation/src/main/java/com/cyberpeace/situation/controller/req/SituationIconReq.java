package com.cyberpeace.situation.controller.req;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author xuxz
 * @Date 15/12/2022 上午11:03
 */
@Data
public class SituationIconReq extends BaseTable implements Serializable {

    @ApiModelProperty("资源id")
    @TableId(type = IdType.ASSIGN_UUID, value = "resource_id")
    @NotBlank(groups = Update.class)
    private String resourceId;

    @ApiModelProperty("图标名称")
    @NotBlank(groups = Create.class)
    private String name;

    @NotBlank(groups = Create.class)
    @ApiModelProperty("类型")
    private String type;

    @NotBlank(groups = Create.class)
    @ApiModelProperty("图标url")
    private String iconUrl;

    @ApiModelProperty("背景色")
    private String color;
}
