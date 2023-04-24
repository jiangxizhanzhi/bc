package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.constant.SelectI18nName;

import lombok.Data;
import lombok.Getter;

@Data
@TableName("cr_constants_config")
public class CrConstantsConfig implements SelectI18nName {

	@TableId
	private Integer id;

	private String resourceId;

	private Integer code;

	private Integer type;

	private String cnName;

	private String enName;

	public enum ConstantsType {

		/* 常量项类别**/
		MISSION_CATEGORY(1, "任务分类"),
		DIFFICULTY(2, "训练难度"),
		TRAINING_CATEGORY(3, "训练分类"),
		EVALUATION_CATEGORY(4, "测试靶场分类"),
		ALGORITHM_TYPE(5, "算法分类"),
		COURSE_DIFFICULTY(9, "课程难度"),
		PRACTICE_DIFFICULTY(10, "练习难度"),
		PRACTICE_STATUS(11, "练习状态"),
		PRACTICE_SET_TYPE(12, "习题集类型"),
		COLLECTION_TYPE(13, "收藏类型"),

		CLASS_DIFFICULTY(15, "课时难度"),
		CLASS_TYPE(16, "课时类型"),
		STUDY_TYPE(17, "课时学习类型"),
		COMMENT_TYPE(18, "评论类型"),
		NOTE_TYPE(19, "笔记类型"),
		RESOURCE_STATUS(20, "资源类型"),
		QUESTION_TYPE(21, "题库题型类型"),
		SINGLE_DIFFICULTY(22, "单兵训练难度"),
		TEAM_DIFFICULTY(23, "团队训练难度"),
		RED_BLUE_DIFFICULTY(24, "红蓝对抗难度"),
		COMPETITION_STATUS(25, "赛场状态"),
		COMPETE_TYPE(26, "参赛方式"),
		MATCH_TYPE(27, "赛事类型"),
		MATCH_STATUS(28, "赛事状态"),
		MESSAGE_MODULE_TYPE(31, "留言主题类型"),
		STUDY_STATISTICS(37,"学习统计类型难度"),
		EVAL_STATUS(38, "靶场测评评估状态"),
		WEB_PRACTICE_STATUS(39, "三种练习状态");
		@Getter
		private final Integer code;

		@Getter
		private final String desc;

		ConstantsType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}
