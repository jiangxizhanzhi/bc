package com.cyberpeace.scene.service.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.cyberpeace.scene.dao.entity.SceneEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author :   SN
 * @date :   2023/12/21 10:25
 * @desc :
 **/
@Data
public class Topology implements Serializable {

    /**
     * 区域
     */
    private List<TopologyArea> areas;

    /**
     * 连接
     */
    private List<TopologyConnection> connections;

    /**
     * 元素
     */
    private List<TopologyElement> elements;

    /**
     * 网络信息
     */
    private NetworkInfo network;

    /**
     * 场景信息
     */
    private SceneEntity scene;
}
