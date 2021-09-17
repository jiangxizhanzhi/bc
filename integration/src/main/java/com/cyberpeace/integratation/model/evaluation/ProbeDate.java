package com.cyberpeace.integratation.model.evaluation;

import java.io.Serializable;

import lombok.Data;

@Data
public class ProbeDate implements Serializable {

	private String machine_identification;

	private String server_id;

	private String probe_hash;

	private String status;

	private String result;

	private String create_time;

}
