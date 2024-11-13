package com.cyberpeace.cr.tool.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Web工具响应
 *
 * @author SN
 * @date 2024/11/08
 */
@ApiModel("前台工具库信息")
@Data
public class WebToolRsp implements Serializable {

    /**
     * 类别i18n名称
     */
    @ApiModelProperty("多个分类i18n名称逗号分割")
    private String categoryI18nName;

    /**
     * 封面 URL
     */
    @ApiModelProperty("封面链接")
    private String coverUrl;

    /**
     * id
     */
    private Integer id;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 资源id
     */
    @ApiModelProperty("ID")
    private String resourceId;

    /**
     * 平台
     */
    @ApiModelProperty("平台名称")
    private List<String> platforms;

}
