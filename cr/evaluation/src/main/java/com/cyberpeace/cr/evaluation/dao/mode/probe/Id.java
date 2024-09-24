package com.cyberpeace.cr.evaluation.dao.mode.probe;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Id {
    @SerializedName("$oid")
    private String oid;
}
