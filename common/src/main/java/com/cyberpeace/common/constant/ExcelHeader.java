package com.cyberpeace.common.constant;

import com.cyberpeace.constant.SelectI18nName;
import lombok.Getter;


enum BaseHeader implements SelectI18nName {
	NAME("姓名", "Name"),
	ORG("组织", "Org"),
	FINISHLESSONS("完成课时数", "Number of classes completed"),
	FINISHPRACTICES("完成课后练习数", "Number of exercises completed after class"),
	FINISHEXPS("完成实验数", "Number of completed experiments"),
	FINISHEXAMS("参加考试次数", "Number of examinations taken"),
	PARTICIPATION("人数", "participation"),
	TRAINPLAN("计划名称", "Train plan"),
	COURSE("课程名", "Course"),
	LESSON("课时名", "Lesson"),
	STARTTIME("开始时间", "StartTime"),
	ENDTIME("结束时间", "EndTime"),
	STUDYDATA("学习数据", "Study data");

	@Getter
	private final String cnName;

	@Getter
	private final String enName;

	BaseHeader(String cnName, String enName){
		this.cnName = cnName;
		this.enName = enName;
	}
}

public enum ExcelHeader  {

	SHEETNAME(new String[]{
			BaseHeader.STUDYDATA.selectI18nName()
	}),

	STUDY_STATISTIC(new String[] {
			BaseHeader.NAME.selectI18nName(), BaseHeader.ORG.selectI18nName(), BaseHeader.FINISHLESSONS.selectI18nName(),
			BaseHeader.FINISHPRACTICES.selectI18nName(), BaseHeader.FINISHEXPS.selectI18nName(),
			BaseHeader.FINISHEXAMS.selectI18nName()
	}),
	ORG_STUDY_STATISTIC(new String[] { BaseHeader.ORG.selectI18nName(), BaseHeader.PARTICIPATION.selectI18nName(), BaseHeader.FINISHLESSONS.selectI18nName(),
			BaseHeader.FINISHPRACTICES.selectI18nName(), BaseHeader.FINISHEXPS.selectI18nName(),
			BaseHeader.FINISHEXAMS.selectI18nName()
	}),
	TRAIN_PLAN(new String[] { BaseHeader.TRAINPLAN.selectI18nName(), BaseHeader.COURSE.selectI18nName(), BaseHeader.LESSON.selectI18nName(),
			BaseHeader.STARTTIME.selectI18nName(), BaseHeader.ENDTIME.selectI18nName(), BaseHeader.ORG.selectI18nName() });

	public String[] header;

	ExcelHeader(String[] header) {
		this.header = header;
	}

}
