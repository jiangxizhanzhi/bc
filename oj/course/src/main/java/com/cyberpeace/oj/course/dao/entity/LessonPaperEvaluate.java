package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 课时试卷评估
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see BaseTable
 * @since 1.0.0
 */
@Data
@TableName("oj_lesson_paper_evaluate")
public class LessonPaperEvaluate extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 课时id
	 *
	 * @see String
	 */
	private String lessonId;

	/**
	 * 得分
	 *
	 * @see Double
	 */
	private Double score;

	/**
	 * 总数得分
	 *
	 * @see Double
	 */
	private Double totalScore;

	/**
	 * 记录
	 *
	 * @see String
	 */
	private String record;

	/**
	 * 提交用户id
	 *
	 * @see String
	 */
	private String submitUserId;

	/**
	 * 水平
	 *
	 * @see Integer
	 */
	private Integer level;

	/**
	 * 准确性
	 *
	 * @see String
	 */
	private String accuracy;

	/**
	 * 校正者
	 *
	 * @see String
	 */
	private String corrector;

	public enum LevelType implements GenericEnum {
		// 1-优， 2-良， 3-中， 4-差
		LEVEL_1(1, "优"),
		LEVEL_2(2, "良"),
		LEVEL_3(3, "中"),
		LEVEL_4(4, "差");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		LevelType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public static Boolean isPass(Integer level) {
            return level <= LevelType.LEVEL_2.getCode();
        }

	}

}
