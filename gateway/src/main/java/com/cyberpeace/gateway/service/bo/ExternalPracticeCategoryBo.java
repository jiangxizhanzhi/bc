package com.cyberpeace.gateway.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 外部执业类 bo
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
@ApiModel
public class ExternalPracticeCategoryBo implements Serializable {

     /**
      * 资源id
      */
     @ApiModelProperty("分类编号")
     private String resourceId;

     /**
      * 名称
      */
     @ApiModelProperty("分类名称")
     private String name;

}
