package com.cyberpeace.rtks.dao.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.rtks.dao.entity.Skill;
import com.cyberpeace.rtks.service.bo.skill.SkillRes;
import com.cyberpeace.rtks.service.bo.skill.SkillResBase;
import com.cyberpeace.rtks.service.bo.skill.SkillSimpleRes;

/**
 * 技能Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface SkillMapper extends BaseMapper<Skill> {

	/**
	 * 列出技能
	 *
	 * @param page         分页
	 * @param skillListReq 技能列表请求体
	 * @return {@code IPage<SkillRes> }
	 * @throws
	 * @see Page
	 * @see PageReqBase
	 * @see IPage<SkillRes>
	 */
	IPage<SkillRes> listSkill(Page page, PageReqBase skillListReq);

	/**
	 * 存在由资源id
	 *
	 * @param resourceId 资源id
	 * @return boolean
	 * @throws
	 * @see String
	 * @see boolean
	 */
	boolean existByResourceId(@Param("resourceId") String resourceId);

	/**
	 * 分页简单
	 *
	 * @param page    分页
	 * @param reqBase 请求体基础
	 * @return {@code IPage<SkillSimpleRes> }
	 * @throws
	 * @see Page
	 * @see PageReqBase
	 * @see IPage<SkillSimpleRes>
	 */
	IPage<SkillSimpleRes> pageSimple(@Param("page") Page page,
			@Param("reqBase") PageReqBase reqBase);

	/**
	 * 清除全部
	 *
	 * @throws
	 */
	void clearAll();

	/**
	 * 验证id列表
	 *
	 * @param skillIds 技能id列表
	 * @return {@code Set<String> }
	 * @throws
	 * @see List<String>
	 * @see Set<String>
	 */
	Set<String> validateIds(List<String> skillIds);

	/**
	 * 分页由岗位id
	 *
	 * @param page   分页
	 * @param roleId 岗位id
	 * @return {@code IPage<SkillResBase> }
	 * @throws
	 * @see Page
	 * @see String
	 * @see IPage<SkillResBase>
	 */
	IPage<SkillResBase> pageByRoleId(@Param("page") Page page, @Param("roleId") String roleId);
}
