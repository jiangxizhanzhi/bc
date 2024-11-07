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
 * @date 2024/11/07
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
	 */
	IPage<SkillRes> listSkill(Page page, PageReqBase skillListReq);

	/**
	 * 存在由资源id
	 *
	 * @param resourceId 资源id
	 * @return boolean
	 */
	boolean existByResourceId(@Param("resourceId") String resourceId);

	/**
	 * 分页简单
	 *
	 * @param page    分页
	 * @param reqBase 请求体基础
	 * @return {@code IPage<SkillSimpleRes> }
	 */
	IPage<SkillSimpleRes> pageSimple(@Param("page") Page page,
			@Param("reqBase") PageReqBase reqBase);

	/**
	 * 全部清除
	 */
	void clearAll();

	/**
	 * 验证id列表
	 *
	 * @param skillIds 技能id列表
	 * @return {@code Set<String> }
	 */
	Set<String> validateIds(List<String> skillIds);

	/**
	 * 分页由角色id
	 *
	 * @param page   分页
	 * @param roleId 角色id
	 * @return {@code IPage<SkillResBase> }
	 */
	IPage<SkillResBase> pageByRoleId(@Param("page") Page page, @Param("roleId") String roleId);
}
