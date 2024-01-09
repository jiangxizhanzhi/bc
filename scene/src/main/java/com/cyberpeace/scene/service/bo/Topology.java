package com.cyberpeace.scene.service.bo;

import java.io.Serializable;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.cyberpeace.scene.dao.entity.SceneEntity;

import lombok.Data;

/**
 * @author :   SN
 * @date :   2023/12/21 10:25
 * @desc :
 **/
@Data
public class Topology implements Serializable {

    /**
     * 区域
     */
    private List<TopologyArea> areas;

    /**
     * 连接
     */
    private List<TopologyConnection> connections;

    /**
     * 元素
     */
    private List<TopologyElement> elements;

    /**
     * 网络信息
     */
    private NetworkInfo network;

    /**
     * 场景信息
     */
    private SceneEntity scene;

	/**
	 * 去掉accessModes的json字符串的双引号
	 */
	public void setAccessModes() {
		if (CollectionUtils.isEmpty(this.elements)) {
			return;
		}
		for (TopologyElement element : this.elements) {
			if (CollectionUtils.isEmpty(element.getPorts())) {
				continue;
			}
			for (ElementPort port : element.getPorts()) {
				if (CollectionUtils.isEmpty(port.getAccessModes())) {
					continue;
				}
				JSONArray array = new JSONArray();
				for (Object accessMode : port.getAccessModes()) {
					array.add(JSON.parseObject(accessMode.toString()));
				}
				port.setAccessModes(array);
			}
		}
	}
}
