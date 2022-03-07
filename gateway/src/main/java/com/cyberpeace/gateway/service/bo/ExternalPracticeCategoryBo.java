package com.cyberpeace.gateway.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class ExternalPracticeCategoryBo implements Serializable {

     @ApiModelProperty("分类编号")
     private String resourceId;

     @ApiModelProperty("分类名称")
     private String name;

}
