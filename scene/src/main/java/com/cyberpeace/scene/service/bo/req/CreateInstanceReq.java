package com.cyberpeace.scene.service.bo.req;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.scene.dao.entity.ImageEntity;
import com.cyberpeace.scene.dao.entity.NetworkEntity;
import com.cyberpeace.scene.service.bo.Net;
import com.cyberpeace.scene.service.bo.NetAccessMode;
import com.cyberpeace.scene.service.bo.interactive.Access;

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


	public CreateInstanceReq(ImageEntity image, NetworkEntity network) {
		Net net = new Net(network.getUuid());
		net.setFixed_ip(image.getIp());
		for (Access access : image.getAccessModes()) {
			NetAccessMode mode = new NetAccessMode();
			mode.setProtocol(access.getProtocol());
			mode.setPort(access.getPort());
			if (StringUtils.isNotBlank(access.getUsername())) {
				mode.setUsername(access.getUsername());
			}
			if (StringUtils.isNotBlank(access.getPassword())) {
				mode.setPassword(access.getPassword());
			}
			mode.setEnable_port_mapping(true);

			net.getAccess_modes().add(mode);
		}
		List<Net> netList = Lists.newArrayList();
		netList.add(net);

		this.name = image.getName();
		this.nets = netList;
	}

}
