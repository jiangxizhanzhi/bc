package com.cyberpeace.situation.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author xuxz
 * @Date 16/11/2022 下午5:02
 */
@Data
public class SituationCommonComponentsPageReq extends PageReqBase implements Serializable {

    @ApiModelProperty("组件名称")
    private String name;

    @ApiModelProperty("组件类型")
    private Integer componentType;

    @ApiModelProperty("组件内部类型")
    private Integer type;

}
