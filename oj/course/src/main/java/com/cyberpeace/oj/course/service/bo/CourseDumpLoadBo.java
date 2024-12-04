package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * 课程 dump load bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class CourseDumpLoadBo implements Serializable {

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 描述
	 *
	 * @see String
	 */
	private String description;

	/**
	 * 类别名称
	 *
	 * @see String
	 */
	private String categoryName;

	/**
	 * 图标
	 *
	 * @see String
	 */
	private String logo;

	/**
	 * 大纲
	 *
	 * @see String
	 */
	private String outline;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	private Integer difficulty;

	/**
	 * 创造用户id
	 *
	 * @see String
	 */
	private String createUserId;

	/**
	 * 修改用户id
	 *
	 * @see String
	 */
	private String modifyUserId;

	/**
	 * 是否公开
	 *
	 * @see Boolean
	 */
	private Boolean isPublic;

	/**
	 * 课程类型
	 *
	 * @see Integer
	 */
	private Integer courseType;

	/**
	 * 讲师
	 *
	 * @see String
	 */
	private String lecturer;

	/**
	 * 得分
	 *
	 * @see Integer
	 */
	private Integer score;

	/**
	 * 课程数量
	 *
	 * @see String
	 */
	private String courseNum;

	/**
	 * 课
	 *
	 * @see List<LessonDumpLoadBo>
	 */
	private List<LessonDumpLoadBo> lesson;

	/**
	 * 课程配置
	 *
	 * @see LessonConfigDumpBo
	 */
	private LessonConfigDumpBo lessonConfig;

	/**
	 * 文件路径图
	 *
	 * @see Map<String, String>
	 */
	private Map<String, String> filePathMap;
}
