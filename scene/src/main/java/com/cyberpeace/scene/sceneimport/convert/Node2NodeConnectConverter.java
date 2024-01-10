package com.cyberpeace.scene.sceneimport.convert;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.Setter;
import lombok.var;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import com.cyberpeace.scene.base.BaseSceneElementPortConnectionBO;
import com.cyberpeace.scene.json.JsonCommon;
import com.cyberpeace.scene.sceneimport.parse.result.NodeElementParseResult;
import com.cyberpeace.scene.util.TextUtils;

import java.util.*;

/**
 * @Description
 * @Author maojun
 * @Date 2021/10/12
 */
class Node2NodeConnectConverter implements IConverter {

    private List<NodeElementParseResult> nodes;

    public Node2NodeConnectConverter(List<NodeElementParseResult> nodes) {
        this.nodes = nodes;
    }

    public void convert2BO(ConvertDataContainer container) {
        Set<String> networkIds = new HashSet<>();
        if (CollectionUtils.isNotEmpty(container.getNetworkElements())) {
            container.getNetworkElements().forEach(e -> networkIds.add(e.getId()));
        }
        var node2NodePort = container.getNode2NodePort();
        Set<String> connection = new HashSet<>();
        List<Node2Node> node2Nodes = new ArrayList<>();
        for (var node : nodes) {
            if (CollectionUtils.isEmpty(node.getNet())) {
                continue;
            }
            for (var net : node.getNet()) {
                // 和网络的连接已由 NetworkElementConverter 处理
                if (networkIds.contains(net.getId())) {
                    continue;
                }
                String key = node.getId() + "_" + net.getId();
                if (!connection.contains(key)) {
                    connection.add(key);
                    connection.add(net.getId() + "_" + node.getId());
                    Node2Node node2Node = new Node2Node();
                    node2Node.setFrom(node.getId());
                    node2Node.setTo(net.getId());
                    node2Nodes.add(node2Node);
                }
            }
        }
        for (var node2Node : node2Nodes) {
            String from = node2Node.getFrom();
            String to = node2Node.getTo();
            var portFrom = node2NodePort.get(from + "_" + to);
            var portTo = node2NodePort.get(to + "_" + from);
            // 创建连线
            BaseSceneElementPortConnectionBO connectionBO = new BaseSceneElementPortConnectionBO();
            connectionBO.setId(TextUtils.uuid());
            connectionBO.setSource(portFrom.getId());
            connectionBO.setTarget(portTo.getId());
            // 传递连线的前端属性
            if (container.getPortId2Protocol().get(portFrom.getId()) != null) {
                var vertices = container.getPortId2Protocol().get(portFrom.getId()).getVertices();
                if (StringUtils.isNotEmpty(vertices)) {
                    var verticesList = JsonCommon.parse(vertices, new TypeReference<List<Map<String, Object>>>() {
                    });
                    connectionBO.setVertices(verticesList);
                } else {
                    connectionBO.setVertices(new ArrayList<>());
                }
                connectionBO.setDisplayContext(container.getPortId2Protocol().get(portFrom.getId()).getDisplayContext());
            }
            connectionBO.setName(portFrom.getName() + "_" + portTo.getName());
            container.addConnection(connectionBO);
        }
    }

    @Getter
    @Setter
    private static class Node2Node {
        private String from;
        private String to;
    }
}
