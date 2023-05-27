package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class AllNodeReq implements Serializable {

	private String sceneId;

	private Integer category;

	private String name;

	private Boolean realTimeHostState;

	public String toQuery() {
		StringBuilder builder = new StringBuilder();
		if (StringUtils.isBlank(sceneId)) {
			log.error("scene id missing");
			throw new BusinessException(ErrorCode.UNKNOWN_SCENE);
		}
		builder.append(this.sceneId);
		builder.append("?realTimeHostState=");
		if (this.realTimeHostState.equals(Boolean.TRUE)) {
			builder.append("true");
		} else {
			builder.append("false");
		}
		if (StringUtils.isNotBlank(name)) {
			builder.append("&name=").append(this.name);
		}
		if (null != category) {
			builder.append("&category=").append(this.category);
		}

		return builder.toString();
	}
}
