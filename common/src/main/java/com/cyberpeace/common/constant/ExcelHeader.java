package com.cyberpeace.common.constant;

public enum ExcelHeader {

	STUDY_STATISTIC(new String[] { "姓名", "组织", "完成课时数", "完成课后练习数", "完成实验数", "参加考试次数" }),
	TRAIN_PLAN(new String[] { "计划名称", "课程名", "课时名", "开始时间", "结束时间", "组织" });

	public String[] header;

	ExcelHeader(String[] header) {
		this.header = header;
	}

}
