package com.cyberpeace.cr.crbase.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.cr.crbase.controller.req.mission.MissionTemplatePageReq;
import com.cyberpeace.cr.crbase.controller.rsp.MissionSchemeTemplateRsp;
import com.cyberpeace.cr.crbase.dao.entity.*;
import com.cyberpeace.cr.crbase.service.context.*;
import com.cyberpeace.cr.crbase.service.context.mission.MissionCategory;
import com.cyberpeace.cr.crbase.service.context.mission.MissionDetail;
import com.cyberpeace.cr.crbase.service.context.mission.MissionTemplateRes;

/**
 * 任务Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see IService
 * @since 1.0.0
 */
public interface MissionService extends IService<Mission> {

	/**
	 * 新增模板任务
	 *
	 * @param missionTemplate 任务模板
	 * @return 任务模板
	 * @throws
	 * @see MissionTemplate
	 * @see MissionTemplate
	 */
	MissionTemplate addMissionTemplate(MissionTemplate missionTemplate);

	/**
	 * 查询模板任务
	 *
	 * @param missionTemplateId 任务模板id
	 * @return 任务模板
	 * @throws
	 * @see String
	 * @see MissionTemplate
	 */
	MissionTemplate getByMissionTemplateId(String missionTemplateId);

	/**
	 * 修改任务模板
	 *
	 * @param missionTemplate 任务模板
	 * @throws
	 * @see MissionTemplate
	 */
	void updateMissionTemplate(MissionTemplate missionTemplate);

	/**
	 * 根据id查询任务
	 *
	 * @param missionId 任务id
	 * @return 任务
	 * @throws
	 * @see String
	 * @see Mission
	 */
	Mission getById(String missionId);

	/**
	 * 批量查询任务，保证顺序与查询时输入的id列表一致
	 *
	 * @param resourceIds 任务id列表
	 * @return 任务列表
	 * @throws
	 * @see List<String>
	 * @see List<Mission>
	 */
	List<Mission> batchGet(List<String> resourceIds);

	/**
	 * 批量查询某训练实例的任务
	 *
	 * @param roomId 训练实例id
	 * @return 任务列表
	 * @throws
	 * @see String
	 * @see List<Mission>
	 */
	List<Mission> batchGetByRoom(String roomId);

	/**
	 * 下发任务
	 *
	 * @param missionId     任务id
	 * @param roleId        角色id
	 * @param currentUserId 用户id
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 */
	void release(String missionId, String roleId, String currentUserId);

	/**
	 * 下发房间某个角色所有任务
	 *
	 * @param roomId        房间id
	 * @param roleId        角色id
	 * @param currentUserId 下发用户
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 */
	void roleRelease(String roomId, String roleId, String currentUserId);

	/**
	 * 导演新增动态任务
	 *
	 * @param mission        任务
	 * @param role           角色
	 * @param stage          阶段
	 * @param afterMissionId 后任务id
	 * @return 结果（布尔值）
	 * @throws
	 * @see Mission
	 * @see CrRole
	 * @see Integer
	 * @see String
	 * @see Boolean
	 */
	Boolean addDynamic(Mission mission, CrRole role, Integer stage,String afterMissionId);

	/**
	 * 获取任务类别列表
	 *
	 * @return 任务类别列表
	 * @throws
	 * @see List<MissionCategory>
	 */
	List<MissionCategory> listCategories();

	/**
	 * mission完成数量统计
	 *
	 * @param roomId 训练实例id
	 * @return 任务完成数量统计
	 * @throws
	 * @see String
	 * @see Map<String, MissionCompletion>
	 */
	Map<String, MissionCompletion> getMissionStatusData(String roomId);

	/**
	 * 根据id获取任务类别
	 *
	 * @param categoryId 任务类别id
	 * @return 任务类别
	 * @throws
	 * @see String
	 * @see MissionCategory
	 */
	MissionCategory getCategoryById(String categoryId);

