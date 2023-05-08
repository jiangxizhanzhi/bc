package com.cyberpeace.situation.controller.res;

import java.io.Serializable;

import com.cyberpeace.common.baseinterface.GenericEnum;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

/**
 * 评估链接返回
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class EvaluateUrlRes implements Serializable {

	/**
	 * 评估链接
	 */
	@ApiModelProperty("评估链接")
	private String url;

	/**
	 * 获取url
	 *
	 * @return {@code String}
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * 角色
	 *
	 * @author chenhy
	 * @date 2023/05/06
	 */
	public enum Role implements GenericEnum {

		/**
		 * 白方
		 */
		WHITE(0, "白方"),

		/**
		 * 红方
		 */
		RED(1, "红方"),

		/**
		 * 蓝方
		 */
		BLUE(2, "蓝方");

		/**
		 * 代码
		 */
		@Getter
		private Integer code;

		/**
		 * 描述
		 */
		@Getter
		private String desc;

		Role(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

}
