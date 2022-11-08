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

public interface DynamicSchemeService {

	String addDynamicScheme(AddDynamicSchemeReq addDynamicSchemeReq);

	DynamicSchemeRes getDynamicSchemeById(String resourceId);

	DynamicSchemeReq updateDynamicScheme(DynamicSchemeReq dynamicSchemeReq);

	void deleteDynamicScheme(String resourceId);

	List<DynamicSchemeNameRes> getAllDynamicName(String sceneId);

	void getLocalOption(OptionsRes optionsRes);

	void getRemoteOption(OptionsRes optionsRes, String sceneId, Boolean isInstance);

	void pushMediaToRoom(AttackMessageReq attackMessageBo, String code);

	void pushAttackToRoom(AttackMessageReq attackMessageBo, List<String> elementIds);

	/**
	 * 推送动效方案数据
	 * @param dynamicSchemeReq 动效方案数据
	 * @param stageName 阶段名称（继续运行动效方案时传该值）
	 * @param attackMessageDescription 动效描述（继续运行动效方案时传该值）
	 */
	void pushDynamicSchemeToRoom(DynamicSchemeReq dynamicSchemeReq, String stageName, String attackMessageDescription);

	void validDynamicSchemeName(AddDynamicSchemeReq addDynamicSchemeReq);

	List<WsAttackSchedule> getSchedule(String sceneId);

	void clearSceneData(String sceneId);

	void clearMedia(String sceneId);

	void validStage(DynamicSchemeReq dynamicSchemeReq);

	void clearRoomSchedule(String sceneId);

	String makeExportFile(String resourceId, String tempDir) throws IOException;

	String importDynamicScheme(String unZipPath, String sceneId, Boolean isInstance);

	void validMedia(DynamicSchemeReq dynamicSchemeReq);

	List<String> getElementIds(String sceneId, Boolean isInstance);
}
