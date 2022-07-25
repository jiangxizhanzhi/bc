package com.cyberpeace.integratation.model.evaluation;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class MatchData implements Serializable {

	private Long baseTime;

	private List<MatchItemData> data;
}
