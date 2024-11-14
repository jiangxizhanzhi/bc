package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

/**
 * 课时试卷评估
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@TableName("oj_lesson_paper_evaluate")
public class LessonPaperEvaluate extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 课时id
	 */
	private String lessonId;

	/**
	 * 得分
	 */
	private Double score;

	/**
	 * 总数得分
	 */
	private Double totalScore;

	/**
	 * 记录
	 */
	private String record;

	/**
	 * 提交用户id
	 */
	private String submitUserId;

	/**
	 * 水平
	 */
	private Integer level;

	/**
	 * 准确性
	 */
	private String accuracy;

	/**
	 * 校正者
	 */
	private String corrector;

}
