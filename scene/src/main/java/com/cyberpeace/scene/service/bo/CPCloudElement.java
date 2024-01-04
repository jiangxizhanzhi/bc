package com.cyberpeace.scene.service.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author :   SN
 * @date :   2024/1/3 10:50
 * @desc :
 **/
@Data
public class CPCloudElement implements Serializable {

    private List<String> staticRoutes;

    private Integer vlanId;

    private String ospfRouteId;

    private String lanPortType;

    private String firewallResourceId;

    private List<String> hostRoutes;

    private String electricPortType;
}
