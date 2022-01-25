package com.cyberpeace.cr.crbase.service.context;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.model.CommonContext;
import com.google.common.collect.Lists;

import lombok.Data;
import lombok.Getter;

@Data
public class TrainingReport {

	private String teacherName = "";

	private Date sceneStartTime;

	private Integer trainingTime = 0;

	private List<UserReport> users = Lists.newArrayList();

	public enum TIME implements GenericEnum {

		DAY(1, "天", "day"), HOUR(2, "小时", "hour"), MINUTE(3, "分钟", "minute"), SECOND(4, "秒",
				"second");

		@Getter
		private Integer code;

		private String desc;

		private String enDesc;

		@Override
		public String getDesc() {
			String aaa = "222";
			if (StringUtils.equals(CommonContext.getLanguage(), WebConstant.LANGUAGE_EN)) {
				return enDesc;
			} else {
				return desc;
			}
		}

		TIME(Integer code, String desc, String enDesc) {
			this.code = code;
			this.desc = desc;
			this.enDesc = enDesc;
		}
	}

	public enum TemplateI18n  {
		trainee ("训练教员", "Trainee"),
		train_duration ("训练时长", "Train Time"),
		train_time ("训练时间", "Train Time"),
		team_stat ("团队数据", "Team Statistics"),
		person_stat ("人员统计", "Personnel Statistics"),
		person ("人员", "Personnel"),
		role ("角色", "Role"),
		score ("得分", "Score"),
		task_completed ("任务完成", "Task Completed"),
		remark ("备注", "remark"),
		person_score ("人员得分", "Personnel Score"),
		task_stat ("任务统计", "Task statistics"),
		task_category ("任务分类", "Task Category"),
		pcs_task ("项任务", "Task"),
		comp_eval ("综合评价", "Comprehensive Evaluation"),
		fen ("分", "score"),
		total_score ("总分", "Total Score"),
		train_score ("训练得分", "Train Score"),
		skill_score ("技能评分", "Skill score"),
		obj_question ("客观题", "Objective Question"),
		subj_question ("主观题", "Subjective Question"),
		ability_score ("能力评分", "Ability Score"),
		pro_ability ("专业能力", "Professional Ability"),
		team_coll ("团队协作", "Team Collaboration"),
		exec_force ("执行力", "Executive Force"),
		leadership ("领导力", "Leadership"),
		dynamic_event ("动态事件", "Dynamic Events"),
		train_record ("训练记录", "Train Records"),
		task_desc ("任务描述", "Task Description"),
		task_record ("任务记录", "Task Record"),
		completed ("已完成", "Completed"),
		ongoing ("进行中", "Ongoing"),
		not_released ("未下发", "Not Issued");


		private String desc;

		@Getter
		private String enDesc;
		public String getDesc() {
			if (true) {
				return enDesc;
			} else {
				return desc;
			}
		}

		TemplateI18n( String desc, String enDesc) {

			this.desc = desc;
			this.enDesc = enDesc;
		}
	}
}
