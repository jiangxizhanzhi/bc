package com.cyberpeace.cr.evaluation.service.context;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class EvalVariableRefProbeDataContext implements Serializable {
    private String formattedValue;

    private Integer status;

    private Date time;
}
