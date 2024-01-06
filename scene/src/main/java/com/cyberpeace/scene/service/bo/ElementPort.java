package com.cyberpeace.scene.service.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author :   SN
 * @date :   2023/12/21 10:32
 * @desc :
 **/
@Data
public class ElementPort implements Serializable {

    private String id;

    private String name;

    private String ip;

    private Integer egress;

    private Integer ingress;

    @TableField(value = "access_modes")
    private List<String> accessModes;

    @TableField(value = "firewall_rules")
    private List<String> firewallRules;

    private Integer loss;

    private Integer delay;

    private String resourceId;

    private Integer event;

    private Integer eventStatus;
}
