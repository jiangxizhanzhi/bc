package com.cyberpeace.cr.evaluation.dao.mode.probe.data;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Time {
    @SerializedName("$date")
    private String date;

    // Getters and setters
}
