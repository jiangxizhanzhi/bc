package com.cyberpeace.integratation.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.integratation.model.SceneConfig;
import com.cyberpeace.integratation.model.scene.TopoDigest;

import java.util.List;
import java.util.Map;

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
	 * 获取节点漏洞信息
	 * @param elementId
	 * @return
	 */
	JSONObject getElementLeaks(String elementId);

	JSONArray getAllLeak();
}
