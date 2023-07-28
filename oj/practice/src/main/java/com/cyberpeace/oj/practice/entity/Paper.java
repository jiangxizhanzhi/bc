package com.cyberpeace.oj.practice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.AuthTable;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("oj_paper")
public class Paper extends AuthTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String name;

	private String description;

	private String logo;

	private Integer practiceCount;

	private Double totalScore;

	@TableField("public")
	private Boolean isPublic;

	private Integer type = PaperType.LIB.getCode();

	public enum PaperType implements GenericEnum {

		LIB(1, "试卷库"),

		EXAM(2, "考试"),

		LESSON(3, "课后练习");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		PaperType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

}
