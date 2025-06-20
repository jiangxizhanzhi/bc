package com.cyberpeace.cr.tool.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Web工具响应
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `WebToolRsp` 的 Java 类，用于表示 Web 工具库的信息。以下是对其主要特性和功能的简要分析： 
 * ### 类概述 
 * - **包名**：`com.cyberpeace.cr.tool.controller.res` 
 * - **类名**：`WebToolRsp` 
 * - **实现接口**：`Serializable`，表明该类的对象可以序列化。 
 * - **注解**： 
 * - `@ApiModel`：用于 Swagger 文档生成，描述这是一个“前台工具库信息”模型。 
 * - `@Getter` 和 `@Setter`：Lombok 注解，自动生成属性的 getter 和 setter 方法。 
 * ### 属性 
 * - `categoryI18nName`：多个分类的国际化名称，用逗号分隔。 
 * - `coverUrl`：工具库的封面链接。 
 * - `id`：工具库的唯一标识符。 
 * - `name`：工具库的名称。 
 * - `resourceId`：资源的 ID。 
 * - `platforms`：支持的平台列表。 
 * - `introduction`：工具库的介绍。 
 * - `type`：工具库的类型。 
 * - `cracked`：布尔值，表示是否为破解版。 
 * - `language`：工具库支持的语言列表。 
 * - `tag`：工具库的标签列表。 
 * ### 主要功能 
 * - 该类主要用于封装 Web 工具库的相关信息，并通过 Swagger 自动生成相关的 API 文档。 
 * - 类中的属性涵盖了工具库的各种基本信息，包括名称、ID、封面、平台支持等。 
 * ### 总结 
 * 这个类是一个数据传输对象（DTO），用于在客户端和服务端之间传递 Web 工具库的信息。它使用了 Lombok 来简化代码，并通过 Swagger 注解来增强文档生成的功能。 
**/
@ApiModel("前台工具库信息")
@Getter
@Setter
public class WebToolRsp implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
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

    /**
     * 介绍
     */
    @ApiModelProperty("介绍")
    private String introduction;

	@ApiModelProperty("分类")
	private String type;

	@ApiModelProperty("是否破解")
	private Boolean cracked;

	@ApiModelProperty("工具语言")
	private List<String> language;
    @ApiModelProperty("标签")
    private List<String> tag;
}

