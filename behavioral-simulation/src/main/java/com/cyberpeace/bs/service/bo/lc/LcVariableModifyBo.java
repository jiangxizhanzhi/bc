package com.cyberpeace.bs.service.bo.lc;

import lombok.Data;

import java.io.Serializable;

@Data
public class LcVariableModifyBo implements Serializable {

    private Long dataId;

    private String name;

    private String varValue;

    private String note;
}
