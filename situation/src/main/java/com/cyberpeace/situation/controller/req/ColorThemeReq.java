package com.cyberpeace.situation.controller.req;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author xuxz
 * @Date 8/12/2022 上午9:15
 */
@Data
public class ColorThemeReq implements Serializable {

    @NotBlank(groups = {Update.class})
    @ApiModelProperty("资源id")
    private String resourceId;

    @NotBlank(groups = {Create.class, Update.class})
    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("颜色内容")
    private String option;

}
