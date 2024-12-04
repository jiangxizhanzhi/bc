package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.cyberpeace.oj.course.dao.entity.LessonConfig;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;

/**
 * 课程配置 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel("课程目录配置信息")
public class LessonConfigBo implements Serializable {

	/**
	 * 课程id
	 *
	 * @see String
	 */
	@ApiModelProperty("课程编号")
	private String courseId;

	/**
	 * 指数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("位置编号")
	private Integer index;

	/**
	 * 是学习
	 */
	@ApiModelProperty("是否学习")
	private boolean isLearn;

	/**
	 * 必修
	 *
	 * @see Integer
	 */
	@ApiModelProperty("是否必修")
	private Integer compulsory;

	/**
	 * 课id
	 *
	 * @see String
	 */
	@ApiModelProperty("课程编号")
	private String lessonId;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课时类型 0-理论 1-实验")
	private Integer type;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("名称")
	private String name;

	/**
	 * 节点类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("节点类型")
	private Integer nodeType;

	/**
	 * 父母id
	 *
	 * @see String
	 */
	@ApiModelProperty("父节点编号")
	private String parentId;

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;

	/**
	 * 章名称
	 *
	 * @see String
	 */
	@ApiModelProperty("章节名称")
	private String chapterName;

	/**
	 * 子级
	 *
	 * @see List<LessonConfigBo>
	 */
	@ApiModelProperty("子节点")
	private List<LessonConfigBo> children;

	/**
	 * 类型索引
	 *
	 * @see String
	 */
	@ApiModelProperty("目录信息索引 课时1 章节1")
	private String typeIndex;

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
