package com.cyberpeace.situation.service;

import com.cyberpeace.situation.controller.req.AddDynamicSchemeReq;
import com.cyberpeace.situation.controller.req.AttackMessageReq;
import com.cyberpeace.situation.controller.req.DynamicSchemeReq;
import com.cyberpeace.situation.controller.res.DynamicSchemeNameRes;
import com.cyberpeace.situation.controller.res.DynamicSchemeRes;
import com.cyberpeace.situation.controller.res.OptionsRes;
import com.cyberpeace.situation.dao.entity.ToolVuln;
import com.cyberpeace.situation.dao.entity.Vuln;
import com.cyberpeace.situation.model.Instruction;
import com.cyberpeace.situation.model.VulnTop;
import com.cyberpeace.situation.model.ws.WsAttackSchedule;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface DynamicSchemeService {

	/**
	 * 新增动效方案
	 * @param addDynamicSchemeReq 新增动效方案的数据
	 * @return 动效方案编号
	 */
	String addDynamicScheme(AddDynamicSchemeReq addDynamicSchemeReq);

	/**
	 * 根据动效方案编号获取动效方案
	 * @param resourceId 动效方案编号
	 * @return 动效方案数据
	 */
	DynamicSchemeRes getDynamicSchemeById(String resourceId);

	/**
	 * 更新动效方案数据
	 * @param dynamicSchemeReq 新的动效方案数据
	 * @return 动效方案数据
	 */
	DynamicSchemeReq updateDynamicScheme(DynamicSchemeReq dynamicSchemeReq);

	/**
	 * 根据动效方案编号删除动效方案
	 * @param resourceId 动效方案编号
	 */
	void deleteDynamicScheme(String resourceId);

	/**
	 * 根据场景编号获取当前场景下所有动效方案名称
	 * @param sceneId 场景编号
	 * @return 动效方案名称列表
	 */
	List<DynamicSchemeNameRes> getAllDynamicName(String sceneId);

	/**
	 * 获取本地选项
	 * @param optionsRes 选项数据
	 */
	void getLocalOption(OptionsRes optionsRes);

	/**
	 * 获取远程选项
	 * @param optionsRes 选项数据
	 * @param sceneId 场景编号
	 * @param isInstance 是否是实例场景
	 */
	void getRemoteOption(OptionsRes optionsRes, String sceneId, Boolean isInstance);

	/**
	 * 推送动效方案数据
	 * @param dynamicSchemeReq 动效方案数据
	 * @param stageName 阶段名称（继续运行动效方案时传该值）
	 * @param attackMessageDescription 动效描述（继续运行动效方案时传该值）
	 */
	void pushDynamicSchemeToRoom(DynamicSchemeReq dynamicSchemeReq, String stageName, String attackMessageDescription);

	/**
	 * 校验动效方案名称
	 * @param addDynamicSchemeReq
	 */
	void validDynamicSchemeName(AddDynamicSchemeReq addDynamicSchemeReq);

	/**
	 * 获取当前场景进程信息
	 * @param sceneId 场景编号
	 * @return 进程信息
	 */
	List<WsAttackSchedule> getSchedule(String sceneId);

	/**
	 * 清除场景数据
	 * @param sceneId 场景编号
	 */
	void clearSceneData(String sceneId);

	/**
	 * 清除多媒体动效
	 * @param sceneId 场景编号
	 */
	void clearMedia(String sceneId);

	/**
	 * 校验动效方案阶段数据
	 * @param dynamicSchemeReq 动效方案数据
	 */
	void validStage(DynamicSchemeReq dynamicSchemeReq);

	/**
	 * 清除当前场景进程信息
	 * @param sceneId 场景编号
	 */
	void clearRoomSchedule(String sceneId);

	/**
	 * 动效方案导出
	 * @param resourceId 动效方案编号
	 * @param tempDir 导出目录
	 * @return 压缩包路径
	 * @throws IOException
	 */
	String makeExportFile(String resourceId, String tempDir) throws IOException;

	/**
	 * 导入动效方案
	 * @param unZipPath 压缩包路径
	 * @param sceneId 场景编号
	 * @param isInstance 是否是实例场景
	 * @return
	 */
	String importDynamicScheme(String unZipPath, String sceneId, Boolean isInstance);

	/**
	 * 校验多媒体动效数据
	 * @param dynamicSchemeReq 动效方案数据
	 */
	void validMedia(DynamicSchemeReq dynamicSchemeReq);

	/**
	 * 获取当前场景节点编号
	 * @param sceneId 场景编号
	 * @param isInstance 是否是实例场景
	 * @return 场景节点数据
	 */
	List<String> getElementIds(String sceneId, Boolean isInstance);

	/**
	 * 回放
	 * @param sceneId 场景编号
	 */
	void playbackPushDynamicSchemeToRoom(String sceneId, Date startTime, Date endTime);

	void pushDataToRealisticScene(String topoId, Integer step);


	void pushDynamicToRoom(AttackMessageReq attackMessageBo, List<String> elementIds, Boolean playback, Boolean isMultiterm);

	public void executeInstruction(Instruction instruction, String topoId);

	List<Vuln> queryLeak(String application, String nodeId);

	List<ToolVuln> queryTool(String vulnId, Integer toolType);

	VulnTop queryVulnTop(String sceneId, String nodeId, String elementId);
}
