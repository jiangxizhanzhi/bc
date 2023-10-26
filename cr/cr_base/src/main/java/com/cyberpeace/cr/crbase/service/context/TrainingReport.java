package com.cyberpeace.cr.crbase.service.context;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.constant.BaseI18n;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

/**
 * 训练报告
 */
@Data
public class TrainingReport {

	@ApiModelProperty(value = "教员姓名")
	private String teacherName = "";

	@ApiModelProperty(value = "场景开启时间")
	private Date sceneStartTime;

	@ApiModelProperty(value = "训练时间")
	private Integer trainingTime = 0;

	@ApiModelProperty(value = "用户列表")
	private List<UserReport> users = Lists.newArrayList();

	public enum TIME implements GenericEnum, BaseI18n {
		/***/
		DAY(1, "天", "day"),
		HOUR(2, "小时", "hour"),
		MINUTE(3, "分钟", "minute"),
		SECOND(4, "秒", "second");

		@Getter
		private final Integer code;

		@Getter
		private final String chinese;

		@Getter
		private final String english;

		@Override
		public String getDesc() {
			return getMsg();
		}

		TIME(Integer code, String chinese, String english) {
			this.code = code;
			this.chinese = chinese;
			this.english = english;
		}
	}

	public enum TemplateI18n implements BaseI18n {
		/***/
		TRAINEE("trainee", "训练教员", "Trainee"),
		TRAIN_DURATION("train_duration", "训练时长", "Train Time"),
		TRAIN_TIME("train_time", "训练时间", "Train Time"),
		TEAM_STAT("team_stat", "团队数据", "Team Statistics"),
		PERSON_STAT("person_stat", "人员统计", "Personnel Statistics"),
		PERSON("person", "人员", "Personnel"),
		ROLE("role", "角色", "Role"),
		SCORE("score", "得分", "Score"),
		TASK_COMPLETED("task_completed", "任务完成", "Task Completed"),
		REMARK("remark", "备注", "Remark"),
		PERSON_SCORE("person_score", "人员得分", "Personnel Score"),
		TASK_STAT("task_stat", "任务统计", "Task Statistics"),
		TASK_CATEGORY("task_category", "任务分类", "Task Category"),
		PCS_TASK("pcs_task", "项任务", "Task"),
		COMP_EVAL("comp_eval", "综合评价", "Comprehensive Evaluation"),
		FEN("fen", "分", "Score"),
		TOTAL_SCORE("total_score", "总分", "Total Score"),
		TRAIN_SCORE("train_score", "训练得分", "Train Score"),
		TRAIN_STAT("train_stat", "训练统计", "Train Statistics"),
		SKILL_SCORE("skill_score", "技能评分", "Skill Score"),
		OBJ_QUESTION("obj_question", "客观题", "Objective Question"),
		SUBJ_QUESTION("subj_question", "主观题", "Subjective Question"),
		ABILITY_SCORE("ability_score", "能力评分", "Ability Score"),
		PRO_ABILITY("pro_ability", "专业能力", "Professional Ability"),
		TEAM_COLL("team_coll", "团队协作", "Team Collaboration"),
		EXEC_FORCE("exec_force", "执行力", "Executive Force"),
		LEADERSHIP("leadership", "领导力", "Leadership"),
		DYNAMIC_EVENT("dynamic_event", "动态事件", "Dynamic Events"),
		TRAIN_RECORD("train_record", "训练记录", "Train Records"),
		TASK_DESC("task_desc", "任务描述", "Task Description"),
		TASK_RECORD("task_record", "任务记录", "Task Record"),
		COMPLETED("completed", "已完成", "Completed"),
		ONGOING("ongoing", "进行中", "Ongoing"),
		NOT_RELEASED("not_released", "未下发", "Not Issued"),
		NOT_SUBMITTED("not_submitted", "未提交", "Not Submitted"),
		NOT_FINISHED("not_finished", "未完成", "Unfinished"),
		RELEASE("release", "下发", "Issue"),
		SUBMIT("submit", "上报", "Submit"),
		SUCCESS("success", "成功", "Successful"),
		FAIL("fail", "失败", "Failed"),
		REJECT("reject", "驳回", "Reject"),
		WAIT("wait", "等待", "Wait"),
		TEST_PROCESS("test_process", "测试过程", "Test Process"),
		TEST_MISSION("test_mission", "测试任务", "Test Mission"),
		TEST_NAME("test_name", "测试名称", "Test Name"),
		TEST_TYPE("test_type", "测试类型", "Test Type"),
		RECORDING("recording", "是否录屏", "Recording"),
		TEST_DESCRIPTION("test_description", "测试描述", "Test Description"),
		NETWORK_TOPO("network_topo", "网络拓扑", "Network Topology"),
		TEST_STEP("test_step", "测试步骤", "Test Step"),
		STEP("step", "步骤", "Step"),
		EVALUATION_PROCESS("evaluation_process", "评估过程", "Evaluation Process"),
		EVALUATION_MISSION("evaluation_mission", "评估任务","Evaluation Mission"),
		EVALUATION_NAME("evaluation_name", "评估名称","Evaluation Name"),
		EVALUATION_TIME("evaluation_time", "评估时间","Evaluation Time"),
		EVALUATION_DESCRIPTION("evaluation_description", "评估描述","Evaluation Description"),
		INDEX_SYSTEM("index_system", "指标体系", "Index System"),
		VARIABLE_TERM_ALGORITHM("variable_term_algorithm", "变量项算法", "Variable Term Algorithm"),
		DETECTION_ALGORITHMID("detection_algorithmId", "模型匹配算法", "Model Matching Algorithm"),
		FORMAT_ALGORITHM("format_algorithm", "格式化算法", "Format Algorithm"),
		SCORE_ALGORITHM("score_algorithm", "评分算法", "Score Algorithm"),
		EVALUATION_DATA_SOURCE("evaluation_data_source", "评估数据源", "Evaluation Data Source"),
		TEST_INSTANCES("test_instances", "测试实例", "Test Instances"),
		EVALUATION_STAGE("evaluation_stage", "评估阶段", "Evaluation Stage"),
		SCENE_START("scene_start", "场景启动", "Scene Start"),
		TO("to", "至", "To"),
		SCENE_END("scene_end", "场景结束", "Scene End"),
		EVALUATION_RESULT("evaluation_result", "评估结果", "Evaluation Result"),
		DETAILED_DATA("detailed_data", "详细数据", "Detailed Data"),
		LOG_EVENTS("log_events", "日志事件", "Log Events"),
		HARD_DISK_USAGE("hard_disk_usage", "硬盘使用率", "Hard Disk Usage");

		private final String name;

		@Getter
		private final String chinese;

		@Getter
		private final String english;

		TemplateI18n(String name,String chinese, String english) {
			this.name = name;
			this.chinese = chinese;
			this.english = english;
		}

		public static JSONObject getTemplateI18n() {
			JSONObject jsonObject = new JSONObject();

			for (TemplateI18n enumData : TemplateI18n.values()) {
				jsonObject.put(enumData.name, enumData.getMsg());
			}

			return jsonObject;
		}
	}
}
