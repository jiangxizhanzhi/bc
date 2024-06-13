package com.cyberpeace.cr.evaluation.dao.mode.topo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Port {
    private String id;
    // element id
    private String element;

    private String ip;
    private String mac;

    private String cidr;

    private String name;
}
