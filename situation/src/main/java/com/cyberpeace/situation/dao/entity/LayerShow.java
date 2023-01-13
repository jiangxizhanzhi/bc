package com.cyberpeace.situation.dao.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author xuxz
 * @Date 13/1/2023 上午11:22
 */
@Data
public class LayerShow implements Serializable {

    @ApiModelProperty("图层index")
    private Integer componentIndex;

    @ApiModelProperty("图层名称")
    private String name;

    private String id;

}
