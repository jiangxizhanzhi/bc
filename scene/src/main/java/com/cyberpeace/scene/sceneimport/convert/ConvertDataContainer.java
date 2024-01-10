package com.cyberpeace.scene.sceneimport.convert;

import lombok.Data;
import lombok.Getter;
import com.cyberpeace.scene.base.*;
import com.cyberpeace.scene.sceneimport.parse.result.ExtNodeInfo;
import com.cyberpeace.scene.sceneimport.parse.result.image.ImageInfoParseResult;

import java.util.*;

@Data
public class ConvertDataContainer {
    // 存主表信息
    private BaseSceneBO sceneConfigBO;
    private List<BaseSceneElementBO> networkElements = new ArrayList<>();
    private List<BaseSceneElementBO> nodeElements = new ArrayList<>();

    // 存云平台插件信息
    private BaseCloudPluginSceneBO cloudPluginConfig;
    private Map<String, BaseCloudPluginSceneElementBO> cloudPluginElements = new HashMap<>();
    private Map<String, BaseCloudPluginSceneElementPortBO> cloudPluginPorts = new HashMap<>();

    private List<BaseSceneElementPortConnectionBO> connections = new ArrayList<>();
    // 未连接任何网络的节点
    private List<BaseSceneElementBO> noNetworkNodeElements = new ArrayList<>();
    private List<BaseSceneAreaBO> areaBOS = new ArrayList<>();
    private Map<String, BaseSceneElementPortBO> node2NodePort = new HashMap<>();
//    private Map<String, String> entityPortId2Name = new HashMap<>();
    private Map<String, ExtNodeInfo.PortProtocolExportInfo> portId2Protocol = new HashMap<>();

    private Set<String> simpleLayer3Switch = new HashSet<>();

    private Map<String, Map<String, BaseSceneElementPortBO>> node2NetworkPorts = new HashMap<>();
    private String sceneFilePath;
    private Map<String, ExtNodeInfo> extNodeInfos = new HashMap<>();
    private Map<String, ImageInfoParseResult> imageInfos = new HashMap<>();
    public BaseSceneTopologyBO getSceneConfigTopologyBO() {
        BaseSceneTopologyBO topology = new BaseSceneTopologyBO();
        topology.setConnections(this.connections);
        topology.setScene(this.sceneConfigBO);
        topology.setElements(this.getAllElements());
        topology.setAreas(this.areaBOS);
        return topology;
    }

    public List<BaseSceneElementBO> getAllElements() {
        List<BaseSceneElementBO> allElements = new ArrayList<>();
        allElements.addAll(this.networkElements);
        allElements.addAll(this.nodeElements);
        return allElements;
    }
    public Set<String> getSimpleLayer3Switch() {
        return simpleLayer3Switch;
    }

    public void addNetworkElement(BaseSceneElementBO element) {
        this.networkElements.add(element);
    }

    public void addNodeElement(BaseSceneElementBO element) {
        this.nodeElements.add(element);
    }

    public void addConnection(BaseSceneElementPortConnectionBO connection) {
        this.connections.add(connection);
    }
    public void addAreaElement(BaseSceneAreaBO area) {
        this.areaBOS.add(area);
    }
    public void addNoNetworkNodeElements(BaseSceneElementBO element) {
        this.noNetworkNodeElements.add(element);
    }

    public void putNode2NetworkPorts(String nodeId, String networkId, BaseSceneElementPortBO sceneConfigElementPortBO) {
        if (!this.node2NetworkPorts.containsKey(nodeId)) {
            this.node2NetworkPorts.put(nodeId, new HashMap<>());
        }
        this.node2NetworkPorts.get(nodeId).put(networkId, sceneConfigElementPortBO);
    }

    public void addNode2NodePort(String fromNode, String toNode, BaseSceneElementPortBO fromPort) {
        this.node2NodePort.put(fromNode + "_" + toNode, fromPort);
    }

    public void setPluginSceneConfigBO(BaseCloudPluginSceneBO pluginSceneConfigBO) {
        this.cloudPluginConfig = pluginSceneConfigBO;
    }

    public void putPluginElement(String elementId, BaseCloudPluginSceneElementBO pluginElement) {
        this.cloudPluginElements.put(elementId,  pluginElement);
    }

    public void putPluginElementPort(String portId, BaseCloudPluginSceneElementPortBO pluginPort) {
            this.cloudPluginPorts.put(portId,  pluginPort);
    }
    public void putImageInfo(String image, ImageInfoParseResult result) {
        this.imageInfos.put(image, result);
    }
}
