package com.cyberpeace.situation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class ImportDynamicSchemeReq implements Serializable {

    @ApiModelProperty(value = "文件",required = true)
    private MultipartFile file;

    @ApiModelProperty(value = "场景id",name="scene_id",required = true)
    @NotBlank
    private String scene_id;

    public String getSceneId(){
        return this.scene_id;
    }

}
