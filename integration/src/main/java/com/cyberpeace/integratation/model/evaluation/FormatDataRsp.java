package com.cyberpeace.integratation.model.evaluation;

import java.io.Serializable;
import java.util.List;


import com.cyberpeace.common.model.evaluation.ProbeTimeSequenceValue;
import lombok.Data;

@Data
public class FormatDataRsp implements Serializable {

	private Integer code;

	private List<ProbeTimeSequenceValue> data;

	private String error_message;
}
