package com.cyberpeace.common.model.evaluation;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class CoordinateDate implements Serializable {

	private List<Date> x;

	private List<Integer> y;
}
