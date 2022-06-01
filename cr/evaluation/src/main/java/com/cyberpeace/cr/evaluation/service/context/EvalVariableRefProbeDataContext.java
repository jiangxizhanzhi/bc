package com.cyberpeace.cr.evaluation.service.context;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class EvalVariableRefProbeDataContext implements Serializable {
    private String formattedValue;

    private Integer status;

    private Date time;
}
