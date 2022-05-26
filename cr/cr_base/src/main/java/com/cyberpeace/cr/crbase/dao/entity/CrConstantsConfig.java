package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.constant.MenuConstant;
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
		COLLECTION_TYPE(13, "收藏类型");

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
