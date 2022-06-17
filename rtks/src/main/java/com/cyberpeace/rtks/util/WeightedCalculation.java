package com.cyberpeace.rtks.util;

public class WeightedCalculation {

	private Integer weightSum = 0;

	private Double scoreSum = 0.0;

	public void feed(Integer weight, Double score) {
		weightSum += weight;
		scoreSum += weight * score;
	}

	public Double solve() {
		if (scoreSum == 0.0) {
			return 0.0;
		} else {
			return scoreSum / weightSum;
		}
	}
}
