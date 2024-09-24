package com.cyberpeace.cr.evaluation.dao.mode.probe;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class NetworkProbeData {
    @SerializedName("_id")
    private Id id;
    private String instanceId;
    private List<EntryPointInterface> entryPointInterfaces;
    @SerializedName("intranet_ip")
    private List<IntranetIP> intranetIPs;
    private List<TraceData> traceData;
    private ProbeTime time;

    // Getters and setters
}

