package com.cyberpeace.situation.controller.req;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author xuxz
 * @Date 8/12/2022 上午10:22
 */
@Data
public class ColorThemePageReq extends PageReqBase implements Serializable {

    @ApiModelProperty("名称")
    private String name;
}
