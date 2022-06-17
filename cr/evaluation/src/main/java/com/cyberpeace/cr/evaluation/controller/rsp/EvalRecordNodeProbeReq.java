package com.cyberpeace.cr.evaluation.controller.rsp;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EvalRecordNodeProbeReq implements Serializable {
    private String id;

    private String label;

    private List<EvalRoomPairRsp> data;
}
