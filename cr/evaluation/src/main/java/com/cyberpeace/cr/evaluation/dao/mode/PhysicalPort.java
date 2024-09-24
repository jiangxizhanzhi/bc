package com.cyberpeace.cr.evaluation.dao.mode;

import lombok.Data;

@Data
public class PhysicalPort {
    private String ip;
    private String subnetMask;
    private String address;
}
