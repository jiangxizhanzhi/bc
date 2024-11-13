package com.cyberpeace.cr.crbase.service.context;

import java.util.Date;
import java.util.List;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.constant.BaseI18n;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

/**
 * 训练报告
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class TrainingReport {

	/**
	 * 教员名称
	 */
	@ApiModelProperty(value = "教员姓名")
	private String teacherName = "";

	/**
	 * 场景开始时间
	 */
	@ApiModelProperty(value = "场景开启时间")
	private Date sceneStartTime;

	/**
	 * 训练时间
	 */
	@ApiModelProperty(value = "训练时间")
	private Integer trainingTime = 0;

	/**
	 * 用户
	 */
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

	/**
	 * 模板 I18N
	 *
	 * @author SN
	 * @date 2024/11/13
	 */
	public enum TemplateI18n implements BaseI18n {
		/**
		 * 训练教员
		 */
		trainee("训练教员", "Trainee"),
		/**
		 * 训练时长
		 */
		train_duration("训练时长", "Train Time"),
		/**
		 * 训练时间
		 */
		train_time("训练时间", "Train Time"),
		/**
		 * 团队数据
		 */
		team_stat("团队数据", "Team Statistics"),
		/**
		 * 人员统计
		 */
		person_stat("人员统计", "Personnel Statistics"),
		/**
		 * 人员
		 */
		person("人员", "Personnel"),
		/**
		 * 岗位
		 */
		role("角色", "Role"),
		/**
		 * 得分
		 */
		score("得分", "Score"),
		/**
		 * 任务已完成
		 */
		task_completed("任务完成", "Task Completed"),
		/**
		 * 备注
		 */
		remark("备注", "Remark"),
		/**
		 * 人员评分
		 */
		person_score("人员得分", "Personnel Score"),
		/**
		 * 任务统计
		 */
		task_stat("任务统计", "Task Statistics"),
		/**
		 * 任务类别
		 */
		task_category("任务分类", "Task Category"),
		/**
		 *  项任务
		 */
		pcs_task("项任务", "Task"),
		/**
		 * 综合评价
		 */
		comp_eval("综合评价", "Comprehensive Evaluation"),
		/**
		 * 分
		 */
		fen("分", "Score"),
		/**
		 * 总分
		 */
		total_score("总分", "Total Score"),
		/**
		 * 训练得分
		 */
		train_score("训练得分", "Train Score"),
		/**
		 * 训练统计
		 */
		train_stat("训练统计", "Train Statistics"),
		/**
		 * 技能分数
		 */
		skill_score("技能评分", "Skill Score"),
		/**
		 * 客观题
		 */
		obj_question("客观题", "Objective Question"),
		/**
		 * 主观题
		 */
		subj_question("主观题", "Subjective Question"),
		/**
		 * 能力分数
		 */
		ability_score("能力评分", "Ability Score"),
		/**
		 * 专业能力
		 */
		pro_ability("专业能力", "Professional Ability"),
		/**
		 * 团队协作
		 */
		team_coll("团队协作", "Team Collaboration"),
		/**
		 * 执行力
		 */
		exec_force("执行力", "Executive Force"),
		/**
		 * 领导力
		 */
		leadership("领导力", "Leadership"),
		/**
		 * 动态事件
		 */
		dynamic_event("动态事件", "Dynamic Events"),
		/**
		 * 训练记录
		 */
		train_record("训练记录", "Train Records"),
		/**
		 * 任务描述
		 */
		task_desc("任务描述", "Task Description"),
		/**
		 * 任务记录
		 */
		task_record("任务记录", "Task Record"),
		/**
		 * 完成
		 */
		completed("已完成", "Completed"),
		/**
		 * 进行中
		 */
		ongoing("进行中", "Ongoing"),
		/**
		 * 未下发
		 */
		not_released("未下发", "Not Issued"),
		/**
		 * 未提交
		 */
		not_submitted("未提交", "Not Submitted"),
		/**
		 * 未完成
		 */
		not_finished("未完成", "Unfinished"),
		/**
		 * 下发
		 */
		release("下发", "Issue"),
		/**
		 * 提交
		 */
		submit("上报", "Submit"),
		/**
		 * 成功
		 */
		success("成功", "Successful"),
		/**
		 * 失败
		 */
		fail("失败", "Failed"),
		/**
		 * 驳回
		 */
		reject("驳回", "Reject"),
		/**
		 * 等待
		 */
		wait("等待", "Wait"),

		/**
		 * 测试过程
		 */
		test_process("测试过程", "Test Process"),
		/**
		 * 测试任务
		 */
		test_mission("测试任务", "Test Mission"),
		/**
		 * 测试名称
		 */
		test_name("测试名称", "Test Name"),
		/**
		 * 测试类型
		 */
		test_type("测试类型", "Test Type"),
		/**
		 * 录音
		 */
		recording("是否录屏", "Recording"),
		/**
		 * 测试描述
		 */
		test_description("测试描述", "Test Description"),
		/**
		 * 网络拓扑
		 */
		network_topo("网络拓扑", "Network Topology"),
		/**
		 * 测试步骤
		 */
		test_step("测试步骤", "Test Step"),
		/**
		 * 步骤
		 */
		step("步骤", "Step"),
		/**
		 * 评估过程
		 */
		evaluation_process("评估过程", "Evaluation Process"),
		/**
		 * 评估任务
		 */
		evaluation_mission("评估任务","Evaluation Mission"),
		/**
		 * 评估名称
		 */
		evaluation_name("评估名称","Evaluation Name"),
		/**
		 * 评估时间
		 */
		evaluation_time("评估时间","Evaluation Time"),
		/**
		 * 评估描述
		 */
		evaluation_description("评估描述","Evaluation Description"),
		/**
		 * 指标体系
		 */
		index_system("指标体系", "Index System"),
		/**
		 * 变量项算法
		 */
		variable_term_algorithm("变量项算法", "Variable Term Algorithm"),
		/**
		 * 模型匹配算法
		 */
		detection_algorithmId("模型匹配算法", "Model Matching Algorithm"),
		/**
		 * 格式化算法
		 */
		format_algorithm("格式化算法", "Format Algorithm"),
		/**
		 * 评分算法
		 */
		score_algorithm("评分算法", "Score Algorithm"),
		/**
		 * 评估数据源
		 */
		evaluation_data_source("评估数据源", "Evaluation Data Source"),
		/**
		 * 测试实例
		 */
		test_instances("测试实例", "Test Instances"),
		/**
		 * 评估阶段
		 */
		evaluation_stage("评估阶段", "Evaluation Stage"),
		/**
		 * 场景开始
		 */
		scene_start("场景启动", "Scene Start"),
		/**
		 * 至
		 */
		to("至", "To"),
		/**
		 * 场景结束
		 */
		scene_end("场景结束", "Scene End"),
		/**
		 * 评估结果
		 */
		evaluation_result("评估结果", "Evaluation Result"),
		/**
		 * 详细数据
		 */
		detailed_data("详细数据", "Detailed Data"),
		/**
		 * 日志事件
		 */
		log_events("日志事件", "Log Events"),
		/**
		 * 硬盘使用情况
		 */
		hard_disk_usage("硬盘使用率", "Hard Disk Usage");

		/**
		 * 中文
		 */
		@Getter
		private final String chinese;

		/**
		 * 英语
		 */
		@Getter
		private final String english;

		TemplateI18n(String chinese, String english) {
			this.chinese = chinese;
			this.english = english;
		}
	}
}
