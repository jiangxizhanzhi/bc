package com.cyberpeace.oj.ojbase.service.bo.post;

import lombok.Data;

@Data
public class ReportEvaBo {

	private String resourceId;

	private String userId;

	private Boolean isSubmit;

	private Integer score;

	private Boolean passed;
}
