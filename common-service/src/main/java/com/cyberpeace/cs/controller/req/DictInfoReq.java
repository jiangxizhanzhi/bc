package com.cyberpeace.cs.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel
public class DictInfoReq implements Serializable {

    private Integer id;

    @ApiModelProperty("字段名")
    @NotBlank
    private String key;

    @ApiModelProperty("字段值")
    @NotBlank
    private String itemValue;

    @ApiModelProperty("字段类型，和父级关联")
    @NotBlank
    private String parentKey;

}
