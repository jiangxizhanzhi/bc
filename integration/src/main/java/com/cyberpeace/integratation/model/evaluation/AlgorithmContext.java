package com.cyberpeace.integratation.model.evaluation;

import lombok.Data;

import java.io.Serializable;

@Data
public class AlgorithmContext implements Serializable {
    private Integer type;

    private String id;
}
