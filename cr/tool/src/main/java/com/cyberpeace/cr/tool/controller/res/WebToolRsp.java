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
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@ApiModel("前台工具库信息")
@Data
public class WebToolRsp implements Serializable {

    /**
     * 类别i18n名称
     *
     * @see String
     */
    @ApiModelProperty("多个分类i18n名称逗号分割")
    private String categoryI18nName;

    /**
     * 封面 URL
     *
     * @see String
     */
    @ApiModelProperty("封面链接")
    private String coverUrl;

    /**
     * id
     *
     * @see Integer
     */
    private Integer id;

    /**
     * 名称
     *
     * @see String
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("ID")
    private String resourceId;

    /**
     * 平台
     *
     * @see List<String>
     */
    @ApiModelProperty("平台名称")
    private List<String> platforms;

}
