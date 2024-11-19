package com.cyberpeace.rtks.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.rtks.dao.entity.Skill;
import com.cyberpeace.rtks.service.bo.skill.*;

/**
 * 技能Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see IService
 * @since 1.0.0
 */
public interface SkillService extends IService<Skill> {

	/**
	 * 获取分页查询结果列表
	 *
	 * @param skillListReq
	 * @return 分页查询结果
	 * @throws
	 * @see PageReqBase
	 * @see ListRsp<SkillRes>
	 */
	ListRsp<SkillRes> page(PageReqBase skillListReq);

	/**
	 * 创建、编辑岗位时 添加技能的列表查询接口
	 *
	 * @param reqBase
	 * @return
	 * @throws
	 * @see PageReqBase
	 * @see ListRsp<SkillSimpleRes>
	 */
	ListRsp<SkillSimpleRes> pageSimple(PageReqBase reqBase);

	/**
	 * 通过id获取查询结果
	 *
	 * @param id
	 * @return 分页查询结果
	 * @throws
	 * @see String
	 * @see SkillRes
	 */
	SkillRes getSkillByResourceId(String id);

	/**
	 * 给定id 返回对应的技能对象 否则报错
	 *
	 * @param skillId 技能id
	 * @return {@code Skill }
	 * @throws
	 * @see String
	 * @see Skill
	 */
	Skill getOr404(String skillId);

	/**
	 * 新增技能和任务的关联关系
	 *
	 * @param skillId
	 * @param missionTemplates 任务模板
	 * @throws
	 * @see String
	 * @see List<IdWeight>
	 */
	void addMissionRelation(String skillId, List<IdWeight> missionTemplates);

	/**
	 * 删除技能和任务的关联关系
	 *
	 * @param skillId
	 * @param missionTemplateIds 任务模板id列表
	 * @throws
	 * @see String
	 * @see List<String>
	 */
	void deleteMissionRelation(String skillId, List<String> missionTemplateIds);

	/**
	 * 校验技能编号是否可用
	 *
	 * @param resourceId 资源id
	 * @throws
	 * @see String
	 */
	void checkResourceId(@Param("resourceId") String resourceId);

	/**
	 * 编辑技能保存时更新任务关联
	 *
	 * @param resourceId
	 * @param missions   任务
	 * @throws
	 * @see String
	 * @see List<IdWeight>
	 */
	void updateMissionRelations(String resourceId, List<IdWeight> missions);

	/**
	 * 删除技能时删除任务关联
	 *
	 * @param skillIds 技能id列表
	 * @throws
	 * @see List<String>
	 */
	void clearMissionRel(List<String> skillIds);

	/**
	 * 计算用户的技能得分
	 *
	 * @param skillId
	 * @param userId
	 * @return score
	 * @throws
	 * @see String
	 * @see String
	 * @see Double
	 */
	Double calculateScore(String skillId, String userId);

	/**
	 * 清空所有技能和技能相关
	 *
	 * @throws
	 */
	void clearAll();

	/**
	 * 给特定岗位关联的skill分页，并训练进度
	 *
	 * @param pageReqBase
	 * @param roleId
	 * @return
	 * @throws
	 * @see PageReqBase
	 * @see String
	 * @see ListRsp<SkillProcessRes>
	 */
	ListRsp<SkillProcessRes> pageSkillListByRoleId(PageReqBase pageReqBase, String roleId);

	/**
	 * 分页获取岗位的技能，并训练路径
	 *
	 * @param pageReqBase
	 * @param roleId
	 * @return
	 * @throws
	 * @see PageReqBase
	 * @see String
	 * @see ListRsp<SkillPagePath>
	 */
	ListRsp<SkillPagePath> pageSkillPathByRoleId(PageReqBase pageReqBase, String roleId);
}
