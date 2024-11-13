package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * 课程 dump load bo
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class CourseDumpLoadBo implements Serializable {

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 类别名称
	 */
	private String categoryName;

	/**
	 * 图标
	 */
	private String logo;

	/**
	 * 大纲
	 */
	private String outline;

	/**
	 * 难度
	 */
	private Integer difficulty;

	/**
	 * 创造用户id
	 */
	private String createUserId;

	/**
	 * 修改用户id
	 */
	private String modifyUserId;

	/**
	 * 是否公开
	 */
	private Boolean isPublic;

	/**
	 * 课程类型
	 */
	private Integer courseType;

	/**
	 * 讲师
	 */
	private String lecturer;

	/**
	 * 得分
	 */
	private Integer score;

	/**
	 * 课程数量
	 */
	private String courseNum;

	/**
	 * 课
	 */
	private List<LessonDumpLoadBo> lesson;

	/**
	 * 课程配置
	 */
	private LessonConfigDumpBo lessonConfig;

	/**
	 * 文件路径图
	 */
	private Map<String, String> filePathMap;
}
