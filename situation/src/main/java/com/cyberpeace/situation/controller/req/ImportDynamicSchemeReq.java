package com.cyberpeace.situation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class ImportDynamicSchemeReq implements Serializable {

    @ApiModelProperty(value = "文件",required = true)
    private MultipartFile file;

    @ApiModelProperty(value = "场景id",required = true)
    private String sceneId;

}
