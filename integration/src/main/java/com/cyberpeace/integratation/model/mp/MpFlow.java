package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Data;
import lombok.Getter;

@Data
public class MpFlow implements Serializable {

	private String id;

	private String title;

	private String flowDesc;

	private Integer interactiveType;

	private Integer resourceType;

	private String fileName;

	private String scriptFile;

	private String pcapFile;

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
}
