package com.cyberpeace.cr.evaluation.service.context;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvalLoadLogContext implements Serializable {

	private String loadName;

	private Date time;
}
