/**
 * 这段代码定义了一个名为 `TrainingReport` 的 Java 类，用于表示训练报告。以下是对其的主要分析： 
 * 1. **类的功能**：该类主要用于存储与训练相关的各种信息，包括教员姓名、场景开始时间、训练时间以及用户列表等。 
 * 2. **属性说明**： 
 * - `teacherName`: 教员的姓名，默认为空字符串。 
 * - `sceneStartTime`: 场景的开始时间。 
 * - `trainingTime`: 训练所花费的时间（以整数表示），默认为0。 
 * - `users`: 包含多个 `UserReport` 对象的列表，用于记录每个用户的详细信息，默认为空列表。 
 * 3. **枚举类型**： 
 * - `TIME`: 定义了时间单位的枚举类，实现了 `GenericEnum` 和 `BaseI18n` 接口，提供了多种时间单位及其对应的中文和英文描述。 
 * - `TemplateI18n`: 提供了一系列国际化支持的常量，包括各种训练报告中的字段名称，如“训练教员”、“训练时长”等，并且每种语言都有对应的中文和英文版本。 
 * 4. **注解**： 
 * - 使用了 Lombok 库的 `@Getter` 和 `@Setter` 注解来自动生成 getter 和 setter 方法，简化了代码编写。 
 * - Swagger 注解 `@ApiModelProperty` 用于描述 API 中的字段信息，方便生成文档或接口测试。 
 * 5. **设计特点**： 
 * - 通过定义内部枚举类 `TIME` 和 `TemplateI18n` 来增强类的功能性和可扩展性，特别是对于多语言的支持非常有用。 
 * - 使用了 Guava 库中的 `Lists.newArrayList()` 方法来初始化用户列表，这是一种常见的集合初始化方式。 
 * 总体而言，这个类结构清晰，功能明确，适合用来构建一个完整的训练报告系统。它不仅包含了基本的数据存储功能，还通过枚举和国际化支持增强了其实用性和灵活性。 
**/
package com.cyberpeace.cr.crbase.service.context;

import java.util.Date;
import java.util.List;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.constant.BaseI18n;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.Getter;

/**
 * 训练报告
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
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

	public enum TemplateI18n implements BaseI18n {
		/***/
		trainee("训练教员", "Trainee"),
		train_duration("训练时长", "Train Time"),
		train_time("训练时间", "Train Time"),
		team_stat("团队数据", "Team Statistics"),
		person_stat("人员统计", "Personnel Statistics"),
		person("人员", "Personnel"),
		role("角色", "Role"),
		score("得分", "Score"),
		task_completed("任务完成", "Task Completed"),
		remark("备注", "Remark"),
		person_score("人员得分", "Personnel Score"),
		task_stat("任务统计", "Task Statistics"),
		task_category("任务分类", "Task Category"),
		pcs_task("项任务", "Task"),
		comp_eval("综合评价", "Comprehensive Evaluation"),
		fen("分", "Score"),
		total_score("总分", "Total Score"),
		train_score("训练得分", "Train Score"),
		train_stat("训练统计", "Train Statistics"),
		skill_score("技能评分", "Skill Score"),
		obj_question("客观题", "Objective Question"),
		subj_question("主观题", "Subjective Question"),
		ability_score("能力评分", "Ability Score"),
		pro_ability("专业能力", "Professional Ability"),
		team_coll("团队协作", "Team Collaboration"),
		exec_force("执行力", "Executive Force"),
		leadership("领导力", "Leadership"),
		dynamic_event("动态事件", "Dynamic Events"),
		train_record("训练记录", "Train Records"),
		task_desc("任务描述", "Task Description"),
		task_record("任务记录", "Task Record"),
		completed("已完成", "Completed"),
		ongoing("进行中", "Ongoing"),
		not_released("未下发", "Not Issued"),
		not_submitted("未提交", "Not Submitted"),
		not_finished("未完成", "Unfinished"),
		release("下发", "Issue"),
		submit("上报", "Submit"),
		success("成功", "Successful"),
		fail("失败", "Failed"),
		reject("驳回", "Reject"),
		wait("等待", "Wait"),

		test_process("测试过程", "Test Process"),
		test_mission("测试任务", "Test Mission"),
		test_name("测试名称", "Test Name"),
		test_type("测试类型", "Test Type"),
		recording("是否录屏", "Recording"),
		test_description("测试描述", "Test Description"),
		network_topo("网络拓扑", "Network Topology"),
		test_step("测试步骤", "Test Step"),
		step("步骤", "Step"),
		evaluation_process("评估过程", "Evaluation Process"),
		evaluation_mission("评估任务","Evaluation Mission"),
		evaluation_name("评估名称","Evaluation Name"),
		evaluation_time("评估时间","Evaluation Time"),
		evaluation_description("评估描述","Evaluation Description"),
		index_system("指标体系", "Index System"),
		variable_term_algorithm("变量项算法", "Variable Term Algorithm"),
		detection_algorithmId("模型匹配算法", "Model Matching Algorithm"),
		format_algorithm("格式化算法", "Format Algorithm"),
		score_algorithm("评分算法", "Score Algorithm"),
		evaluation_data_source("评估数据源", "Evaluation Data Source"),
		test_instances("测试实例", "Test Instances"),
		evaluation_stage("评估阶段", "Evaluation Stage"),
		scene_start("场景启动", "Scene Start"),
		to("至", "To"),
		scene_end("场景结束", "Scene End"),
		evaluation_result("评估结果", "Evaluation Result"),
		detailed_data("详细数据", "Detailed Data"),
		log_events("日志事件", "Log Events"),
		hard_disk_usage("硬盘使用率", "Hard Disk Usage");

		@Getter
		private final String chinese;

		@Getter
		private final String english;

		TemplateI18n(String chinese, String english) {
			this.chinese = chinese;
			this.english = english;
		}
	}
}
