package com.cyberpeace.common.model.evaluation;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class ProbeOriginData implements Serializable {

    private String data;

    private Date time;

    private String hostName;
}
