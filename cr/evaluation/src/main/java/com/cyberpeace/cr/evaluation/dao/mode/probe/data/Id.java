package com.cyberpeace.cr.evaluation.dao.mode.probe.data;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Id {
    @SerializedName("$oid")
    private String oid;

    // Getters and setters
}
