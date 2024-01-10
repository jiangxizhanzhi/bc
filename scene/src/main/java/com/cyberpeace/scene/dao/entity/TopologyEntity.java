package com.cyberpeace.scene.dao.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

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
	@TableId
    private String topologyId;

    /**
     * 拓扑结构
     */
    private String topology;
}
