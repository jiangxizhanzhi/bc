package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 练习实操文件Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class PracticeOperationFileBo implements Serializable {

    /**
     * 文件名称
     */
    @ApiModelProperty("文件名称")
    private String fileName;

    /**
     * 文件地址
     */
    @ApiModelProperty("文件地址")
    private String fileUrl;

}
