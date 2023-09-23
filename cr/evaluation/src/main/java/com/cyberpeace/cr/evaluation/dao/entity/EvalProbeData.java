package com.cyberpeace.cr.evaluation.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
@TableName("cr_eval_probe_data")
public class EvalProbeData extends BaseTable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String sceneId;

    private String deviceId;

    private String probeId;

    private Date collectionTime;

    private String deviceIp;

    private String content;

}
