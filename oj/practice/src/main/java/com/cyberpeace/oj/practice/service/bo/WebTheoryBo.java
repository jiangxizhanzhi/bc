package com.cyberpeace.oj.practice.service.bo;

import lombok.Data;

@Data
public class WebTheoryBo extends AdminTheoryBo {

	private Boolean isCollection;

	// TODO 解题信息
	private String solved;

}
