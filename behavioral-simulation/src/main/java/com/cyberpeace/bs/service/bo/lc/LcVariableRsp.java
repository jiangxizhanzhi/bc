package com.cyberpeace.bs.service.bo.lc;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LcVariableRsp implements Serializable {
    private String id;

    private List<LcVariableRspBo> variables;
}
