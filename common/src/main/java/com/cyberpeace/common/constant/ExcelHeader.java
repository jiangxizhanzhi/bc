package com.cyberpeace.common.constant;

import com.cyberpeace.common.model.CommonContext;

public enum ExcelHeader {

	SHEETNAME(new String[]{"学习数据"},new String[]{"Study data"},new String[]{"desc"}),

	STUDY_STATISTIC(new String[] {"姓名", "组织", "完成课时数", "完成课后练习数", "完成实验数", "参加考试次数"},
			new String[] {"Name", "Organization", "Number of classes completed",
					"Number of exercises completed after class", "Number of completed experiments",
					"Number of examinations taken"
			},
			new String[] {"desc"}),
	ORG_STUDY_STATISTIC(new String[] {"组织", "人数", "完成课时数", "完成课后练习数", "完成实验数","参加考试次数"},
			new String[] {"Organization", "Participation", "Number of classes completed",
					"Number of exercises completed after class", "Number of completed experiments","Number of examinations taken"},
			new String[] {"desc"}),
	TRAIN_PLAN(new String[] { "计划名称", "课程名", "课时名", "开始时间", "结束时间", "组织", "任课教师", "任务个数", "学生人数" },
			new String[] { "Plan Name", "Course Name", "Course Hour Name", "Start time", "End time",
					"Organization", "Teacher", "Lesson Number", "Student Number" },
			new String[] {"desc"}),

	CLASSROOM(new String[] { "学号", "姓名", "通过状态", "得分", "课程分数", "最高分", "最低分" },
			new String[] { "Plan Name", "Course Name", "Course Hour Name", "Start time", "End time",
			"Organization", "Teacher", "Lesson Number", "Student Number" },
			new String[] {"desc"});


	private  String[] cnName;

	private  String[] enName;

	public String[] header;

	ExcelHeader(String[] cnName, String[] enName, String[] header) {
		this.cnName = cnName;
		this.enName = enName;
		this.header = header;
	}

	public String[] getCnName() {
		return cnName;
	}

	public String[] getEnName() {
		return enName;
	}

	public String[] getHeader() {
		return CommonContext.isChinese() ? getCnName() : getEnName();
	}
}
