package com.cyberpeace.integratation.model.evaluation;

import lombok.Data;

import java.io.Serializable;

@Data
public class MarkItemData implements Serializable {
    private Long t;

    private String v;

    private Integer m;
}
