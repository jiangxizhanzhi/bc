package com.cyberpeace.scene.service.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author :   辛发圣
 * @date :   2023/12/21 10:28
 * @desc :
 **/
@Data
public class TopologyElement implements Serializable {

    private String id;

    private String eid;

    private String name;

    private Integer type;

    private String icon2;

    private String icon3;

    @TableField(value = "system_language")
    private String systemLanguage;

    @TableField(value = "system_architecture")
    private String systemArchitecture;

    @TableField(value = "system_type")
    private String systemType;

    @TableField(value = "max_port_number")
    private Integer maxPortNumber;

    private Integer cpu;

    private Integer ram;

    private Integer disk;

    @TableField(value = "init_commands")
    private List<String> initCommands;

    private List<ElementPort> ports;

    private String file;

    @TableField(value = "node_command")
    private String nodeCommand;

    private String position2;

    private String displayContext;

    private NetworkInfo network;

    private String nodeId;
}
