package com.cyberpeace.cr.crbase.controller.rsp;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TrainStatsAccGraphRsp implements Serializable {
    private List<String> label;

    private List<Integer> startValue;

    private List<Integer> notStartValue;

    private Integer count;
}
