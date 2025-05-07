package com.cyberpeace.integratation.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.integratation.model.SceneConfig;
import com.cyberpeace.integratation.model.scene.TopoDigest;
import com.cyberpeace.integratation.model.scene.TopoNodeListReq;
import com.cyberpeace.integratation.service.bo.ChangeTopoBo;
import com.cyberpeace.integratation.service.bo.ChangeTopoConfigBo;

/**
 * 远程拓扑Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @since 1.0.0
 */
public interface RemoteTopoService {

    /**
     * 创建
     *
     * @param sceneConfig 场景配置
     * @return {@code String }
     * @throws
     * @see SceneConfig
     * @see String
     */
    String create(SceneConfig sceneConfig);

    /**
     * 获取拓扑由id
     *
     * @param topoId 拓扑id
     * @return {@code JSONObject }
     * @throws
     * @see String
     * @see JSONObject
     */
    JSONObject getTopoById(String topoId);

    /**
     * 根据中台的csceneId和areaId从中台获取当前areaId指示的分层对应的拓扑信息,
     * 3D的吴俊要我们把分层节点放到elements处，这个接口针对其特制
     *
     * @param topoId 对应中台的csceneId
     * @param areaId 分层的id, 如果为顶层，area_id为空
     * @return
     * @throws
     * @see String
     * @see String
     * @see JSONObject
     */
    JSONObject getTopoByIdAndAreaIdFor3D(String topoId, String areaId);


    /**
     * @description: 改变原始拓扑数据
     * @param changeTopoConfigBo
     * @throws
     * @return JSONObject
     */
    JSONObject changeTopoInfoFor3D(ChangeTopoConfigBo changeTopoConfigBo);

    /**
     * 根据现有的topoid，生成一份新的拓扑拷贝数据
     *
     * @param originTopoId 原始拓扑id
     * @return 返回拷贝后的拓扑id
     * @throws
     * @see String
     * @see String
     */
    String copyTopo(String originTopoId);

    /**
     * 获取拓扑消化
     *
     * @param topoId 拓扑id
     * @return {@code TopoDigest }
     * @throws
     * @see String
     * @see TopoDigest
     */
    TopoDigest getTopoDigest(String topoId);

    /**
     * 批量获取拓扑消化
     *
     * @param topoIds 拓扑id列表
     * @return {@code Map<String, TopoDigest> }
     * @throws
     * @see List<String>
     * @see Map<String, TopoDigest>
     */
    Map<String, TopoDigest> batchGetTopoDigest(List<String> topoIds);

    /**
     * 保存mp拓扑
     * 新建和修改用同一个接口
     *
     * @param json JSON 格式
     * @return {@code String }
     * @throws
     * @see JSONObject
     * @see String
     */
    String save(JSONObject json);

    /**
     * 保存mp拓扑，但不限制节点数量
     *
     * @param json 拓扑数据
     * @return 拓扑id
     * @throws
     * @see JSONObject
     * @see String
     */
    String saveUnlimited(JSONObject json);

    /**
     * 返回同步之后的拓扑数据
     *
     * @param json JSON 格式
     * @return {@code JSONObject }
     * @throws
     * @see JSONObject
     * @see JSONObject
     */
    JSONObject sync(JSONObject json);

    /**
     * 删除中台拓扑
     *
     * @param topoId
     * @return
     * @throws
     * @see String
     * @see Boolean
     */
    Boolean deleteById(String topoId);

    /**
     * topo配置数据导出 数据可直接请求中台保存接口生成新的topo
     *
     * @param topoId
     * @return
     * @throws
     * @see String
     * @see JSONObject
     */
    JSONObject exportTopo(String topoId);

    /**
     * 比较两个topo是否相同
     *
     * @param topo
     * @param topoId
     * @return
     * @throws
     * @see JSONObject
     * @see String
     * @see Boolean
     */
    Boolean equalTopo(JSONObject topo, String topoId);

    /**
     * 批量删除topo
     *
     * @param topoIds 拓扑id列表
     * @throws
     * @see List<String>
     */
    void batchDeleteTopo(List<String> topoIds);

    /**
     * 设置topo部分启动
     *
     * @param topoId
     * @param supportPartialStart 支持部分启动
     * @throws
     * @see String
     * @see Boolean
     */
    void setSupportPartialStart(String topoId, Boolean supportPartialStart);

    /**
     * 获取所有节点
     *
     * @param req 请求体
     * @return {@code JSONArray}
     * @throws
     * @see TopoNodeListReq
     * @see JSONArray
     */
    JSONArray getAllNodes(TopoNodeListReq req);

    /**
     * 获取节点统计数据
     *
     * @param topoId 拓扑id
     * @return {@code JSONObject}
     * @throws
     * @see String
     * @see JSONObject
     */
    JSONObject getNodeStatistics(String topoId);
}
