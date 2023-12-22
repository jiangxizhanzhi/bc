package com.cyberpeace.scene.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

import java.io.Serializable;

/**
 * @author :   SN
 * @date :   2023/12/20 16:19
 * @desc :
 **/
@Data
@TableName("cr_topology")
public class TopologyEntity extends BaseTable implements Serializable {
    /**
     * 拓扑id
     */
    private String topologyId;

    /**
     * 拓扑结构
     */
    private String topology;
}
