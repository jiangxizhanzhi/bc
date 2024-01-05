package com.cyberpeace.common.constant;

import com.cyberpeace.common.model.CommonContext;

public enum ExcelHeader {

	SHEETNAME(new String[]{"学习数据"},new String[]{"Study data"},new String[]{"desc"}),

	STUDY_STATISTIC(new String[]{"用户名", "姓名", "组织", "角色", "完成课时", "完成实验", "完成练习",  "完成考试", "实验平均得分"},
			new String[]{"UserName", "Name", CommonConstant.ORG, "Role", "Number of classes completed",
					"Number of exercises completed after class", "Number of completed experiments",
					"Number of examinations taken", "Number of examinations taken", "Experimental mean score"
			},
			new String[]{"desc"}),
	ORG_STUDY_STATISTIC(new String[]{"组织", "人数", "完成课时", "完成实验", "完成练习", "完成考试", "实验平均得分"},
			new String[]{CommonConstant.ORG, "Participation", "Number of classes completed",
					"Number of exercises completed after class", "Number of completed experiments", "Number of examinations taken", "Experimental mean score"},
			new String[] {"desc"}),
	PERSON_STUDY_STATISTIC(new String[]{"用户名", "姓名", "课程学习进度", "理论题正确率",  "实操题正确率", "考试通过率"},
			new String[]{"UserName", "Name", "Course progress",
					"Correct rate of theoretical questions", "Correct rate of real operation", "Exam pass rate"
			},
			new String[] {"desc"}),
	TRAIN_PLAN(new String[] {"计划名称", "课程名", "课时名", "开始时间", "结束时间", "组织"},
			new String[] {"Plan Name", "Course Name", "Course Hour Name", "Start time", "End time", CommonConstant.ORG},
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
