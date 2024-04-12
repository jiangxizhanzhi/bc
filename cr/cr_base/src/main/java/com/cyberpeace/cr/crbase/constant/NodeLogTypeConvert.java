package com.cyberpeace.cr.crbase.constant;


import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.mdparser.convert.MdConvertEnum;

public enum NodeLogTypeConvert implements MdConvertEnum<Integer, String>, GenericEnum {

	SSH(0, "靶标控守变化","靶标ssh登录"),SHUTDOWN(1, "网络联通状态变化","靶标关机，网络失去连接"),
	PAUSE(2, "网络联通状态变化","靶标重启，网络失去连接"),
	RESUME(3, "网络联通状态变化","靶标重建，网络失去连接"), CONSOLE(4, "靶标控守变化","靶标console登录"),
	RDP(5, "靶标控守变化","靶标rdp登录"), NC(6, "靶标控守变化","靶标nc登录"),
	TELNET(7, "靶标控守变化","靶标telnet登录"), COM(8, "靶标控守变化","靶标COM登录");

	private Integer trainType;

	private String desc;

	private String content;

	NodeLogTypeConvert(Integer trainType, String desc, String content) {
		this.trainType = trainType;
		this.desc = desc;
		this.content = content;
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

	public String getContent() {
		return content;
	}
}
