package com.cyberpeace.cr.evaluation.dao.mode;

import lombok.Data;

import java.util.List;

@Data
public class Firewall {
    private List<PhysicalPort> physicalPorts;
    private String interfaceName;
}
