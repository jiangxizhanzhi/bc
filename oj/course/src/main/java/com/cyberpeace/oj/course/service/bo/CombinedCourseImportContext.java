package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 合并课程导入上下文
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class CombinedCourseImportContext implements Serializable {

    /**
     * 路径
     */
    @ApiModelProperty("课时地址绝对路径")
    private String path;

    /**
     * 课程名称
     */
    @ApiModelProperty("课程名称")
    @NotBlank
    private String courseName;

    /**
     * 课程数量
     */
    @ApiModelProperty("课程编号")
    @NotBlank
    private String courseNum;

    /**
     * 类别名称
     */
    @ApiModelProperty("课程方向")
    @NotBlank
    private String categoryName;

    /**
     * 章名称
     */
    @ApiModelProperty("课程章节")
    @NotBlank
    private String chapterName;

    /**
     * 课名称
     */
    @ApiModelProperty("课时名称")
    @NotBlank
    private String lessonName;

    /**
     * 组织课程名称
     */
    @ApiModelProperty("原课程名称")
    private String orgCourseName;

    /**
     * 组织章名称
     */
    @ApiModelProperty("原课程章节")
    private String orgChapterName;

    /**
     * 拓扑id
     */
    @ApiModelProperty("场景编号")
    private String topoId;

}
