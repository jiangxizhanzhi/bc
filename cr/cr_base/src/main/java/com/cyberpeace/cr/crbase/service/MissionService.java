package com.cyberpeace.cr.crbase.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.cr.crbase.controller.rsp.MissionSchemeTemplateRsp;
import com.cyberpeace.cr.crbase.dao.entity.*;
import com.cyberpeace.cr.crbase.service.context.*;

public interface MissionService extends IService<Mission> {

	/** 新增模板任务 */
	MissionTemplate addMissionTemplate(MissionTemplate missionTemplate);

	/** 查询模板任务 */
	MissionTemplate getByMissionTemplateId(String missionTemplateId);

	/** 修改模板任务 */
	void updateMissionTemplate(MissionTemplate missionTemplate);

	Mission getById(String missionId);

	/**
	 * 保证顺序与查询时输入的id列表一致
	 */
	List<Mission> batchGet(List<String> resourceIds);

	List<Mission> batchGetByRoom(String roomId);

	/**
	 * 下发任务
	 */
	void release(String missionId, String roleId, String currentUserId);

	/**
	 * 导演新增动态任务
	 */
	Boolean addDynamic(Mission mission, CrRole role, Integer stage);

	/**
	 * mission category 列表
	 */
	List<MissionCategory> listCategories();

	/**
	 * mission完成数量统计
	 */
	Map<String, MissionCompletion> getMissionStatusData(String roomId);

	MissionCategory getCategoryById(String categoryId);

	/**
	 * 复制模板任务为靶场实例任务
	 * @return map{旧模板任务id：复制任务id}
	 */
	Map<String, String> copyScheme(List<String> missionTemplateIds, String userId, String roomId);

	/**
	 * 开放、关闭题解
	 */
	void openOrCloseWriteup(String missionId, Boolean showWriteup);

	void addSchemeTemplate(MissionSchemeTemplate schemeTemplate);

	void batchDeleteSchemeTemplate(List<String> ids);

	MissionSchemeTemplate getSchemeTemplateById(String schemeTemplateId);

	void updateMissionSchemeTemplateById(MissionSchemeTemplate toUpdateSchemeTemplate);

	ListRsp<MissionSchemeTemplateRsp> pageSchemeTemplateRsp(PageReqBase pageReqBase);

	MissionSchemeTemplateRsp getSchemeTemplateRspById(String resourceId);

	List<DynamicData> getTaskLogByRoomIdAndUserId(String roomId, String studentId);

	/**
	 * 获取某个团队训练的训练手册
	 * @param roomId
	 * @return
	 */
	String getTeamTrainingManual(String roomId);

	List<MissionTemplate> batchInsertMissionTemplate(List<LoadMission> templateQuestions);

	/**
	 * 从 roleConfig, missionScheme 中解析出 id，再获取任务模板列表
	 * @param schemeJson
	 * @return
	 */
	List<MissionTemplate> batchGetByMissionSchemeJson(String schemeJson);

	/**
	 * 导入场景模板时批量导入 mission
	 * @param missionTemplates
	 * @return
	 */
	Boolean batchLoadMissionTemplates(List<MissionTemplate> missionTemplates);

	void validateMission(String missionsScheme);

	void getMissionContent(MissionSchemeObject<AddTemplateMission> schemeData);

	void validateMission(ColorMission<AddTemplateMission> missionsScheme);

	void validateRBMission(JSONObject jsonObject);

	/**
	 * 检查任务方案是否有效
	 * @param missionsScheme 任务方案json对象
	 * @return bool
	 */
	void missionsSchemeValidator(JSONObject missionsScheme);

	/**
	 * 生成任务方案导出文件
	 * @param templateIds mission scheme template ids
	 * @param tempDir
	 * @return
	 */
	String makeDumpFile(List<String> templateIds, String tempDir) throws IOException;

	/**
	 * 导入任务方案
	 * @param unZipPath
	 */
	void batchLoadScheme(String unZipPath);
}
