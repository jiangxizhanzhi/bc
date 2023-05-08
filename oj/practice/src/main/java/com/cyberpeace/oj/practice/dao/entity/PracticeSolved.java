package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;
import lombok.Getter;

@Data
@TableName("oj_practice_solved")
public class PracticeSolved extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String submitUserId;

	private String submitAnswer;

	private Double score;

	private Double totalScore;

	private Boolean solved;

	private String practiceId;

	private Integer practiceType;

	private Boolean collection;

	private Boolean isPaper = false;

	public enum SolvedStatus implements GenericEnum {
		TRIED(0, "尝试过"),
		SOLVED(1, "已解决"),
		NOT_START(2,"未开始");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		SolvedStatus(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

}
