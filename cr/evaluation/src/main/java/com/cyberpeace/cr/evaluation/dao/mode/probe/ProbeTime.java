package com.cyberpeace.cr.evaluation.dao.mode.probe;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ProbeTime {
    @SerializedName("$date")
    private String date;
}
