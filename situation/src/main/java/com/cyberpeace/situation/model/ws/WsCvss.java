package com.cyberpeace.situation.model.ws;

import lombok.Data;

import java.io.Serializable;

@Data
public class WsCvss implements Serializable {
    private String nodeId;

    private Integer flag;

    private Double cvss;
}
