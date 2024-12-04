package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 实践实操文件博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class PracticeOperationFileBo implements Serializable {

    /**
     * 文件名称
     *
     * @see String
     */
    @ApiModelProperty("文件名称")
    private String fileName;

    /**
     * 文件 URL
     *
     * @see String
     */
    @ApiModelProperty("文件地址")
    private String fileUrl;

}
