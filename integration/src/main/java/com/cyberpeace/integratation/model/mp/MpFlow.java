package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Data;
import lombok.Getter;

/**
 * 中台流量
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MpFlow implements Serializable {

	/**
	 * id
	 *
	 * @see String
	 */
	private String id;

	/**
	 * 标题
	 *
	 * @see String
	 */
	private String title;

	/**
	 * 流量描述
	 *
	 * @see String
	 */
	private String flowDesc;

	/**
	 * 交互式类型
	 *
	 * @see Integer
	 */
	private Integer interactiveType;

	/**
	 * 资源类型
	 *
	 * @see Integer
	 */
	private Integer resourceType;

	/**
	 * 文件名称
	 *
	 * @see String
	 */
	private String fileName;

	/**
	 * 脚本文件
	 *
	 * @see String
	 */
	private String scriptFile;

	/**
	 * pcap 文件
	 *
	 * @see String
	 */
	private String pcapFile;

	/**
	 * 类别
	 *
	 * @see Integer
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
