package com.cyberpeace.common.mdparser.convert;

/**
 * 训练类型转换
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public enum TrainTypeConvert implements MdConvertEnum<Integer, String> {

	TEST(1, "渗透测试"), MAINTENANCE(2, "运维防护"), SIMULATION(3, "系统仿真"), SCENES(4, "综合场景"), OTHER(5,
			"其他");

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
}
