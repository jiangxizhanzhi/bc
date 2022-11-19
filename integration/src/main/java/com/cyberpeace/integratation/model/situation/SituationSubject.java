package com.cyberpeace.integratation.model.situation;

import lombok.Data;

import java.io.Serializable;

@Data
public class SituationSubject implements Serializable {

	private String subject_name;

	private String subject_id;

	private String subject_status;

	private String time;
}
