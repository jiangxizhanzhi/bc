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

		/***/
		MISSION_CATEGORY(1, "任务分类"),
		DIFFICULTY(2, "训练难度"),
		TRAINING_CATEGORY(3, "训练分类");

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
