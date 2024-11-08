package com.cyberpeace.oj.ojbase.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.service.bo.SceneMonitorBo;
import com.cyberpeace.oj.ojbase.service.context.InitOjSceneContext;

import java.util.List;

/**
 * OJ当地场景Service
 *
 * @author SN
 * @date 2024/11/08
 */
public interface OjLocalSceneService extends IService<OjLocalScene> {

	/**
	 * 查询ojLocalScene数据 不包含
	 *
	 * @param businessId  资源编号 课程编号 课时编号 习题编号
	 * @param shared      是否共享 1=私有 2=共享
	 * @param authContext 前后台权限数据
	 * @return
	 */
	OjLocalScene selectOjLocalScene(String businessId, Integer shared, AuthContext authContext);

	/**
	 * 初始化ojLocalScene
	 *
	 * @param sceneContext
	 * @return
	 */
	OjLocalScene initOjLocalScene(InitOjSceneContext sceneContext);

	/**
	 * 设置topo结构中注入flag命令
	 *
	 * @param ojLocalScene
	 * @param topoId       拓扑id
	 * @return
	 */
	JSONObject setDynamicFlag(OjLocalScene ojLocalScene, JSONObject topoId);

	/**
	 * 删除本地场景
	 *
	 * @param ojLocalSceneId OJ当地场景id
	 * @return {@code OjLocalScene }
	 */
	OjLocalScene deleteLocalScene(String ojLocalSceneId);

	/**
	 * 删除本地和中台场景
	 *
	 * @param ojLocalScene
	 * @return
	 */
	Boolean deleteLocalAndRemoteScene(OjLocalScene ojLocalScene);

	/**
	 * 组装实训场景启动完成消息体
	 *
	 * @param message      场景启动相关消息
	 * @param ojLocalScene OJ当地场景
	 * @return
	 */
	JSONObject getStartSceneMessage(String message, OjLocalScene ojLocalScene);

	/**
	 * 检查场景启动数量是否超过最大数量
	 *
	 * @param userId
	 * @param createCount 创造计数
	 */
	void checkSceneStartCount(String userId, Integer createCount);

	/**
	 * 查询场景信息
	 *
	 * @param ojLocalScene
	 * @return
	 */
	JSONObject getSceneData(OjLocalScene ojLocalScene);

	/**
	 * 获取监控信息列表
	 *
	 * @param ojLocalScenes
	 * @return
	 */
	List<SceneMonitorBo> selectMonitorList(List<OjLocalScene> ojLocalScenes);

	/**
	 * 是否开启录屏
	 *
	 * @param topoId
	 * @param record
	 * @return
	 */
	JSONObject switchScreenRecord(String topoId, Boolean record);

}
