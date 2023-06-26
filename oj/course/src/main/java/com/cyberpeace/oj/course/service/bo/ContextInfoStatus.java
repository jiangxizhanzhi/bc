package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ContextInfoStatus implements Serializable {
    @ApiModelProperty("课时编号")
    private String lessonId;
    @ApiModelProperty("课程编号")
    private String courseId;
    @ApiModelProperty("课时类型")
    private Integer lessonType;
    @ApiModelProperty("训练手册")
    private Integer handoutFlg;
    @JsonIgnore
    private CourseLesson lesson;
}
