package com.cyberpeace.common.constant;

import com.cyberpeace.common.model.CommonContext;

/**
 * Excel 标题
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Enum
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个枚举类 `ExcelHeader`，用于管理 Excel 表格中不同工作表的标题。以下是代码的主要特点和功能： 
 * 1. **枚举定义**： 
 * - 枚举类 `ExcelHeader` 定义了一系列常量，每个常量代表一种 Excel 工作表的标题。 
 * - 每个枚举值包含三种语言的标题数组（中文、英文和其他描述性文字）。 
 * 2. **构造方法**： 
 * - 提供了一个构造函数，接收三种语言的标题数组，并初始化实例变量 `cnName`、`enName` 和 `header`。 
 * 3. **属性**： 
 * - `cnName`: 存储中文标题。 
 * - `enName`: 存储英文标题。 
 * - `header`: 根据当前上下文（是否为中文）动态返回 `cnName` 或 `enName`。 
 * 4. **方法**： 
 * - `getCnName()`: 返回中文标题数组。 
 * - `getEnName()`: 返回英文标题数组。 
 * - `getHeader()`: 根据上下文（通过 `CommonContext.isChinese()` 判断）动态返回中文或英文标题。 
 * 5. **应用场景**： 
 * - 该类适用于多语言支持的 Excel 导出场景，可以根据用户的语言环境动态生成合适的标题。 
 * 6. **优点**： 
 * - 使用枚举类封装了标题数据，便于管理和扩展。 
 * - 动态切换语言的功能提高了代码的灵活性。 
 * 总结：这是一个用于管理 Excel 表格标题的工具类，支持多语言动态切换，结构清晰且易于维护。 
**/
public enum ExcelHeader {

	/**
	 * 工作表名称
	 *
	 * @see ExcelHeader
	 */
	SHEETNAME(new String[]{"学习数据"},new String[]{"Study data"},new String[]{"desc"}),

	/**
	 * 研究统计量
	 *
	 * @see ExcelHeader
	 */
	STUDY_STATISTIC(new String[] {"姓名", "组织", "完成课时数", "完成课后练习数", "完成实验数", "参加考试次数"},
			new String[] {"Name", "Organization", "Number of classes completed",
					"Number of exercises completed after class", "Number of completed experiments",
					"Number of examinations taken"
			},
			new String[] {"desc"}),
	/**
	 * ORG 研究统计
	 *
	 * @see ExcelHeader
	 */
	ORG_STUDY_STATISTIC(new String[] {"组织", "人数", "完成课时数", "完成课后练习数", "完成实验数","参加考试次数"},
			new String[] {"Organization", "Participation", "Number of classes completed",
					"Number of exercises completed after class", "Number of completed experiments","Number of examinations taken"},
			new String[] {"desc"}),

	APT_THREAT_DETECT_RULE(new String[] { "APT代号/组织", "检测类型", "内容简介", "内容详情", "应用工具" },
			new String[] {}, new String[] { "desc" }),

	EVENT_INTEGRATION_RULE(new String[] { "防御类型", "内容简介", "内容详情", "应用工具" }, new String[] {},
			new String[] { "desc" }),

	LOADED_FILE_THREAT_RULE(new String[] { "病毒类型", "内容简介", "内容详情", "应用工具", "执行方式" },
			new String[] {}, new String[] { "desc" }),

	TRAFFIC_DETECT_RULE(new String[] { "规则内容", "规则编号", "规则信息", "威胁等级", "工具" , "攻击类型"}, new String[] {},
			new String[] { "desc" }),

	WARNING_DICTIONARY(new String[] { "告警名称", "工具类型", "工具告警等级", "告警等级", "等级描述", "更新时间" },
			new String[] {}, new String[] { "desc" }),

	TRAFFIC_EXTRACTION(new String[] { "名称", "顺序", "内容", "工具"},
			new String[] {}, new String[] { "desc" }),

	/**
	 * 训练计划
	 *
	 * @see ExcelHeader
	 */
	TRAIN_PLAN(new String[] {"计划名称", "课程名", "课时名", "开始时间", "结束时间", "组织"},
			new String[] {"Plan Name", "Course Name", "Course Hour Name", "Start time", "End time", "Organization"},
			new String[] {"desc"});


	/**
	 * 中文名称
	 *
	 * @see String[]
	 */
	private  String[] cnName;

	/**
	 * 中文名称
	 *
	 * @see String[]
	 */
	private  String[] enName;

	/**
	 * 页眉
	 *
	 * @see String[]
	 */
	public String[] header;

	/**
	 * Excel 标题
	 *
	 * @param cnName 中文名称
	 * @param enName 中文名称
	 * @param header 页眉
	 */
	ExcelHeader(String[] cnName, String[] enName, String[] header) {
		this.cnName = cnName;
		this.enName = enName;
		this.header = header;
	}

	/**
	 * 获取中文名称
	 *
	 * @return {@code String[] }
	 */
	public String[] getCnName() {
		return cnName;
	}

	/**
	 * 获取中文名称
	 *
	 * @return {@code String[] }
	 */
	public String[] getEnName() {
		return enName;
	}

	/**
	 * 获取页眉
	 *
	 * @return {@code String[] }
	 */
	public String[] getHeader() {
		return CommonContext.isChinese() ? getCnName() : getEnName();
	}
}

