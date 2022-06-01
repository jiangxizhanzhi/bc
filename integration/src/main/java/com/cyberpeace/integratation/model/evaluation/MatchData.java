package com.cyberpeace.integratation.model.evaluation;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MatchData implements Serializable {
    private Long baseTime;

    private List<MatchItemData> data;
}
