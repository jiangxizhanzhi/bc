package com.cyberpeace.situation.service;

import java.io.IOException;
import java.util.List;

import com.cyberpeace.situation.controller.req.AddDynamicSchemeReq;
import com.cyberpeace.situation.controller.req.AttackMessageReq;
import com.cyberpeace.situation.controller.req.DynamicSchemeReq;
import com.cyberpeace.situation.controller.res.DynamicSchemeNameRes;
import com.cyberpeace.situation.controller.res.DynamicSchemeRes;
import com.cyberpeace.situation.controller.res.OptionsRes;
import com.cyberpeace.situation.model.ws.WsAttackSchedule;

/**
 * 动态方案Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
public interface DynamicSchemeService {

	/**
	 * 新增动态方案
	 *
	 * @param addDynamicSchemeReq 新增动态方案请求体
	 * @return {@code String }
	 * @throws
	 * @see AddDynamicSchemeReq
	 * @see String
	 */
	String addDynamicScheme(AddDynamicSchemeReq addDynamicSchemeReq);

	/**
	 * 获取动态方案由id
	 *
	 * @param resourceId 资源id
	 * @return {@code DynamicSchemeRes }
	 * @throws
	 * @see String
	 * @see DynamicSchemeRes
	 */
	DynamicSchemeRes getDynamicSchemeById(String resourceId);

	/**
	 * 更新动态方案
	 *
	 * @param dynamicSchemeReq 动态方案请求体
	 * @return {@code DynamicSchemeReq }
	 * @throws
	 * @see DynamicSchemeReq
	 * @see DynamicSchemeReq
	 */
	DynamicSchemeReq updateDynamicScheme(DynamicSchemeReq dynamicSchemeReq);

	/**
	 * 删除动态方案
	 *
	 * @param resourceId 资源id
	 * @throws
	 * @see String
	 */
	void deleteDynamicScheme(String resourceId);

	/**
	 * 获取全部动态名称
	 *
	 * @param sceneId 场景id
	 * @return {@code List<DynamicSchemeNameRes> }
	 * @throws
	 * @see String
	 * @see List<DynamicSchemeNameRes>
	 */
	List<DynamicSchemeNameRes> getAllDynamicName(String sceneId);

	/**
	 * 获取当地选项
	 *
	 * @param optionsRes 选项结果
	 * @throws
	 * @see OptionsRes
	 */
	void getLocalOption(OptionsRes optionsRes);

	/**
	 * 获取远程选项
	 *
	 * @param optionsRes 选项结果
	 * @param sceneId    场景id
	 * @param isInstance 是否实例
	 * @throws
	 * @see OptionsRes
	 * @see String
	 * @see Boolean
	 */
	void getRemoteOption(OptionsRes optionsRes, String sceneId, Boolean isInstance);

	/**
	 * 推送媒体自房间
	 *
	 * @param attackMessageBo 攻击消息Bo
	 * @param code            代号
	 * @throws
	 * @see AttackMessageReq
	 * @see String
	 */
	void pushMediaToRoom(AttackMessageReq attackMessageBo, String code);

	/**
	 * 推送攻击自房间
	 *
	 * @param attackMessageBo 攻击消息Bo
	 * @param elementIds      元素id列表
	 * @throws
	 * @see AttackMessageReq
	 * @see List<String>
	 */
	void pushAttackToRoom(AttackMessageReq attackMessageBo, List<String> elementIds);

	/**
	 * 推送动态方案自房间
	 *
	 * @param dynamicSchemeReq 动态方案请求体
	 * @throws
	 * @see DynamicSchemeReq
	 */
	void pushDynamicSchemeToRoom(DynamicSchemeReq dynamicSchemeReq);

	/**
	 * 有效动态方案名称
	 *
	 * @param addDynamicSchemeReq 新增动态方案请求体
	 * @throws
	 * @see AddDynamicSchemeReq
	 */
	void validDynamicSchemeName(AddDynamicSchemeReq addDynamicSchemeReq);

	/**
	 * 获取附表
	 *
	 * @param sceneId 场景id
	 * @return {@code List<WsAttackSchedule> }
	 * @throws
	 * @see String
	 * @see List<WsAttackSchedule>
	 */
	List<WsAttackSchedule> getSchedule(String sceneId);

	/**
	 * 清除场景数据
	 *
	 * @param sceneId 场景id
	 * @throws
	 * @see String
	 */
	void clearSceneData(String sceneId);

	/**
	 * 清除媒体
	 *
	 * @param sceneId 场景id
	 * @throws
	 * @see String
	 */
	void clearMedia(String sceneId);

	/**
	 * 有效阶段
	 *
	 * @param dynamicSchemeReq 动态方案请求体
	 * @throws
	 * @see DynamicSchemeReq
	 */
	void validStage(DynamicSchemeReq dynamicSchemeReq);

	/**
	 * 清除房间附表
	 *
	 * @param sceneId 场景id
	 * @throws
	 * @see String
	 */
	void clearRoomSchedule(String sceneId);

	/**
	 * 做导出文件
	 *
	 * @param resourceId 资源id
	 * @param tempDir    临时目录
	 * @return {@code String }
	 * @throws @throws IOException io异常
	 * @see String
	 * @see String
	 * @see String
	 */
	String makeExportFile(String resourceId, String tempDir) throws IOException;

	/**
	 * 导入动态方案
	 *
	 * @param unZipPath  un zip 路径
	 * @param sceneId    场景id
	 * @param isInstance 是否实例
	 * @return {@code String }
	 * @throws
	 * @see String
	 * @see String
	 * @see Boolean
	 * @see String
	 */
	String importDynamicScheme(String unZipPath, String sceneId, Boolean isInstance);

	/**
	 * 有效媒体
	 *
	 * @param dynamicSchemeReq 动态方案请求体
	 * @throws
	 * @see DynamicSchemeReq
	 */
	void validMedia(DynamicSchemeReq dynamicSchemeReq);

	/**
	 * 获取元素id列表
	 *
	 * @param sceneId    场景id
	 * @param isInstance 是否实例
	 * @return {@code List<String> }
	 * @throws
	 * @see String
	 * @see Boolean
	 * @see List<String>
	 */
	List<String> getElementIds(String sceneId, Boolean isInstance);
}
