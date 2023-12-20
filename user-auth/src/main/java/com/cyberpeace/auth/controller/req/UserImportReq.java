package com.cyberpeace.auth.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * @author :   SN
 * @date :   2023/12/20 14:03
 * @desc :
 **/
@Data
@ApiModel
public class UserImportReq {

    @ApiModelProperty("文件")
    private MultipartFile file;
}
