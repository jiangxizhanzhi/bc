package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class EvalIndicatorLoadReq {
    @ApiModelProperty("文件: Json格式")
    private MultipartFile file;
}