	/**
	 * 复制模板任务为靶场实例任务
	 *
	 * @param missionTemplateIds 任务模板列表
	 * @param userId             用户id
	 * @param roomId             房间id
	 * @return map{旧模板任务id：复制任务id}
	 * @throws
	 * @see List<String>
	 * @see String
	 * @see String
	 * @see Map<String, String>
	 */
	Map<String, String> copyScheme(List<String> missionTemplateIds, String userId, String roomId);

	/**
	 * 开放、关闭题解
	 *
	 * @param missionId   任务id
	 * @param showWriteup 开放or关闭
	 * @throws
	 * @see String
	 * @see Boolean
	 */
	void openOrCloseWriteup(String missionId, Boolean showWriteup);

	/**
	 * 新增任务编排模板
	 *
	 * @param schemeTemplate 任务编排模版
	 * @throws
	 * @see MissionSchemeTemplate
	 */
	void addSchemeTemplate(MissionSchemeTemplate schemeTemplate);

	/**
	 * 批量删除任务编排模板
	 *
	 * @param ids 任务编排模板id列表
	 * @throws
	 * @see List<String>
	 */
	void batchDeleteSchemeTemplate(List<String> ids);

	/**
	 * 查询任务编排模板
	 *
	 * @param schemeTemplateId 任务编排模板id
	 * @return 任务编排模板
	 * @throws
	 * @see String
	 * @see MissionSchemeTemplate
	 */
	MissionSchemeTemplate getSchemeTemplateById(String schemeTemplateId);

	/**
	 * 更新任务编排模板
	 *
	 * @param toUpdateSchemeTemplate 任务编排模板数据
	 * @throws
	 * @see MissionSchemeTemplate
	 */
	void updateMissionSchemeTemplateById(MissionSchemeTemplate toUpdateSchemeTemplate);

	/**
	 * 分页查询任务编排模板
	 *
	 * @param pageReqBase 分页查询体
	 * @return 任务编排模板
	 * @throws
	 * @see PageReqBase
	 * @see ListRsp<MissionSchemeTemplateRsp>
	 */
	ListRsp<MissionSchemeTemplateRsp> pageSchemeTemplateRsp(PageReqBase pageReqBase);

	/**
	 * 获取任务编排模板数据
	 *
	 * @param resourceId 任务编排模板id
	 * @return 任务编排模板数据
	 * @throws
	 * @see String
	 * @see MissionSchemeTemplateRsp
	 */
	MissionSchemeTemplateRsp getSchemeTemplateRspById(String resourceId);

	/**
	 * 获取任务动态数据
	 *
	 * @param roomId    房间id
	 * @param studentId 学员id
	 * @return 任务动态数据
	 * @throws
	 * @see String
	 * @see String
	 * @see List<DynamicData>
	 */
	List<DynamicData> getTaskLogByRoomIdAndUserId(String roomId, String studentId);

	/**
	 * 获取某个团队训练的训练手册
	 *
	 * @param roomId 团队房间id
	 * @return 团队训练的训练手册
	 * @throws
	 * @see String
	 * @see String
	 */
	String getTeamTrainingManual(String roomId);

	/**
	 * 导入场景模板时批量导入 mission
	 *
	 * @param missionTemplates 任务模板列表
	 * @return 结果（布尔值）
	 * @throws
	 * @see List<MissionTemplate>
	 * @see Boolean
	 */
	Boolean batchLoadMissionTemplates(List<MissionTemplate> missionTemplates);

	/**
	 * 获取任务内容
	 *
	 * @param schemeData 方案数据
	 * @throws
	 * @see MissionSchemeObject<AddTemplateMission>
	 */
	void getMissionContent(MissionSchemeObject<AddTemplateMission> schemeData);

