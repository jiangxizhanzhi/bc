package com.cyberpeace.common.model.evaluation;

import java.io.Serializable;

import lombok.Data;

@Data
public class ProbeTimeSequenceValue implements Serializable {

    private Integer value;

    /**
     * yyyy-mm-dd hh:mm:ss
     */
    private String time;
}
