package com.cyberpeace.cr.evaluation.dao.mode.topo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Connection {
    // port id
    private String from;
    // port id
    private String to;
}
