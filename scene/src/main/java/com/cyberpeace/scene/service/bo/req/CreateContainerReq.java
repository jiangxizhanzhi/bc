package com.cyberpeace.scene.service.bo.req;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.scene.dao.entity.NetworkEntity;
import com.cyberpeace.scene.service.bo.AccessMode;
import com.cyberpeace.scene.service.bo.Net;
import com.cyberpeace.scene.service.bo.NetAccessMode;
import com.cyberpeace.scene.service.bo.TopologyElement;

import lombok.Data;

/**
 * @author :   SN
 * @date :   2023/12/21 19:56
 * @desc :
 **/
@Data
public class CreateContainerReq implements Serializable {

    /**
     * 容器名称
     */
    private String name;

    /**
     * 容器镜像id
     */
    private String image;

    /**
     * 容器网络配置
     */
    private List<Net> nets;

	public CreateContainerReq(TopologyElement hostElement, NetworkEntity network) {
		Net net = new Net(network.getUuid());
		if (!CollectionUtils.isEmpty(hostElement.getPorts())) {
			for (JSONObject json : hostElement.getPorts().get(0).getAccessModes()) {
				AccessMode accessMode = json.toJavaObject(AccessMode.class);
				NetAccessMode mode = new NetAccessMode();
				mode.setProtocol(accessMode.getProtocol());
				mode.setPort(accessMode.getPort());
				mode.setUsername(accessMode.getUsername());
				mode.setPassword(accessMode.getPassword());
				mode.setEnable_port_mapping(accessMode.getProxy());

				net.getAccess_modes().add(mode);
			}
		}
		List<Net> netList = Lists.newArrayList();
		netList.add(net);

		this.name = hostElement.getName();
		this.nets = netList;
	}
}
