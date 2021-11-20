package com.cyberpeace.oj.practice.service.bo;

import lombok.Data;

@Data
public class WebTheoryBo extends AdminTheoryBo {

	private Boolean isCollection;

	private Boolean solved;

	private String submitAnswer;

}
