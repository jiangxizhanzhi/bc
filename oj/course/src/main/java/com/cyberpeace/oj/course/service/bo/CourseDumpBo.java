package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.entity.LessonConfig;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CourseDumpBo {

    @ApiModelProperty("课时的根节点")
    private String rootParentId;

    @ApiModelProperty("导出的课程目录")
    private String exportCoursePath;

    @ApiModelProperty("导出的课程镜像目录")
    private String imagePath;

    @ApiModelProperty("课时所有目录")
    private List<LessonConfig> lessonConfig;

    @ApiModelProperty("所有课时")
    private List<CourseLesson> courseLessons;

    @ApiModelProperty("课程")
    private Course course;

    @ApiModelProperty("是否导出拓扑")
    private Boolean isDumpTopo;

    @ApiModelProperty("场景编号")
    private String sceneNumber;

    @ApiModelProperty("是否导出镜像")
    private Boolean disableImageExport;

}
