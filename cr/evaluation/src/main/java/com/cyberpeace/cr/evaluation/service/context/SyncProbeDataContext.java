package com.cyberpeace.cr.evaluation.service.context;

import com.cyberpeace.cr.evaluation.dao.entity.EvalRecord;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class SyncProbeDataContext {
    @ApiModelProperty("测评评估对象")
    @NotNull
    private EvalRecord evalRecord;

    @ApiModelProperty("测评任务对象")
    @NotNull
    private EvalTask evalTask;

    @ApiModelProperty("测评评估中的变量项目ID")
    @NotNull
    private Set<String> variableIds;
}
