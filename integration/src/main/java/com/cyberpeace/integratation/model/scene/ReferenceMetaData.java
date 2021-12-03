package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReferenceMetaData implements Serializable {

	private String businessId;

	private String businessName;

	/**
	 * 1xx=实训；2xx=靶场
	 */
	private Integer businessCode;

	/**
	 * oj专用，暂时不用
	 */
	private Boolean fromWeb;

	private String createUserId;
}
