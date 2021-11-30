package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.oj.course.constant.LessonConfigType;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.entity.LessonConfig;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;

@Data
@ApiModel("课程目录配置信息")
public class LessonConfigBo implements Serializable {

	@ApiModelProperty("课程编号")
	private String courseId;

	@ApiModelProperty("位置编号")
	private Integer index;

	@ApiModelProperty("是否学习")
	private boolean isLearn;

	@ApiModelProperty("是否必修")
	private boolean isRequired;

	@ApiModelProperty("课程编号")
	private String lessonId;

	@ApiModelProperty("课时类型 0-理论 1-实验")
	private Integer type;

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("节点类型")
	private Integer nodeType;

	@ApiModelProperty("父节点编号")
	private String parentId;

	@ApiModelProperty("资源编号")
	private String resourceId;

	@ApiModelProperty("章节名称")
	private String chapterName;

	@ApiModelProperty("子节点")
	private List<LessonConfigBo> children;

	@ApiModelProperty("目录信息索引 课时1 章节1")
	private Integer typeIndex;

	/**
	 * 获取排序好子类课时信息
	 * @param includeChapter true 包含章节 false
	 * @param self true 是否包含自身 true包含   
	 * @return
	 */
	public List<LessonConfigBo> sortListByChildren(boolean includeChapter, boolean self) {

		List<LessonConfigBo> bos = sortListByChildren(this, includeChapter);
		if (self) {
			bos.add(0, this);
		}
		return bos;

	}

	/**
	 * 获取排序数据
	 * @param lessonConfigBo
	 * @param includeChapter
	 * @return
	 */
	private List<LessonConfigBo> sortListByChildren(LessonConfigBo lessonConfigBo,
			boolean includeChapter) {

		if (CollectionUtils.isEmpty(lessonConfigBo.getChildren())) {
			return Arrays.asList(lessonConfigBo);
		}

		List<LessonConfigBo> bos = lessonConfigBo.children.stream()
				.map(bo -> sortListByChildren(bo, includeChapter)).flatMap(Collection::stream)
				.collect(Collectors.toList());

		List<LessonConfigBo> configBos = bos.stream().filter(bo -> includeChapter ? includeChapter
				: bo.getNodeType()
						.intValue() == LessonConfigType.NODE_LESSON.getCode().intValue()).collect(Collectors.toList());

		return configBos;

	}

}
