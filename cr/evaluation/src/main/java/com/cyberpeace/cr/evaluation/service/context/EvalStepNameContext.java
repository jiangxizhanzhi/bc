package com.cyberpeace.cr.evaluation.service.context;

import lombok.Data;

import java.io.Serializable;

@Data
public class EvalStepNameContext implements Serializable {

	private String resourceId;

	private String name;
}
