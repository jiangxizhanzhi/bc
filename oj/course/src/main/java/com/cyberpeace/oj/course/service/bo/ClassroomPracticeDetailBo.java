package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomPracticeDetailBo implements Serializable {

	@ApiModelProperty("课时名称")
	private String lessonName;

	@ApiModelProperty("学员名字")
	private String submitUserName;

	@ApiModelProperty("准确率")
	private String accuracy;

	@ApiModelProperty("题目数量")
	private Integer practiceCount;

	@ApiModelProperty("评级")
	private Integer level;

	@ApiModelProperty("课后练习题信息")
	private Map<String, LessonPracticeListBo> practiceList;
}
