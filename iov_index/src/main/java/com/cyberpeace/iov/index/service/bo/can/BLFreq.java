package com.cyberpeace.iov.index.service.bo.can;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BLFreq {

    @ApiModelProperty("blf文件")
    private MultipartFile blf;

}
