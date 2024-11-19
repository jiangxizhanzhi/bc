package com.cyberpeace.gateway.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 外部练习类别Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class ExternalPracticeCategoryBo implements Serializable {

     /**
      * 资源id
      *
      * @see String
      */
     @ApiModelProperty("分类编号")
     private String resourceId;

     /**
      * 名称
      *
      * @see String
      */
     @ApiModelProperty("分类名称")
     private String name;

}
