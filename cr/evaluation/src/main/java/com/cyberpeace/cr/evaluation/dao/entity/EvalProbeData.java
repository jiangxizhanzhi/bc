package com.cyberpeace.cr.evaluation.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 测评探针数据
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@TableName("cr_eval_probe_data")
public class EvalProbeData extends BaseTable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 场景id
     */
    private String sceneId;

    /**
     * 装置id
     */
    private String deviceId;

    /**
     * 探针id
     */
    private String probeId;

    /**
     * 收集时间
     */
    private Date collectionTime;

    /**
     * 装置ip地址
     */
    private String deviceIp;

    /**
     * 内容
     */
    private String content;

}
