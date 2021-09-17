package com.cyberpeace.evaluate.service.bo;

import java.io.Serializable;

import com.cyberpeace.common.model.evaluation.CoordinateDate;
import lombok.Data;

@Data
public class EstimateReviewBo implements Serializable {

	private String instanceId;

	private String variableId;

	private String variableName;

	private CoordinateDate date;
}
