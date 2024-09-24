package com.cyberpeace.cr.evaluation.dao.mode.probe;

import lombok.Data;

@Data
public class Hop {
    private int hop;
    private String ip;
    private String host;
    private String rtt;
    private boolean timeout;

    // Getters and setters
}
