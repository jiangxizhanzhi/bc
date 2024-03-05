package com.cyberpeace.cs.service.context;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RoleBo implements Serializable {

    private String teamName;

    private List<String> roleName;

    public RoleBo(String teamName, List<String> roleName) {
        this.teamName = teamName;
        this.roleName = roleName;
    }

    public RoleBo() {
    }
}
