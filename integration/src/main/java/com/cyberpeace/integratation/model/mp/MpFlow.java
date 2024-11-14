package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Data;
import lombok.Getter;

/**
 * 中台流量
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class MpFlow implements Serializable {

	/**
	 * id
	 */
	private String id;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 流量描述
	 */
	private String flowDesc;

	/**
	 * 交互式类型
	 */
	private Integer interactiveType;

	/**
	 * 资源类型
	 */
	private Integer resourceType;

	/**
	 * 文件名称
	 */
	private String fileName;

	/**
	 * 脚本文件
	 */
	private String scriptFile;

	/**
	 * pcap 文件
	 */
	private String pcapFile;

	/**
	 * 类别
	 */
	private Integer category;

	public enum resourceTypeEnum implements GenericEnum {

		PCAP(0, "pcap包"), SCRIPT(1, "脚本");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		resourceTypeEnum(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

	public enum interactiveTypeEnum implements GenericEnum {

		BACKGROUND(0, "背景流量"), ATTACK(1, "攻击流量");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		interactiveTypeEnum(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}
