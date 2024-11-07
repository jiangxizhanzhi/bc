package com.cyberpeace.common.mdparser.convert;


import com.cyberpeace.common.baseinterface.GenericEnum;

/**
 * 训练类型转换
 *
 * @author SN
 * @date 2024/11/07
 */
public enum TrainTypeConvert implements MdConvertEnum<Integer, String> , GenericEnum {

	TEST(1, "渗透测试"), MAINTENANCE(2, "运维防护"),
	SIMULATION(3, "系统仿真"), SCENES(4, "综合场景"), OTHER(5, "其他");

	private Integer trainType;

	private String desc;

	/**
	 * 训练类型转换
	 *
	 * @param trainType 训练类型
	 * @param desc      描述
	 */
	TrainTypeConvert(Integer trainType, String desc) {
		this.trainType = trainType;
		this.desc = desc;
	}

	/**
	 * 自
	 *
	 * @return {@code Integer }
	 */
	@Override
	public Integer to() {
		return trainType;
	}

	/**
	 * re 更改为
	 *
	 * @return {@code String }
	 */
	@Override
	public String reTo() {
		return desc;
	}

	/**
	 * 获取代号
	 *
	 * @return {@code Integer }
	 */
	@Override
	public Integer getCode() {
		return trainType;
	}

	/**
	 * 获取描述
	 *
	 * @return {@code String }
	 */
	@Override
	public String getDesc() {
		return desc;
	}
}
