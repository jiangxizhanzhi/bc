package com.cyberpeace.cr.evaluation.dao.mode.probe.data;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class ReportData {
    @SerializedName("_id")
    private Id id;
    private String instanceId;
    private String ip;
    private int port;
    private String transport;
    private Time time;
    private String hostname;
    private Service service;
    private List<Application> apps;
    private List<String> vuls;
    private List<String> scriptInfo;
    private boolean hasImage;

    // Getters and setters
}

