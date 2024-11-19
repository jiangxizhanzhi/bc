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
 * @version 1.0.0
 * @date 2024/11/14
 * @since 1.0.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CourseDumpBo {

	/**
	 * 课时的根节点
	 *
	 * @see String
	 */
	@ApiModelProperty("课时的根节点")
    private String rootParentId;

	/**
	 * 导出的课程目录
	 *
	 * @see String
	 */
	@ApiModelProperty("导出的课程目录")
    private String exportCoursePath;

	/**
	 * 导出的课程镜像目录
	 *
	 * @see String
	 */
	@ApiModelProperty("导出的课程镜像目录")
    private String imagePath;

	/**
	 * 课时所有目录
	 *
	 * @see List<LessonConfig>
	 */
	@ApiModelProperty("课时所有目录")
    private List<LessonConfig> lessonConfig;

	/**
	 * 所有课时
	 *
	 * @see List<CourseLesson>
	 */
	@ApiModelProperty("所有课时")
    private List<CourseLesson> courseLessons;

	/**
	 * 课程
	 *
	 * @see Course
	 */
	@ApiModelProperty("课程")
    private Course course;

	/**
	 * 是否导出拓扑
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否导出拓扑")
    private Boolean isDumpTopo;

	/**
	 * 场景编号
	 *
	 * @see String
	 */
	@ApiModelProperty("场景编号")
    private String sceneNumber;

	/**
	 * 是否导出镜像
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否导出镜像")
    private Boolean disableImageExport;

}
