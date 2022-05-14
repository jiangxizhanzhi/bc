package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

import java.util.Date;

@Data
@TableName("cr_eval_probe_data")
public class EvalProbeData extends BaseTable {
    private String sceneId;

    private String nodeId;

    private String probeId;

    private Date time;

    private String probeData;

}
