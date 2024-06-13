package com.cyberpeace.cr.evaluation.dao.mode.probe;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class IntranetIP {
    private String cidr;
    @SerializedName("online_ip")
    private List<OnlineIP> onlineIPs;

    // Getters and setters
}
