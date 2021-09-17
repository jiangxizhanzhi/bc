package com.cyberpeace.integratation.model.evaluation;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ProbeDateRsp implements Serializable {

	private ProbeLinks links;

	private List<ProbeDate> data;
}
