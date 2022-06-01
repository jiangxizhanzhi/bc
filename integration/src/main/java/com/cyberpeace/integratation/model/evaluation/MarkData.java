package com.cyberpeace.integratation.model.evaluation;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MarkData implements Serializable {
    private Long baseTime;

    List<MarkItemData> data;
}
