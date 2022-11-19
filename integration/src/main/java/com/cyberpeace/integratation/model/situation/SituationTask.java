package com.cyberpeace.integratation.model.situation;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SituationTask implements Serializable {

	private String task_name;

	private String task_id;

	private String task_type;

	private List<SituationSubject> subject_list;
}
