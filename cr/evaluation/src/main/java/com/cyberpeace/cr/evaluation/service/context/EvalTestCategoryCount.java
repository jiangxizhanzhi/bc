package com.cyberpeace.cr.evaluation.service.context;

import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalTestCategoryCount implements Serializable {

	private Integer type;

	private Integer count;
}
