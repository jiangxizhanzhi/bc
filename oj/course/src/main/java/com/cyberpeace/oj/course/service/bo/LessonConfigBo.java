package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("课程目录配置信息")
public class LessonConfigBo implements Serializable {

    @ApiModelProperty("课程编号")
    private String course;

    @ApiModelProperty("位置编号")
    private Integer index;

    @ApiModelProperty("是否学习")
    private boolean isLearn;

    @ApiModelProperty("是否必修")
    private boolean isRequired;

    @ApiModelProperty("课程编号")
    private String lesson;

    @ApiModelProperty("课时类型")
    private Integer lessonType;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("节点类型")
    private Integer nodeType;

    @ApiModelProperty("父节点编号")
    private String parent;

    @ApiModelProperty("资源编号")
    private String resourceId;

    @ApiModelProperty("子节点")
    private List<LessonConfigBo> lessonConfigs;

}
