package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.cyberpeace.common.util.BeanUtils;
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
	private String course;

	@ApiModelProperty("位置编号")
	private Integer index;

	@ApiModelProperty("是否学习")
	private boolean isLearn;

	@ApiModelProperty("是否必修")
	private boolean isRequired;

	@ApiModelProperty("课程编号")
	private String lesson;

	@ApiModelProperty("课时类型 0-理论 1-实验")
	private Integer type;

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("节点类型")
	private Integer nodeType;

	@ApiModelProperty("父节点编号")
	private String parent;

	@ApiModelProperty("资源编号")
	private String resourceId;

	@ApiModelProperty("章节名称")
	private String chapterName;

	@ApiModelProperty("子节点")
	private List<LessonConfigBo> lessonConfigs;

	/**
	 * 获取排序好的课时信息
	 * @param configBo
	 * @param includeChapter true 包含章节 false
	 * @return
	 */
	public List<LessonConfigBo> getSortListByTree(LessonConfigBo configBo, Boolean includeChapter) {

		List<LessonConfigBo> configs = configBo.getLessonConfigs();

		if (CollectionUtils.isNotEmpty(configs)) {
			List<LessonConfigBo> bos = configs.stream()
					.map(bo -> getSortListByTree(bo, includeChapter)).flatMap(Collection::stream)
					.collect(Collectors.toList());
			// 将跟节点和子节点加入集合
			configs.addAll(bos);
			configs.set(0, configBo);

			List<LessonConfigBo> configBos = bos.stream()
					.filter(bo -> includeChapter ? includeChapter
							: bo.getNodeType().intValue() == LessonConfig.NODE_LESSON)
					.map(bo -> {
						BeanUtils.copy(bo, LessonConfig.class);
						bo.setLessonConfigs(null);
						return bo;
					}).collect(Collectors.toList());

			return configBos;
		} else {
			return Lists.newArrayList(configBo);
		}
	}

}