	/**
	 * 校验任务
	 *
	 * @param missionsScheme 任务编排数据
	 * @throws
	 * @see ColorMission<AddTemplateMission>
	 */
	void validateMission(ColorMission<AddTemplateMission> missionsScheme);

	/**
	 * 检查任务方案是否有效
	 *
	 * @param missionsScheme 任务方案json对象
	 * @throws
	 * @see JSONObject
	 */
	void missionsSchemeValidator(JSONObject missionsScheme);

	/**
	 * 生成任务方案导出文件
	 *
	 * @param templateIds 任务模板 ids
	 * @param tempDir     临时目录
	 * @return 压缩文件路径
	 * @throws @throws IOException io异常
	 * @see List<String>
	 * @see String
	 * @see String
	 */
	String makeDumpFile(List<String> templateIds, String tempDir) throws IOException;

	/**
	 * 导入任务方案
	 *
	 * @param unZipPath 解压路径
	 * @throws
	 * @see String
	 */
	void batchLoadScheme(String unZipPath);

	/**
	 * 获取雷达图维度
	 *
	 * @return 雷达图维度
	 * @throws
	 * @see List<RadarLabel>
	 */
	List<RadarLabel> getRadarLabel();

	/**
	 * 更新雷达图维度
	 *
	 * @param radarLabelList 雷达图维度
	 * @throws
	 * @see List<RadarLabel>
	 */
	void updateRadarLabel(List<RadarLabel> radarLabelList);

	/**
	 * 校验任务方案名称重名
	 *
	 * @param title 任务方案名称
	 * @throws
	 * @see String
	 */
	void checkSchemeTemplateTitleDuplicate(String title);

	/**
	 * 批量获取训练实例的任务
	 *
	 * @param roomId     训练实例id
	 * @param crRoleList 角色列表
	 * @return 任务列表
	 * @throws
	 * @see String
	 * @see List<CrRole>
	 * @see List<Mission>
	 */
	List<Mission> batchGetByRoomAndCrRoles(String roomId, List<CrRole> crRoleList);

	/**
	 * 从任务编排阶段列表获取任务id
	 *
	 * @param missionStageSchemes 任务编排阶段列表
	 * @return 任务id列表
	 * @throws
	 * @see List<MissionStageScheme>
	 * @see List<String>
	 */
	List<String> getMissionIdsByScheme(List<MissionStageScheme> missionStageSchemes);

	/**
	 * 后台任务模板
	 *
	 * @param pageReq 分页查询体
	 * @return 任务模板分页响应
	 * @throws
	 * @see MissionTemplatePageReq
	 * @see ListRsp<MissionTemplateRes>
	 */
	ListRsp<MissionTemplateRes> pageMissionTemplate(MissionTemplatePageReq pageReq);

	/**
	 * 前台修改任务，校验是否阶段内重名
	 *
	 * @param missionId 任务id
	 * @param roomId    训练实例id
	 * @param title     任务名称
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 */
	void updateMissionValidate(String missionId, String roomId, String title);


	/**
	 * 批量下发任务
	 *
	 * @param missionIds    任务ids
	 * @param roleId        角色id
	 * @param currentUserId 用户id
	 * @return {@code Boolean} 有任务被下发返回true，否则返回false
	 * @throws
	 * @see List<String>
	 * @see String
	 * @see String
	 * @see Boolean
	 */
	Boolean batchRelease(List<String> missionIds, String roleId, String currentUserId);

	/**
	 * 获取详情通过id
	 *
	 * @param resourceId 资源id
	 * @return {@code MissionDetail}
	 * @throws
	 * @see String
	 * @see MissionDetail
	 */
	MissionDetail getDetailById(String resourceId);

	/**
	 * 获取任务map通过id列表
	 *
	 * @param missionIds 任务id列表
	 * @return {@code Map<String, Mission>}
	 * @throws
	 * @see List<String>
	 * @see Map<String, Mission>
	 */
	Map<String, Mission> getMissionMapByIds(List<String> missionIds);
}
