package com.cyberpeace.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author xuxz
 */
@Data
@ApiModel
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExtractBase implements Serializable {

    @ApiModelProperty("文件名称")
    private String fileName;

    @ApiModelProperty("文件路径")
    private String filePath;

    @ApiModelProperty("解压文件内容")
    private String content;

    @ApiModelProperty("是否是解压后文件内容 true=是 false=否")
    private Boolean isExtract;
}
