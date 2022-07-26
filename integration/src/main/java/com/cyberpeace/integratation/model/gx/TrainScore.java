package com.cyberpeace.integratation.model.gx;

import java.io.Serializable;

import lombok.Data;

@Data
public class TrainScore implements Serializable {

	private String personId;

	private String score;

	public TrainScore() {

	}

	public TrainScore(String userId, Integer score) {
		this.personId = userId;
		this.score = score.toString();
	}

}
