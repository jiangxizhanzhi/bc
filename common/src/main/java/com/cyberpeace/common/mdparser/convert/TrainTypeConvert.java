package com.cyberpeace.common.mdparser.convert;


import com.cyberpeace.common.baseinterface.GenericEnum;

public enum TrainTypeConvert implements MdConvertEnum<Integer, String> , GenericEnum {

	TEST(1, "渗透测试"), MAINTENANCE(2, "运维防护"),
	SIMULATION(3, "系统仿真"), SCENES(4, "综合场景"), OTHER(5, "其他");

	private Integer trainType;

	private String desc;

	TrainTypeConvert(Integer trainType, String desc) {
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
