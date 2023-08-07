package com.cyberpeace.cr.crbase.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.cr.crbase.controller.req.SetMissionEvent;
import com.cyberpeace.cr.crbase.controller.req.mission.MissionTemplatePageReq;
import com.cyberpeace.cr.crbase.controller.rsp.MissionSchemeTemplateRsp;
import com.cyberpeace.cr.crbase.dao.entity.*;
import com.cyberpeace.cr.crbase.service.context.*;
import com.cyberpeace.cr.crbase.service.context.mission.MissionCategory;
import com.cyberpeace.cr.crbase.service.context.mission.MissionTemplateRes;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface MissionService extends IService<Mission> {

	/**
	 * 新增模板任务
	 * @param missionTemplate 任务模板
	 * @return 任务模板
	 */
	MissionTemplate addMissionTemplate(MissionTemplate missionTemplate);

	/**
	 * 查询模板任务
	 * @param missionTemplateId 任务模板id
	 * @return 任务模板
	 */
	MissionTemplate getByMissionTemplateId(String missionTemplateId);

	/**
	 * 修改任务模板
	 * @param missionTemplate 任务模板
	 */
	void updateMissionTemplate(MissionTemplate missionTemplate);

	/**
	 * 根据id查询任务
	 * @param missionId 任务id
	 * @return 任务
	 */
	Mission getById(String missionId);

	/**
	 * 批量查询任务，保证顺序与查询时输入的id列表一致
	 * @param resourceIds 任务id列表
	 * @return 任务列表
	 */
	List<Mission> batchGet(List<String> resourceIds);

	/**
	 * 批量查询某训练实例的任务
	 * @param roomId 训练实例id
	 * @return 任务列表
	 */
	List<Mission> batchGetByRoom(String roomId);

	/**
	 * 下发任务
	 * @param missionId 任务id
	 * @param roleId 角色id
	 * @param currentUserId 用户id
	 */
	void release(String missionId, String roleId, String currentUserId);

	/**
	 * 批量下发任务
	 *
	 * @param missionIds    任务ids
	 * @param roleId        角色id
	 * @param currentUserId 用户id
	 * @return {@code Boolean} 有任务被下发返回true，否则返回false
	 */
	Boolean batchRelease(List<String> missionIds, String roleId, String currentUserId);

	/**
	 * 下发房间某个角色所有任务
	 * @param roomId 房间id
	 * @param roleId 角色id
	 * @param currentUserId 下发用户
	 */
	void roleRelease(String roomId, String roleId, String currentUserId);

	/**
	 * 导演新增动态任务
	 * @param mission 任务
	 * @param role 角色
	 * @param stage 阶段
	 * @return 结果（布尔值）
	 */
	Boolean addDynamic(Mission mission, CrRole role, Integer stage);

	/**
	 * 获取任务类别列表
	 * @return 任务类别列表
	 */
	List<MissionCategory> listCategories();

	/**
	 * mission完成数量统计
	 * @param roomId 训练实例id
	 * @return 任务完成数量统计
	 */
	Map<String, MissionCompletion> getMissionStatusData(String roomId);

	/**
	 * 根据id获取任务类别
	 * @param categoryId 任务类别id
	 * @return 任务类别
	 */
	MissionCategory getCategoryById(String categoryId);

	/**
	 * 复制模板任务为靶场实例任务
	 * @param missionTemplateIds 任务模板列表
	 * @param userId 用户id
	 * @param roomId 房间id
	 * @return map{旧模板任务id：复制任务id}
	 */
	Map<String, String> copyScheme(List<String> missionTemplateIds, String userId, String roomId);

	/**
	 * 开放、关闭题解
	 * @param missionId 任务id
	 * @param showWriteup 开放or关闭
	 */
	void openOrCloseWriteup(String missionId, Boolean showWriteup);

	/**
	 * 新增任务编排模板
	 * @param schemeTemplate 任务编排模版
	 */
	void addSchemeTemplate(MissionSchemeTemplate schemeTemplate);

	/**
	 * 批量删除任务编排模板
	 * @param ids 任务编排模板id列表
	 */
	void batchDeleteSchemeTemplate(List<String> ids);

	/**
	 * 查询任务编排模板
	 * @param schemeTemplateId 任务编排模板id
	 * @return 任务编排模板
	 */
	MissionSchemeTemplate getSchemeTemplateById(String schemeTemplateId);

	/**
	 * 更新任务编排模板
	 * @param toUpdateSchemeTemplate 任务编排模板数据
	 */
	void updateMissionSchemeTemplateById(MissionSchemeTemplate toUpdateSchemeTemplate);

	/**
	 * 分页查询任务编排模板
	 * @param pageReqBase 分页查询体
	 * @return 任务编排模板
	 */
	ListRsp<MissionSchemeTemplateRsp> pageSchemeTemplateRsp(PageReqBase pageReqBase);

	/**
	 * 获取任务编排模板数据
	 * @param resourceId 任务编排模板id
	 * @return 任务编排模板数据
	 */
	MissionSchemeTemplateRsp getSchemeTemplateRspById(String resourceId);

	/**
	 * 获取任务动态数据
	 * @param roomId 房间id
	 * @param studentId 学员id
	 * @return 任务动态数据
	 */
	List<DynamicData> getTaskLogByRoomIdAndUserId(String roomId, String studentId);

	/**
	 * 获取某个团队训练的训练手册
	 * @param roomId 团队房间id
	 * @return 团队训练的训练手册
	 */
	String getTeamTrainingManual(String roomId);

	/**
	 * 导入场景模板时批量导入 mission
	 * @param missionTemplates 任务模板列表
	 * @return 结果（布尔值）
	 */
	Boolean batchLoadMissionTemplates(List<MissionTemplate> missionTemplates);

	/**
	 * @param schemeData
	 */
	void getMissionContent(MissionSchemeObject<AddTemplateMission> schemeData);

	/**
	 * 校验任务
	 * @param missionsScheme 任务编排数据
	 */
	void validateMission(ColorMission<AddTemplateMission> missionsScheme);

	/**
	 * 检查任务方案是否有效
	 * @param missionsScheme 任务方案json对象
	 * @return 结果（布尔值）
	 */
	void missionsSchemeValidator(JSONObject missionsScheme);

	/**
	 * 生成任务方案导出文件
	 * @param templateIds 任务模板 ids
	 * @param tempDir 临时目录
	 * @return 压缩文件路径
	 */
	String makeDumpFile(List<String> templateIds, String tempDir) throws IOException;

	/**
	 * 导入任务方案
	 * @param unZipPath 解压路径
	 */
	void batchLoadScheme(String unZipPath);

	/**
	 * 获取雷达图维度
	 * @return 雷达图维度
	 */
	List<RadarLabel> getRadarLabel();

	/**
	 * 更新雷达图维度
	 * @param radarLabelList 雷达图维度
	 */
	void updateRadarLabel(List<RadarLabel> radarLabelList);

	/**
	 * 校验任务方案名称重名
	 * @param title 任务方案名称
	 */
	void checkSchemeTemplateTitleDuplicate(String title);

	/**
	 * 批量获取训练实例的任务
	 * @param roomId 训练实例id
	 * @param crRoleList 角色列表
	 * @return 任务列表
	 */
	List<Mission> batchGetByRoomAndCrRoles(String roomId, List<CrRole> crRoleList);

	/**
	 * 从任务编排阶段列表获取任务id
	 * @param missionStageSchemes 任务编排阶段列表
	 * @return 任务id列表
	 */
	List<String> getMissionIdsByScheme(List<MissionStageScheme> missionStageSchemes);

	/**
	 * 后台任务模板
	 * @param pageReq 分页查询体
	 * @return 任务模板分页响应
	 */
	ListRsp<MissionTemplateRes> pageMissionTemplate(MissionTemplatePageReq pageReq);

	/**
	 * 前台修改任务，校验是否阶段内重名
	 * @param missionId 任务id
	 * @param roomId 训练实例id
	 * @param title 任务名称
	 */
	void updateMissionValidate(String missionId, String roomId, String title);

	/**
	 * 获取任务动效配置
	 * @param missionId 任务id
	 * @return SetMissionEvent
	 */
	SetMissionEvent getMissionEvent(String missionId);

	/**
	 * 设置任务动效事件
	 * @param missionId 任务id
	 * @param eventTypeId 事件id
	 * @param deviceIds 设备id列表
	 */
	Boolean setMissionEvent(String missionId, Integer eventTypeId, List<String> deviceIds);

	Boolean missionEventTest(String sceneId, Integer eventTypeId, List<String> deviceIds);
}
