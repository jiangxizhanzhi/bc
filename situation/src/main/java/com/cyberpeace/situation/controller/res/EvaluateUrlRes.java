package com.cyberpeace.situation.controller.res;

import java.io.Serializable;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.integratation.service.impl.SituationEnvService;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Data
public class EvaluateUrlRes implements Serializable {

	@ApiModelProperty("评估链接")
	private String url;

	@Autowired
	private SituationEnvService situationEnvService;

	public String getUrl() {
		return this.url;
	}

	public enum Role implements GenericEnum {

		WHITE(0, "白方"), RED(1, "红方"), BLUE(2, "蓝方");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		private Role(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

	public EvaluateUrlRes(String runId, String role) {

	}

}
