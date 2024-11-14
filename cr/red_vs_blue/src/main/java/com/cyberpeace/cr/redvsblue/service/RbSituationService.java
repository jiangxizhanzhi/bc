package com.cyberpeace.cr.redvsblue.service;

import java.util.List;
import java.util.Map;

import com.cyberpeace.cr.redvsblue.service.context.RbSituation;
import com.cyberpeace.cr.redvsblue.service.context.RbSituationServer;

/**
 * 红蓝对抗态势Service
 *
 * @author SN
 * @date 2024/11/14
 */
public interface RbSituationService {

	/**
	 * 获取全部红蓝对抗态势
	 *
	 * @return {@code List<RbSituation> }
	 */
	List<RbSituation> getAllRbSituation();

	/**
	 * 获取红蓝对抗服务器由资源id
	 *
	 * @param resourceId 资源id
	 * @return {@code List<RbSituationServer> }
	 */
	List<RbSituationServer> getRbServerByResourceId(String resourceId);

	/**
	 * 获取场景id
	 *
	 * @return {@code String }
	 */
	String getSceneId();

	/**
	 * 获取节点映射
	 *
	 * @return {@code Map<String, String> }
	 */
	Map<String, String> getNodeMap();

	/**
	 * 获取中台场景id
	 *
	 * @return {@code String }
	 */
	String getMpSceneId();

	/**
	 * 再设置id
	 */
	void reSetId();
}
