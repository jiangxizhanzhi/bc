package com.cyberpeace.scene.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.scene.dao.entity.TopologyEntity;
import com.cyberpeace.scene.service.bo.Topology;
import com.cyberpeace.scene.service.bo.TopologyElement;

/**
 * @author :   辛发圣
 * @date :   2023/12/20 16:22
 * @desc :
 **/
public interface TopologyService extends IService<TopologyEntity> {

    /**
     * 根据拓扑id查询拓扑
     *
     * @param topologyId 拓扑id
     * @return 拓扑信息
     */
    JSONObject getTopology(String topologyId);

    /**
     * 格式化拓扑信息
     * @param topologyId    拓扑id
     * @return  格式化信息
     */
    Topology formatTopology(String topologyId);

    /**
     * 获取拓扑元素信息
     * @param topology  拓扑信息
     * @return  拓扑元素信息
     */
    Map<String, List<TopologyElement>> getTopologyElementMap(Topology topology);

    TopologyEntity selectTopology(String topologyId);

    JSONObject makePlugins(Topology topology);

    JSONObject getStatusEvent(TopologyElement element);

	/**
	 * 提供给竞赛复制topo
	 * @param req 请求体
	 * @return {@code String}
	 */
	String adCreate(JSONObject req);

    void deleteTopology(String topologyId, boolean deleteImage);
}
