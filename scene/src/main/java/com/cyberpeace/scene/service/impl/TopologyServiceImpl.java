package com.cyberpeace.scene.service.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.JsonKeyConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.scene.constant.enums.ImageTypeEnum;
import com.cyberpeace.scene.constant.enums.RoleEnum;
import com.cyberpeace.scene.constant.enums.SceneEnum;
import com.cyberpeace.scene.dao.entity.NodeEntity;
import com.cyberpeace.scene.dao.entity.TopologyEntity;
import com.cyberpeace.scene.dao.mapper.TopologyMapper;
import com.cyberpeace.scene.service.NodeService;
import com.cyberpeace.scene.service.TopologyService;
import com.cyberpeace.scene.service.bo.ElementPort;
import com.cyberpeace.scene.service.bo.Topology;
import com.cyberpeace.scene.service.bo.TopologyElement;

import lombok.extern.slf4j.Slf4j;

/**
 * @author :   SN
 * @date :   2023/12/20 16:24
 * @desc :
 **/
@Service
@Slf4j
public class TopologyServiceImpl extends ServiceImpl<TopologyMapper, TopologyEntity> implements TopologyService {

	@Autowired
	private NodeService nodeService;

    @Override
    public JSONObject getTopology(String topologyId) {
        LambdaQueryWrapper<TopologyEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TopologyEntity::getTopologyId, topologyId);
		TopologyEntity topologyEntity = getOne(wrapper);
		if (null == topologyEntity) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
        JSONObject jsonObject = new JSONObject();
		jsonObject.put("topology", JSONObject.parse(topologyEntity.getTopology()));
		JSONObject pluginJson = new JSONObject();
		Topology topology = JsonUtil.toJsonObject(topologyEntity.getTopology(), Topology.class);
		pluginJson.put("cpcloud", this.makePlugins(topology));
		jsonObject.put("plugins", pluginJson);
        JSONObject configDataJson = new JSONObject();
        configDataJson.put("scene_config", jsonObject);
        return configDataJson;
    }

    @Override
    public Topology formatTopology(String topologyId) {
        LambdaQueryWrapper<TopologyEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TopologyEntity::getTopologyId, topologyId);
        TopologyEntity entity = getOne(wrapper);
        if (entity != null){
            String topologyInfo = entity.getTopology();
            Topology topology = JsonUtil.toJsonObject(topologyInfo, Topology.class);
            return topology;
        }
        return null;
    }

    @Override
    public Map<String, List<TopologyElement>> getTopologyElementMap(Topology topology) {
        Map<String, List<TopologyElement>> map = new HashMap<>();
        if (Objects.nonNull(topology)){
            for (TopologyElement element : topology.getElements()){
                ImageTypeEnum imageType = EnumUtils.getEnumByCode(element.getType(), ImageTypeEnum.class);
                if (Objects.nonNull(imageType)){
                    if (map.containsKey(imageType.getName())){
                        map.get(imageType.getName()).add(element);
                    }else {
                        List<TopologyElement> list = new ArrayList<>();
                        list.add(element);
                        map.put(imageType.getName(), list);
                    }
                }
            }
            return map;
        }
        return null;
    }

    @Override
    public TopologyEntity selectTopology(String topologyId) {
        LambdaQueryWrapper<TopologyEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TopologyEntity::getTopologyId, topologyId);
        return getOne(wrapper);
    }

	@Override
	public JSONObject makePlugins(Topology topology) {
		JSONObject jsonObject = new JSONObject();
		if (Objects.isNull(topology)) {
			return jsonObject;
		}
		JSONObject elementsJson = new JSONObject();
		JSONObject elementPortsJson = new JSONObject();
		List<TopologyElement> elements = topology.getElements();
		int sum = elements.stream().mapToInt(e -> e.getPorts().size()).sum();
		List<TopologyElement> newElements = new ArrayList<>();
		for (TopologyElement element : elements) {
			JSONObject elementJson = new JSONObject();
			RoleEnum roleEnum = RoleEnum.getRoleEnum(element.getType());
			if (!Objects.isNull(roleEnum)) {
				elementJson.put("role", roleEnum.getValue());
				elementJson.put("image_type", roleEnum.getImageType());
				elementJson.put("electric_port_number", sum);
			}
			elementsJson.put(element.getId(), elementJson);
			JSONObject address = getStatusEvent(element);
			for (ElementPort port : element.getPorts()) {
				port.setIp(address.getString("ip"));
				port.setEvent(2);
				port.setEventStatus(address.getInteger("event_status"));
				JSONObject elementPortJson = new JSONObject();
				if (!CollectionUtils.isEmpty(port.getAccessModes())) {
					JSONArray accessModes = port.getAccessModes();
					JSONArray newAccessModes = new JSONArray();
					accessModes.forEach(iter -> {
						JSONObject accessMode = JSONObject.parseObject(iter.toString());
						accessMode.put("id", element.getNodeId());
						accessMode.put("ip", address.getString("ip"));
						accessMode.put("available", address.getBooleanValue("available"));
						accessMode.put("type", element.getType());
						newAccessModes.add(accessMode);
					});
					elementPortJson.put("access_modes", newAccessModes);
				}
				elementPortJson.put("ip", address.getString("ip"));
				elementPortJson.put("event_status", address.getInteger("event_status"));
				elementPortJson.put("event", 2);
				elementPortJson.put("resource_id", element.getNodeId());
				elementPortsJson.put(port.getId(), elementPortJson);
				newElements.add(element);
			}
		}
		jsonObject.put("elements", elementsJson);
		jsonObject.put("element_ports", elementPortsJson);
		topology.setElements(newElements);
		return jsonObject;
	}

	@Override
	public JSONObject getStatusEvent(TopologyElement element) {
		JSONObject jsonObject = new JSONObject();
		String ip = null;
		int eventStatus = 4;
		boolean available = false;
		NodeEntity node = nodeService.getOne(new LambdaQueryWrapper<NodeEntity>()
				.eq(NodeEntity::getInstanceId, element.getNodeId()));
		if (Objects.nonNull(node)) {
			ip = node.getIp();
			if (Objects.equals(SceneEnum.ACTIVE.getCode(), node.getStatus())) {
				eventStatus = 4;
				available = true;
			} else if (Objects.equals(SceneEnum.ERROR.getCode(), node.getStatus())) {
				eventStatus = 5;
			} else if (Objects.equals(SceneEnum.CREATE.getCode(), node.getStatus())) {
				eventStatus = 2;
			}
		}
		element.setEvent(2);
		element.setEventStatus(eventStatus);
		jsonObject.put("ip", ip);
		jsonObject.put("event_status", eventStatus);
		jsonObject.put("available", available);
		return jsonObject;
	}

	@Override
	public String adCreate(JSONObject req) {
		log.info("ad copy topo request: {}", req);
		JSONObject topo = req.getJSONObject(JsonKeyConstant.SCENE_CONFIG)
				.getJSONObject(JsonKeyConstant.TOPOLOGY);
		TopologyEntity entity = new TopologyEntity();
		entity.setTopologyId(IDGeneratorUtil.getID());
		entity.setTopology(JsonUtil.toJsonString(topo));
		this.save(entity);
		return entity.getTopologyId();
	}
}
