package com.cyberpeace.integratation.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.integratation.model.SceneConfig;
import com.cyberpeace.integratation.model.scene.AllNodeReq;
import com.cyberpeace.integratation.model.scene.TopoDigest;

public interface RemoteTopoService {

	String create(SceneConfig sceneConfig);

	JSONObject getTopoById(String topoId);

	/**
	 * 根据现有的topoid，生成一份新的拓扑拷贝数据
	 *
	 * @param originTopoId 原始拓扑id
	 * @return 返回拷贝后的拓扑id
	 */
	String copyTopo(String originTopoId);

	TopoDigest getTopoDigest(String topoId);

	Map<String, TopoDigest> batchGetTopoDigest(List<String> topoIds);

	/**
	 * 保存mp拓扑
	 * 新建和修改用同一个接口
	 */
	String save(JSONObject json);

	/**
	 * 保存mp拓扑，但不限制节点数量
	 * @param json 拓扑数据
	 * @return 拓扑id
	 */
	String saveUnlimited(JSONObject json);

	/**
	 * 返回同步之后的拓扑数据
	 */
	JSONObject sync(JSONObject json);

	/**
	 * 删除中台拓扑
	 * @param topoId
	 * @return
	 */
	Boolean deleteById(String topoId);

	/**
	 * topo配置数据导出 数据可直接请求中台保存接口生成新的topo
	 * @param topoId
	 * @return
	 */
	JSONObject exportTopo(String topoId);

	/**
	 * 比较两个topo是否相同
	 * @param topo
	 * @param topoId
	 * @return
	 */
	Boolean equalTopo(JSONObject topo, String topoId);

	/**
	 * 批量删除topo
	 * @param topoIds
	 * @return
	 */
	Boolean batchDeleteTopo(List<String> topoIds);

	/**
	 * 设置topo部分启动
	 * @param topoId
	 * @param supportPartialStart
	 */
	void setSupportPartialStart(String topoId, Boolean supportPartialStart);

	/**
	 * 获取所有节点
	 *
	 * @param req 请求体
	 * @return {@code JSONArray}
	 */
	JSONArray getAllNodes(AllNodeReq req);

	/**
	 * 获取节点统计数据
	 *
	 * @param topoId 拓扑id
	 * @return {@code JSONObject}
	 */
	JSONObject getNodeStatistics(String topoId);
}
