package com.cyberpeace.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 提取基础
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@ApiModel
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExtractBase implements Serializable {

    /**
     * 文件名称
     */
    @ApiModelProperty("文件名称")
    private String fileName;

    /**
     * 文件路径
     */
    @ApiModelProperty("文件路径")
    private String filePath;

    /**
     * 内容
     */
    @ApiModelProperty("解压文件内容")
    private String content;

    /**
     * 是提取
     */
    @ApiModelProperty("是否是解压后文件内容 true=是 false=否")
    private Boolean isExtract;
}
