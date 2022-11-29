package com.cyberpeace.bs.service.bo.simulator;

import lombok.Data;

import java.io.Serializable;

@Data
public class BehaviorConfigRaw implements Serializable {

    private String behaviorId;

    private String variables;
}
