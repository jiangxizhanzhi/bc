package com.cyberpeace.cr.evaluation.dao.entity.rb;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
@TableName(value = "cr_eval_score")
public class CrEvalScore {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String roomId;

	private String userId;

	private Integer trainingScore;

	private String trainingPercentage;

	private String skillScore;

	private String skillPercentage;

	private String comment;

	private Integer finalScore;

	private String evalTemplateId;

	private Integer id;

	private Date createTime;

	private Date updateTime;

	public enum ScoreValue {

		MIN_PERCENTAGE(10, "评分最低占比"), MAX_PERCENTAGE(90, "评分最高占比"), SUM_OF_PERCENTAGE_INT(100,
				"评分占比之和"), ABILITY_SCORE_NUM(5, "能力评分数量"), MIN_ABILITY_SCORE_VALUE(0,
						"最先能力评分取值"), MAX_ABILITY_SCORE_VALUE(5,
								"最大能力评分取值"), MAX_COMMENT_LEN(200, "评语最大长度");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		private ScoreValue(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}
}
