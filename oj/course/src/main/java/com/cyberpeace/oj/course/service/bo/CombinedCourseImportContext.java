package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel
public class CombinedCourseImportContext implements Serializable {

    @ApiModelProperty("课时地址绝对路径")
    private String path;

    @ApiModelProperty("课程名称")
    @NotBlank
    private String courseName;

    @ApiModelProperty("课程编号")
    @NotBlank
    private String courseNum;

    @ApiModelProperty("课程方向")
    @NotBlank
    private String categoryName;

    @ApiModelProperty("课程章节")
    @NotBlank
    private String chapterName;

    @ApiModelProperty("课时名称")
    @NotBlank
    private String lessonName;

    @ApiModelProperty("原课程名称")
    private String orgCourseName;

    @ApiModelProperty("原课程章节")
    private String orgChapterName;

    @ApiModelProperty("场景编号")
    private String topoId;

}
