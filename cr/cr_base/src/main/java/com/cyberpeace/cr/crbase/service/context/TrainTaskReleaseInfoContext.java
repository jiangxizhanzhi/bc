package com.cyberpeace.cr.crbase.service.context;

import lombok.Data;

import java.util.List;

@Data
public class TrainTaskReleaseInfoContext {

    private String name;

    private List<TrainTaskReleaseInfoRsp>  taskReleaseInfos;

    public TrainTaskReleaseInfoContext(String name, List<TrainTaskReleaseInfoRsp> taskReleaseInfos) {
        this.name = name;
        this.taskReleaseInfos = taskReleaseInfos;
    }
}
