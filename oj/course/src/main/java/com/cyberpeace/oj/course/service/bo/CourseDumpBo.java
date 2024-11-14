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

/**
 * 课程导出Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CourseDumpBo {

	/**
	 * 课时的根节点
	 */
	@ApiModelProperty("课时的根节点")
    private String rootParentId;

	/**
	 * 导出的课程目录
	 */
	@ApiModelProperty("导出的课程目录")
    private String exportCoursePath;

	/**
	 * 导出的课程镜像目录
	 */
	@ApiModelProperty("导出的课程镜像目录")
    private String imagePath;

	/**
	 * 课时所有目录
	 */
	@ApiModelProperty("课时所有目录")
    private List<LessonConfig> lessonConfig;

	/**
	 * 所有课时
	 */
	@ApiModelProperty("所有课时")
    private List<CourseLesson> courseLessons;

	/**
	 * 课程
	 */
	@ApiModelProperty("课程")
    private Course course;

	/**
	 * 是否导出拓扑
	 */
	@ApiModelProperty("是否导出拓扑")
    private Boolean isDumpTopo;

	/**
	 * 场景编号
	 */
	@ApiModelProperty("场景编号")
    private String sceneNumber;

	/**
	 * 是否导出镜像
	 */
	@ApiModelProperty("是否导出镜像")
    private Boolean disableImageExport;

}
