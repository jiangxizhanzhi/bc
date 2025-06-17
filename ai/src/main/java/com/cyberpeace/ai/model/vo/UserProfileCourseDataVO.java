package com.cyberpeace.ai.model.vo;

import java.util.List;

import com.cyberpeace.ai.model.vo.course.CourseListDataVO;
import com.cyberpeace.rtks.service.bo.RoleStudyBo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户画像-课程学习数据")
public class UserProfileCourseDataVO {

	@ApiModelProperty("课程列表")
	private List<CourseListDataVO> courseList;

	@ApiModelProperty("完成数据统计")
	private RoleStudyBo studyStatistics;

	@ApiModelProperty("知识点列表")
	private List<PointDataVO> knowledgePoints;
}