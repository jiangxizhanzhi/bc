package com.cyberpeace.scene.sceneimport.handler;

import com.cyberpeace.scene.base.BaseSceneElementBO;
import com.cyberpeace.scene.base.BaseSceneElementPortBO;
import com.cyberpeace.scene.base.BaseSceneElementPortConnectionBO;
import com.cyberpeace.scene.base.BaseSceneTopologyBO;
import com.cyberpeace.scene.util.MapUtils;
import lombok.var;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BaseSceneHandler {

    private final Map<String, String> portIdConnectedPortIdMap = new ConcurrentHashMap<>();
    private final Map<String, BaseSceneElementBO> portIdElementMap = new ConcurrentHashMap<>();
    private final Map<String, BaseSceneElementBO> elementMap = new ConcurrentHashMap<>();
    private final Map<String, BaseSceneElementPortBO> elementPortMap = new ConcurrentHashMap<>();
    private final Map<String, BaseSceneElementPortConnectionBO> elementPortConnectionMap = new ConcurrentHashMap<>();

    public void fromData(BaseSceneTopologyBO topology) {
        // 验证数据字段
        if (topology != null) {
            loadElementBOs(topology.getElements());
            loadConnectionBOs(topology.getConnections());
        }

//        loadScenePluginData(sceneDataBO.getPlugins());
    }
    public BaseSceneElementBO getPortConnectedElement(String portId) {
        var connectedPortId = portId != null ? portIdConnectedPortIdMap.getOrDefault(portId, null) : null;
        return getPortElement(connectedPortId);
    }
    public BaseSceneElementBO getPortElement(String portId) {
        return portId != null ? portIdElementMap.getOrDefault(portId, null) : null;
    }
    private void loadElementBOs(List<BaseSceneElementBO> elements) {
        if (elements == null) {
            return;
        }

        elements.forEach(element -> {

            elementMap.put(element.getId(), element);

            List<BaseSceneElementPortBO> elementPorts = element.getPorts();
            if (elementPorts == null) {
                return;
            }
            elementPorts.forEach(elementPort -> {
                elementPort.setElement(element);
                elementPortMap.put(elementPort.getId(), elementPort);
                portIdElementMap.put(elementPort.getId(), element);
            });
        });
    }
    private void loadConnectionBOs(List<BaseSceneElementPortConnectionBO> connections) {
        if (connections == null) {
            return;
        }

        Map<String, List<String>> outerConnectionPortIdConnectionsMap = new HashMap<>();
        Map<String, BaseSceneElementPortConnectionBO> validConnectionMap = new HashMap<>();
        connections.forEach(connection -> {
            String sourcePortId = connection.getSource();
            String targetPortId = connection.getTarget();
            if (!elementPortMap.containsKey(sourcePortId)
                    && !elementPortMap.containsKey(targetPortId)) {
                return;
            }

            var connectionKey = elementPortConnectionKey(connection);
            if (!elementPortMap.containsKey(sourcePortId)) {
                MapUtils.setDefault(outerConnectionPortIdConnectionsMap, sourcePortId, new ArrayList<>())
                        .add(connectionKey);
            }
            if (!elementPortMap.containsKey(targetPortId)) {
                MapUtils.setDefault(outerConnectionPortIdConnectionsMap, targetPortId, new ArrayList<>())
                        .add(connectionKey);
            }
            validConnectionMap.put(connectionKey, connection);
        });

        // 外部连接端口（其它区域端口）
//        if (!outerConnectionPortIdConnectionsMap.isEmpty()) {
//            Set<String> currentOuterConnectionPortIds = outerConnectionPortIdConnectionsMap.keySet();
//            if (!StringUtils.isEmpty(sceneId)) {
//                Set<String> existOuterConnectionPortIds = new HashSet<>();
//                sceneElementPortMapper.list(new HumanCondition()
//                                .select(BaseSceneElementPortModel::getPortId)
//                                .filterEqual(BaseSceneElementPortModel::getScene, sceneId)
//                                .filterIn(BaseSceneElementPortModel::getPortId,
//                                        outerConnectionPortIdConnectionsMap.keySet().toArray(new String[0])))
//                        .forEach(elementPort -> existOuterConnectionPortIds.add(elementPort.getPortId()));
//                currentOuterConnectionPortIds.removeAll(existOuterConnectionPortIds);
//            }
//            currentOuterConnectionPortIds.forEach(notExistOuterConnectionPortId -> {
//                outerConnectionPortIdConnectionsMap.get(notExistOuterConnectionPortId)
//                        .forEach(validConnectionMap::remove);
//            });
//        }

        validConnectionMap.forEach((key, connection) -> {
            elementPortConnectionMap.put(key, connection);
            portIdConnectedPortIdMap.put(connection.getSource(), connection.getTarget());
            portIdConnectedPortIdMap.put(connection.getTarget(), connection.getSource());
        });
    }
    private String elementPortConnectionKey(BaseSceneElementPortConnectionBO elementPortConnection) {
        return StringUtils.joinWith(":", elementPortConnection.getSource(),
                elementPortConnection.getTarget());
    }
}
