package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author xuxz
 * @Date 30/12/2022 上午9:37
 */
@Data
public class ComponentLayer extends BaseTable implements Serializable {

    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    @NotBlank(groups = Create.class)
    @ApiModelProperty("组件id")
    private String componentId;

    @NotBlank(groups = Create.class)
    @ApiModelProperty("模板id")
    private String templateId;

    @NotBlank(groups = Create.class)
    @ApiModelProperty("图层名称")
    private String name;

    @NotNull(groups = Create.class)
    @ApiModelProperty("图层序号")
    private Integer order;

    @ApiModelProperty("组合图层")
    private String combination;
}
