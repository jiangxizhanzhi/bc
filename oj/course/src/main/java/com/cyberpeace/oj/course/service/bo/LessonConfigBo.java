package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;

import com.cyberpeace.oj.course.dao.entity.LessonConfig;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
	private Integer compulsory;

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
	private String typeIndex;

	@ApiModelProperty("是否连续")
	private Boolean continuous = false;

	@ApiModelProperty("是否可用")
	private Boolean accessible = true;

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
	
	public void checkAccessibility(Collection<String> submittedIds) {
		List<LessonConfigBo> continuousList = Lists.newArrayList();
		this.collectContinuous(continuousList);
		int i;
		for (i = 0; i < continuousList.size(); i++) {
			if (!submittedIds.contains(continuousList.get(i).getResourceId())) {
				break;
			}
		}
		for (i = i + 2; i < continuousList.size(); i++) {
			continuousList.get(i).setAccessible(false);
		}
	}

	private void collectContinuous(List<LessonConfigBo> res) {
		if (this.continuous) {
			res.add(this);
		}
		if (null != this.children) {
			for (LessonConfigBo child : this.children) {
				child.collectContinuous(res);
			}
		}
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
			return new ArrayList<>(Arrays.asList(lessonConfigBo));
		}

		// 递归获取数据
		List<LessonConfigBo> bos = lessonConfigBo.getChildren().stream().map(bo -> {
			bo.setParentId(lessonConfigBo.getResourceId());
			return sortListByChildren(bo, includeChapter);
		}).flatMap(Collection::stream).collect(Collectors.toList());

		// 将自身数据加入
		bos.add(0, lessonConfigBo);

		// 转换数据
		List<LessonConfigBo> configBos = bos.stream()
				.filter(bo -> includeChapter ? includeChapter
						: bo.getNodeType().intValue() == LessonConfig.NodeType.NODE_LESSON.getCode()
								.intValue())
				.collect(Collectors.toList());

		return configBos;

	}

}
