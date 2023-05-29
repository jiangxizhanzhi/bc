package com.cyberpeace.cr.crbase.service.context;

import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import lombok.Data;

@Data
public class TaskStage extends CrTask {

    private String stage;

    private String name;
}
