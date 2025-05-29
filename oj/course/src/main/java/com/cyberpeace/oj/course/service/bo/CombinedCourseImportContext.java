package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 合并课程导入上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `CombinedCourseImportContext` 的 Java 类，用于表示合并课程导入的上下文。以下是该类的主要特点和功能的简要分析： 
 * ### 包和引入 
 * - **包声明**：`com.cyberpeace.oj.course.service.bo` 
 * - **引入的库**： 
 * - `io.swagger.annotations.ApiModel` 和 `io.swagger.annotations.ApiModelProperty` 用于生成 API 文档。 
 * - `lombok.Getter` 和 `lombok.Setter` 注解用于自动生成 getter 和 setter 方法。 
 * - `javax.validation.constraints.NotBlank` 用于验证字段是否为空。 
 * - `java.io.Serializable` 接口，表明这个类可以序列化。 
 * ### 类定义 
 * - **类名**：`CombinedCourseImportContext` 
 * - **注解**： 
 * - `@ApiModel`：用于描述这个类在 Swagger 中的模型。 
 * - `@Getter` 和 `@Setter`：由 Lombok 自动生成 getter 和 setter 方法。 
 * - `implements Serializable`：表示这个类支持序列化。 
 * ### 类成员变量 
 * - `path`：课程文件的绝对路径，使用 `@ApiModelProperty` 注解描述。 
 * - `courseName`：课程名称，必须非空。 
 * - `courseNum`：课程编号，必须非空。 
 * - `categoryName`：课程方向，必须非空。 
 * - `chapterName`：课程章节，必须非空。 
 * - `lessonName`：课时名称，必须非空。 
 * - `orgCourseName`：原课程名称。 
 * - `orgChapterName`：原课程章节。 
 * - `topoId`：场景编号。 
 * ### 总结 
 * - 这个类主要用于表示合并课程导入时需要的各种信息。 
 * - 它包含多个字符串类型的字段，其中某些字段（如 `courseName`, `courseNum`, `categoryName`, `chapterName`, `lessonName`）被标记为必须非空。 
 * - 使用了 Swagger 注解来帮助生成 API 文档，并且通过 Lombok 减少了样板代码的编写。 
 * - 支持序列化，可以在需要时将对象转换为字节流进行存储或传输。 
**/
@Getter
@Setter
@ApiModel
public class CombinedCourseImportContext implements Serializable {

    /**
     * 路径
     *
     * @see String
     */
    @ApiModelProperty("课时地址绝对路径")
    private String path;

    /**
     * 课程名称
     *
     * @see String
     */
    @ApiModelProperty("课程名称")
    @NotBlank
    private String courseName;

    /**
     * 课程数量
     *
     * @see String
     */
    @ApiModelProperty("课程编号")
    @NotBlank
    private String courseNum;

    /**
     * 类别名称
     *
     * @see String
     */
    @ApiModelProperty("课程方向")
    @NotBlank
    private String categoryName;

    /**
     * 章名称
     *
     * @see String
     */
    @ApiModelProperty("课程章节")
    @NotBlank
    private String chapterName;

    /**
     * 课名称
     *
     * @see String
     */
    @ApiModelProperty("课时名称")
    @NotBlank
    private String lessonName;

    /**
     * 组织课程名称
     *
     * @see String
     */
    @ApiModelProperty("原课程名称")
    private String orgCourseName;

    /**
     * 组织章名称
     *
     * @see String
     */
    @ApiModelProperty("原课程章节")
    private String orgChapterName;

    /**
     * 拓扑id
     *
     * @see String
     */
    @ApiModelProperty("场景编号")
    private String topoId;

}

