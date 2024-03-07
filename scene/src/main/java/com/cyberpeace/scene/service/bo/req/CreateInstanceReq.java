package com.cyberpeace.scene.service.bo.req;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.scene.dao.entity.ImageEntity;
import com.cyberpeace.scene.dao.entity.NetworkEntity;
import com.cyberpeace.scene.service.bo.AccessMode;
import com.cyberpeace.scene.service.bo.Net;
import com.cyberpeace.scene.service.bo.NetAccessMode;
import com.cyberpeace.scene.service.bo.TopologyElement;

import lombok.Data;

/**
 * @author :   SN
 * @date :   2023/12/29 9:23
 * @desc :
 **/
@Data
public class CreateInstanceReq implements Serializable {

    /**
     * 虚拟机名称
     */
    private String name;

    /**
     * 镜像id
     */
    private String image;

    /**
     * 网络信息
     */
    private List<Net> nets;

	public CreateInstanceReq(TopologyElement hostElement, NetworkEntity network) {
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

		if (StringUtils.isBlank(hostElement.getImage())) {
			this.name = hostElement.getName();
		} else {
			this.name = hostElement.getImage();
		}
		this.nets = netList;
	}

	public CreateInstanceReq(ImageEntity image, NetworkEntity network) {
		Net net = new Net(network.getUuid());
		net.setFixed_ip(image.getIp());
		NetAccessMode mode = new NetAccessMode();
		mode.setProtocol("rdp");
		mode.setPort(image.getPort());
		mode.setUsername(image.getUsername());
		mode.setPassword(image.getPassword());
		mode.setEnable_port_mapping(true);

		net.getAccess_modes().add(mode);
		List<Net> netList = Lists.newArrayList();
		netList.add(net);

		this.name = image.getName();
		this.nets = netList;
	}

}
