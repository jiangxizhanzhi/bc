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
import lombok.Getter;
import lombok.Setter;
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
/**
 * 这段代码定义了一个名为 `LessonConfigBo` 的 Java 类，用于表示课程配置信息。以下是对该代码的简要分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.oj.course.service.bo` 
 * - **导入的库**：包括一些常用的 Java 库（如 `java.io.Serializable` 和 `java.util`）以及一些第三方库（如 `io.swagger.annotations` 和 `lombok`）。 
 * ### 类注释 
 * - **作者**：SN 
 * - **版本**：1.0.0 
 * - **日期**：2024年11月8日 
 * - **继承接口**：`Serializable` 
 * ### 类属性 
 * - **courseId**：课程ID 
 * - **index**：位置编号 
 * - **isLearn**：是否学习 
 * - **compulsory**：是否必修 
 * - **lessonId**：课ID 
 * - **type**：课时类型（0-理论，1-实验） 
 * - **name**：名称 
 * - **nodeType**：节点类型 
 * - **parentId**：父节点ID 
 * - **resourceId**：资源ID 
 * - **chapterName**：章名称 
 * - **children**：子节点列表 
 * - **typeIndex**：目录信息索引 
 * ### 方法 
 * - **sortListByChildren**：根据子节点排序列表 
 * - 参数： 
 * - `includeChapter`：是否包含章节 
 * - `self`：是否包含自身 
 * - 返回值：排序后的 `LessonConfigBo` 列表 
 * - **sortListByChildren**（私有方法）：递归获取排序数据 
 * - 参数： 
 * - `lessonConfigBo`：当前 `LessonConfigBo` 对象 
 * - `includeChapter`：是否包含章节 
 * - 返回值：排序后的 `LessonConfigBo` 列表 
 * ### 主要功能 
 * - 该类主要用于表示课程配置信息，并提供了递归排序子节点的方法。 
 * - 通过 `sortListByChildren` 方法可以获取按照特定规则排序的子节点列表。 
 * ### 总结 
 * - 该类是一个数据传输对象（DTO），用于在不同层之间传递课程配置信息。 
 * - 提供了递归排序子节点的功能，便于前端展示或后端处理课程结构。 
 * - 使用了 Lombok 注解简化了 getter 和 setter 的生成。 
**/
@Getter
@Setter
@ApiModel("课程目录配置信息")
public class LessonConfigBo implements Serializable {
		
	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
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

