package com.cyberpeace.situation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author xuxz
 * @Date 31/1/2023 下午5:38
 */
@Data
public class ImageReq implements Serializable {

    @ApiModelProperty("图片base64")
    @NotNull
    private String base64;
}
