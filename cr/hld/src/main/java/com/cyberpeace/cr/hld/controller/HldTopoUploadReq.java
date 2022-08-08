package com.cyberpeace.cr.hld.controller;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class HldTopoUploadReq implements Serializable {

    @ApiModelProperty("文件")
    private MultipartFile file;

    @ApiModelProperty("hld拓扑id ")
    @NotBlank
    private String roomId;
}
