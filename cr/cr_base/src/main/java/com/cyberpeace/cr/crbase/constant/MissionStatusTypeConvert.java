package com.cyberpeace.cr.crbase.constant;


import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.mdparser.convert.MdConvertEnum;

public enum MissionStatusTypeConvert implements MdConvertEnum<Integer, String>, GenericEnum {

	START(1, "开始任务"), PAUSE(2, "暂停任务"),
	RESUME(3, "继续任务"), ABORT(4, "中止任务"), FAIL(5, "任务失败"),
	FINISH(6, "完成任务"),OVERTIME(7, "任务超时"),NEW(8, "任务新增");

	private Integer trainType;

	private String desc;

	MissionStatusTypeConvert(Integer trainType, String desc) {
		this.trainType = trainType;
		this.desc = desc;
	}

	@Override
	public Integer to() {
		return trainType;
	}

	@Override
	public String reTo() {
		return desc;
	}

	@Override
	public Integer getCode() {
		return trainType;
	}

	@Override
	public String getDesc() {
		return desc;
	}
}
