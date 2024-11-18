package com.cyberpeace.cr.redvsblue.service;

import com.cyberpeace.cr.redvsblue.service.context.RbSituation;
import com.cyberpeace.cr.redvsblue.service.context.RbSituationServer;

import java.util.List;
import java.util.Map;

/**
 * 红蓝对抗态势Service
 *
 * @author SN
 * @version 1.0.0
 * @since 1.0.0
 * @date 2024/11/18
 */
public interface RbSituationService {

	/**
	 * 获取全部红蓝对抗态势
	 *
	 * @throws
	 * @see List<RbSituation>@return {@code List<RbSituation> }
	 */
	List<RbSituation> getAllRbSituation();

	/**
	 * 获取红蓝对抗服务器由资源id
	 *
	 * @param resourceId 资源id
	 * @return {@code List<RbSituationServer> }
	 * @throws
	 * @see String
	 * @see String
	 */
	List<RbSituationServer> getRbServerByResourceId(String resourceId);

	/**
	 * 获取场景id
	 *
	 * @throws
	 * @see String@return {@code String }
	 */
	String getSceneId();

	/**
	 * 获取节点映射
	 *
	 * @throws
	 * @see Map<String, String>@return {@code Map<String, String> }
	 */
	Map<String, String> getNodeMap();

	/**
	 * 获取中台场景id
	 *
	 * @throws
	 * @see String@return {@code String }
	 */
	String getMpSceneId();
}
