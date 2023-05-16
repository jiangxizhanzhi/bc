package com.cyberpeace.cr.singletrain.service.bo.record;

import java.io.Serializable;

import lombok.Data;

@Data
public class ScoreAndProgress implements Serializable {

	private Integer score;

	private Integer progress;
}
