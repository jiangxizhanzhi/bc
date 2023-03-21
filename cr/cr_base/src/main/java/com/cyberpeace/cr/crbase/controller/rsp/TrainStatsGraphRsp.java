package com.cyberpeace.cr.crbase.controller.rsp;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TrainStatsGraphRsp implements Serializable {
    private List<String> label;

    private List<Integer> value;

    private Integer count;
}
