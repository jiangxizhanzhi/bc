package com.cyberpeace.common.constant;

import com.cyberpeace.common.model.CommonContext;

/**
 * Excel 标题
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Enum
 * @since 1.0.0
 */
public enum ExcelHeader {

	/**
	 * 工作表名称
	 */
	SHEETNAME(new String[]{"学习数据"},new String[]{"Study data"},new String[]{"desc"}),

	/**
	 * 研究统计量
	 */
	STUDY_STATISTIC(new String[] {"姓名", "组织", "完成课时数", "完成课后练习数", "完成实验数", "参加考试次数"},
			new String[] {"Name", "Organization", "Number of classes completed",
					"Number of exercises completed after class", "Number of completed experiments",
					"Number of examinations taken"
			},
			new String[] {"desc"}),
	/**
	 * ORG 研究统计
	 */
	ORG_STUDY_STATISTIC(new String[] {"组织", "人数", "完成课时数", "完成课后练习数", "完成实验数","参加考试次数"},
			new String[] {"Organization", "Participation", "Number of classes completed",
					"Number of exercises completed after class", "Number of completed experiments","Number of examinations taken"},
			new String[] {"desc"}),
	/**
	 * 训练计划
	 */
	TRAIN_PLAN(new String[] {"计划名称", "课程名", "课时名", "开始时间", "结束时间", "组织"},
			new String[] {"Plan Name", "Course Name", "Course Hour Name", "Start time", "End time", "Organization"},
			new String[] {"desc"});


	/**
	 * 中文名称
	 */
	private  String[] cnName;

	/**
	 * 中文名称
	 */
	private  String[] enName;

	/**
	 * 页眉
	 */
	public String[] header;

	/**
	 * Excel 标题
	 *
	 * @param cnName 中文名称
	 * @param enName 中文名称
	 * @param header 页眉
	 * @throws
	 * @see String[]
	 * @see String[]
	 * @see String[]
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
	 * @throws
	 * @see String[]
	 */
	public String[] getCnName() {
		return cnName;
	}

	/**
	 * 获取中文名称
	 *
	 * @return {@code String[] }
	 * @throws
	 * @see String[]
	 */
	public String[] getEnName() {
		return enName;
	}

	/**
	 * 获取页眉
	 *
	 * @return {@code String[] }
	 * @throws
	 * @see String[]
	 */
	public String[] getHeader() {
		return CommonContext.isChinese() ? getCnName() : getEnName();
	}
}
