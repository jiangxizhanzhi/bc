package com.cyberpeace.cr.evaluation.dao.mode.probe.data;

import lombok.Data;

@Data
public class Service {
    private String name;
    private String product;
    private String version;
    private String banner;
    private String response;
    private String cert;
    private CertParsed certParsed;
    private Http http;

    // Getters and setters
}
